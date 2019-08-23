package com.jufan.platform.ui;

import java.io.File;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.view.ZoomableImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowImageActivity extends AutoActivity {
	@InjectionView(id = R.id.zoom_img)
	private ZoomableImageView zoomImageView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.show_image, "");
		Intent intent = getIntent();
		String filePath = intent.getStringExtra("image_path");
		File f = new File(filePath);
		if (f.exists() && f.isFile()) {
			zoomImageView.setBitmap(ImageLoader.getInstance().loadImageSync(
					"file://" + filePath));
		} else if (filePath.startsWith("http://")) {
			zoomImageView.setBitmap(ImageLoader.getInstance().loadImageSync(
					filePath));
		} else {
			Toast.makeText(this, "加载图片失败", Toast.LENGTH_LONG).show();
		}
	}
}
