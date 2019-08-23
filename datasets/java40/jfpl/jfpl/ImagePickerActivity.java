package com.jufan.platform.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.util.FileUtil;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.ImageUtil;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("DefaultLocale")
public class ImagePickerActivity extends AutoActivity {

	private List<AbstractCommonData> imageDataSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.image_picker, "");
		handler.sendEmptyMessage(SHOW_LOADING);
		new Thread(new Runnable() {

			@Override
			public void run() {
				File f = new File(FileUtil.getBaseStoragePath());
				imageDataSource = new ArrayList<AbstractCommonData>();
				AbstractCommonData item = getImagePickerList(f, 0);
				if (item != null)
					imageDataSource.add(item);
				final AbstractCommonData acd = DataConvertFactory
						.getInstanceEmpty();
				acd.putArrayValue("image_list", imageDataSource);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						fillData(acd, getWindow().getDecorView());
						handler.sendEmptyMessage(HIDE_LOADING);
					}
				});

			}
		}).start();
	}

	@Override
	protected void handleListItem(View v, int position) {

		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		ImageView iv = (ImageView) acd.getObjectValue("_image_view");
		if (iv == null) {
			iv = (ImageView) v.findViewById(R.id.image_preview);
		}
		String filePath = acd.getStringValue("image_path");
		try {
			ImageLoader.getInstance().displayImage("file://" + filePath, iv);
		} catch (Exception ex) {
			SystemUtil.printException(ex, "w");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (resultCode == ConstVariables.SEND_IMAGE_FLAG) {
			ArrayList<String> imageList = intent
					.getStringArrayListExtra("image_list");
			Intent i = new Intent();
			i.putStringArrayListExtra("image_list", imageList);
			setResult(ConstVariables.SEND_IMAGE_FLAG, i);
			finish();
		}

	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		String imagePath = acd.getStringValue("image_parent_dir");
		Intent intent = new Intent(this, ImageSendActivity.class);
		intent.putExtra("image_dir", imagePath);
		startActivityForResult(intent, 0);
	}

	private AbstractCommonData getImagePickerList(File f, int i) {
		int j = i + 1;
		if (f == null || !f.exists()) {
			return null;
		}
		File[] imageList = f.listFiles();
		if (imageList == null) {
			return null;
		}
		if (imageList.length > 0) {
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			String fname = f.getName();
			String imageDir = f.getAbsolutePath();
			if (fname.length() > 20 || f.getName().startsWith(".")
					|| f.getName().indexOf(".") > 0 || i > 2) {
				return null;
			}

			String imagePath = "";
			int length = 0;
			for (File file : imageList) {
				if (file.isDirectory()) {
					AbstractCommonData _acd = getImagePickerList(file, j);
					if (_acd != null) {
						imageDataSource.add(_acd);
					}
				} else if (file.isFile()) {
					boolean flag = false;
					for (String type : ConstVariables.IMAGE_TYPE) {
						if (file.getName().toLowerCase().endsWith(type)) {
							flag = true;
							break;
						}
					}
					if (flag) {
						if (StringUtil.isNull(imagePath)) {
							imagePath = file.getAbsolutePath();
						}
						length++;
					}
				}
			}
			if (length > 0) {
				acd.putStringValue("image_num", length + "张");
				acd.putStringValue("image_dir", fname);
				acd.putStringValue("image_path", imagePath);
				acd.putStringValue("image_parent_dir", imageDir);
				return acd;
			} else {
				return null;
			}
		}
		return null;
	}
}
