package com.jufan.platform.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.jufan.platform.util.ConstVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageSendActivity extends AutoActivity {

	@InjectionView(id = R.id.image_grid)
	private GridView gridView;

	@InjectionView(id = R.id.image_send_btn, click = "imgSendBtnClick")
	private android.widget.Button sendImageBtn;

	private ArrayList<String> selectImageList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.image_send, "");

		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
		acd.putArrayValue("image_grid", list);

		Intent intent = getIntent();
		String imagePath = intent.getStringExtra("image_dir");
		File f = new File(imagePath);
		for (File file : f.listFiles()) {
			boolean flag = false;
			for (String type : ConstVariables.IMAGE_TYPE) {
				if (file.getName().toLowerCase().endsWith(type)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				AbstractCommonData _acd = DataConvertFactory.getInstanceEmpty();
				_acd.putStringValue("image_path", file.getAbsolutePath());
				list.add(_acd);
			}
		}
		fillData(acd, getWindow().getDecorView());
	}

	public void imgSendBtnClick(View v) {
		Intent i = new Intent();
		i.putStringArrayListExtra("image_list", selectImageList);
		setResult(ConstVariables.SEND_IMAGE_FLAG, i);
		finish();
	}

	@Override
	protected void onGridItemClick(String prxName, android.widget.GridView g,
			View v, int position, long id) {
		setCheckBoxEvent(v, (AbstractCommonData) v.getTag());
	}

	@Override
	protected void handleGridItem(GridView gv, View v, int position) {
		ImageView iv = (ImageView) v.findViewById(R.id.image_preview);
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		String filePath = acd.getStringValue("image_path");
		if (!acd.containsKey("is_checked")) {
			acd.putBooleanValue("is_checked", false);
		}
		ImageLoader.getInstance().displayImage("file://" + filePath, iv);

		setCheckBoxEvent(v, acd);
	}

	private void setCheckBoxEvent(View v, AbstractCommonData acd) {

		CheckBox cb = (CheckBox) v.findViewById(R.id.image_select_cb);
		cb.setChecked(acd.getBooleanValue("is_checked"));
		cb.setTag(acd);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				AbstractCommonData acd = (AbstractCommonData) buttonView
						.getTag();
				String path = acd.getStringValue("image_path");
				if (isChecked) {
					selectImageList.add(path);
					acd.putBooleanValue("is_checked", true);
				} else {
					selectImageList.remove(path);
					acd.putBooleanValue("is_checked", false);
				}
				if (selectImageList.size() > 0) {
					sendImageBtn.setEnabled(true);
				} else {
					sendImageBtn.setEnabled(false);
				}
			}
		});
	}

}
