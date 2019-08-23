package com.jufan.platform.ui;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.util.ConstVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class VioActivity extends BaseChatEnterActivity {

	@InjectionView(id = R.id.title_back_btn, click = "titleBackBtnClick")
	private ImageButton titleBackBtn;
	@InjectionView(id = R.id.frame_title)
	private android.widget.TextView titleTv;
	@InjectionView(id = R.id.wz_tip)
	private android.widget.TextView wztipTv;

	private String bindUsername;
	private String hphm;
	private AbstractCommonData vioAcd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.vio, "");
		Intent intent = getIntent();
		this.bindUsername = intent.getStringExtra("bind_name");
		this.hphm = intent.getStringExtra("hphm");
		titleTv.setText("豫A" + this.hphm);

		AbstractCommonData acd = queryForData("get_bind_veh_by_bindname",
				this.bindUsername);
		long syncTime = 0;
		try {
			syncTime = Long.parseLong(acd.getStringValue("sync_time"));
		} catch (Exception e) {
		}
		String lastView = acd.getStringValue("last_vio");
		if (StringUtil.isNull(lastView)
				|| new Date().getTime() - syncTime > ConstVariables.DEFAULT_SYNC_TIME) {
			refreshVio();
		} else {
			try {
				vioAcd = DataConvertFactory.getInstanceByJson(lastView);
				fillVioData(vioAcd);
			} catch (Exception e) {

			}
		}

	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		final AbstractCommonData acd = (AbstractCommonData) v.getTag();
		Intent intent = getCustomIntent(VioDetailActivity.class);
		intent.putExtra("bind_user", bindUsername);
		intent.putExtra("wfdz", acd.getStringValue("wfdz"));
		intent.putExtra("json", DataConvertFactory.praseNormJson(acd));
		startActivity(intent);
	}

	@Override
	protected void handleListItem(View v, int position) {
		final AbstractCommonData acd = (AbstractCommonData) v.getTag();
		android.widget.Button btn = (android.widget.Button) v
				.findViewById(R.id.vio_detail_btn);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = getCustomIntent(VioDetailActivity.class);
				intent.putExtra("bind_user", bindUsername);
				intent.putExtra("wfdz", acd.getStringValue("wfdz"));
				intent.putExtra("json", DataConvertFactory.praseNormJson(acd));
				startActivity(intent);
			}
		});
		ImageView imgV = (ImageView) v.findViewById(R.id.temp_img1);
		imgV.setImageBitmap(ImageLoader.getInstance()
				.loadImageSync(
						"assets://clpp/car_" + vioAcd.getStringValue("pp_pic")
								+ ".jpg"));
	}

	private void fillVioData(AbstractCommonData acd) {
		if (acd.getArrayValue("wzxx").size() == 0) {
			wztipTv.setText("恭喜您，没有违章。");
		} else {
			wztipTv.setText("共有 " + acd.getArrayValue("wzxx").size() + " 条违章");
		}
		fillData(acd, getWindow().getDecorView());
	}

	public void refreshVio() {
		AbstractCommonData acd = SystemUtil.getCommonData("S22003",
				this.bindUsername);
		// acd.putBooleanValue("is_alert", true);
		acd.putStringValue("_url", ConstVariables.VIO_SERVER_URL);
		acd.putObjectValue("ipacket", HttpSendFactory.getClientSendMethod());
		acd.putObjectValue("iservice", new IServiceLogic() {

			@Override
			public AbstractCommonData doSuccess(AbstractCommonData acd) {
				update("update_bind_veh", new Date().getTime(),
						DataConvertFactory.praseNormJson(acd).getBytes(),
						acd.getStringValue("pp_pic"), bindUsername);
				fillVioData(acd);
				return acd;
			}

			@Override
			public AbstractCommonData doError(AbstractCommonData acd) {
				return acd;
			}
		});
		ServiceController.addService(acd, this);
	}

	public void titleBackBtnClick(View v) {
		this.finish();
	}

}
