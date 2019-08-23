package com.jufan.platform.ui;

import java.util.Date;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.service.BindVehService;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("DefaultLocale")
public class MyVioActivity extends BaseChatEnterActivity {

	@InjectionView(id = R.id.add_bind_veh, click = "addBindVehClick")
	private ImageView addBindVeh;
	@InjectionView(id = R.id.bind_layout)
	private LinearLayout bindLayout;
	@InjectionView(id = R.id.vio_layout)
	private LinearLayout vioLayout;
	@InjectionView(id = R.id.bind_veh_hpzl)
	private Spinner hpzlSpinner;
	@InjectionView(id = R.id.bind_btn, click = "bindVehClick")
	private Button bindBtn;
	@InjectionView(id = R.id.bind_hphm)
	private EditText bindHphmEt;
	@InjectionView(id = R.id.bind_clsbdh)
	private EditText bindClsbdhEt;
	@InjectionView(id = R.id.bind_syr)
	private EditText syrEt;
	@InjectionView(id = R.id.bind_sfzmhm)
	private EditText sfzmhmEt;

	private List<AbstractCommonData> bindVehList;
	private boolean isShowBindFlag = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.my_vio, "");
		String[] hpzlList = getResources()
				.getStringArray(R.array.bind_veh_hpzl);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, hpzlList);
		hpzlSpinner.setAdapter(adapter);
		refreshBindVeh();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isShowBindFlag && bindVehList.size() > 0
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			changeLayout(bindVehList.size());
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		Intent intent = getCustomIntent(VioActivity.class);
		intent.putExtra("hphm", acd.getStringValue("hphm"));
		intent.putExtra("bind_name", acd.getStringValue("bind_name"));
		startActivity(intent);
	}

	@Override
	protected void onListItemLongClick(String prxName, ListView l, View v,
			int position, long id) {
		final AbstractCommonData acd = (AbstractCommonData) v.getTag();
		new AlertDialog.Builder(this).setItems(R.array.unbind_veh,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							unbindVeh(acd.getStringValue("bind_name"));
							break;

						default:
							break;
						}
					}
				}).show();
	}

	@Override
	protected void handleListItem(View v, int position) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		ImageView clpp1 = (ImageView) v.findViewById(R.id.clpp_icon);
		TextView hphmTv = (TextView) v.findViewById(R.id.bind_veh_hphm);
		TextView hpzlTv = (TextView) v.findViewById(R.id.bind_veh_hpzl);
		String vio = acd.getStringValue("last_vio");
		hphmTv.setText("豫A" + acd.getStringValue("hphm"));
		hpzlTv.setText(GlobalVariables.getHpzlDesc(acd.getStringValue("hpzl")));
		String clpp1Str = acd.getStringValue("clpp1");
		clpp1.setImageBitmap(ImageLoader.getInstance().loadImageSync(
				"assets://clpp/car_" + clpp1Str + ".jpg"));
		if (StringUtil.isNull(vio)) {
			refreshBindVeh(acd.getStringValue("bind_name"));
		}
	}

	public void addBindVehClick(View v) {
		changeLayout(0);
	}

	public void unbindVeh(final String bindUser) {
		AbstractCommonData sendData = SystemUtil.getCommonData("",
				GlobalVariables.loginUsername);
		sendData.putStringValue("jtgzfw_user", bindUser);
		sendData.putStringValue("_url", ConstVariables.UNBIND_VEH_URL);
		sendData.putObjectValue("ipacket",
				HttpSendFactory.getChatPlatformSendMethod());
		sendData.putObjectValue("iservice", new IServiceLogic() {

			@Override
			public AbstractCommonData doSuccess(AbstractCommonData acd) {
				update("delete_bind_veh", bindUser);
				refreshBindVeh();
				return null;
			}

			@Override
			public AbstractCommonData doError(AbstractCommonData acd) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		ServiceController.addService(sendData, this);
	}

	public void bindVehClick(View v) {
		String hphm = bindHphmEt.getText().toString();
		hphm = hphm.toUpperCase();
		bindHphmEt.setText(hphm);
		String clsbdh = bindClsbdhEt.getText().toString();
		clsbdh = clsbdh.toUpperCase();
		bindClsbdhEt.setText(clsbdh);
		String hpzl = hpzlSpinner.getSelectedItemPosition() == 0 ? "02" : "01";
		String syr = syrEt.getText().toString();
		String sfzmhm = sfzmhmEt.getText().toString();
		sfzmhm = sfzmhm.toUpperCase();
		sfzmhmEt.setText(sfzmhm);
		AbstractCommonData sendData = SystemUtil.getCommonData("",
				GlobalVariables.loginUsername);
		sendData.putStringValue("_url", ConstVariables.BIND_VEH_URL);
		sendData.putStringValue("hphm", hphm);
		sendData.putStringValue("hpzl", hpzl);
		sendData.putStringValue("clsbdh", clsbdh);
		sendData.putStringValue("syr", syr);
		sendData.putStringValue("sfzmhm", sfzmhm);
		sendData.putObjectValue("ipacket",
				HttpSendFactory.getChatPlatformSendMethod());
		sendData.putObjectValue("iservice", new BindVehService(this, sendData));
		ServiceController.addService(sendData, this);
	}

	public void refreshBindVeh() {
		bindVehList = query("get_bind_veh_by_username",
				GlobalVariables.loginUsername);
		changeLayout(bindVehList.size());
		if (bindVehList.size() > 0) {
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			acd.putArrayValue("bind_veh_list", bindVehList);
			fillData(acd, vioLayout);
		} else {
			changeLayout(0);
		}
	}

	public void refreshBindVeh(final String bindUser) {
		refreshBindVeh();
		AbstractCommonData acd = SystemUtil.getCommonData("S22003", bindUser);
		acd.putBooleanValue("is_alert", false);
		acd.putBooleanValue("is_show_loading", false);
		acd.putStringValue("_url", ConstVariables.VIO_SERVER_URL);
		acd.putObjectValue("ipacket", HttpSendFactory.getClientSendMethod());
		acd.putObjectValue("iservice", new IServiceLogic() {

			@Override
			public AbstractCommonData doSuccess(AbstractCommonData acd) {
				update("update_bind_veh", new Date().getTime(),
						DataConvertFactory.praseNormJson(acd).getBytes(),
						acd.getStringValue("pp_pic"), bindUser);
				refreshBindVeh();
				return null;
			}

			@Override
			public AbstractCommonData doError(AbstractCommonData acd) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		ServiceController.addService(acd);
	}

	public void changeLayout(int flag) {
		switch (flag) {
		case 0:
			addBindVeh.setVisibility(View.GONE);
			bindLayout.setVisibility(View.VISIBLE);
			vioLayout.setVisibility(View.GONE);
			isShowBindFlag = true;
			break;

		default:
			addBindVeh.setVisibility(View.VISIBLE);
			bindLayout.setVisibility(View.GONE);
			vioLayout.setVisibility(View.VISIBLE);
			isShowBindFlag = false;
			break;
		}
	}
}
