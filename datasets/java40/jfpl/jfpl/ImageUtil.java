package com.jufan.platform.util;

import java.io.File;
import java.io.FileNotFoundException;

import com.cyss.android.lib.util.BitmapCache;
import com.cyss.android.lib.util.SystemUtil;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageUtil {

	private static BitmapCache bitmapCache = new BitmapCache();

	public static ImageSize getChatImageSize(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		int maxWidth = SystemUtil.SCREEN_WIDTH * 2 / 3;
		if (width > SystemUtil.SCREEN_WIDTH * 2 / 3) {
			int scale = width / maxWidth + 1;
			width = width / scale;
			height = height / scale;
		}
		return new ImageSize(width, height);
	}

	public static Bitmap setImageView(String filePath, ImageView iv)
			throws FileNotFoundException {
		Bitmap bm = null;
		bm = bitmapCache.get(filePath);
		if (bm != null) {
			iv.setImageBitmap(bm);
			return bm;
		}
		File f = new File(filePath);
		int size = 0;
		if (f.length() < 1024 * 10) {
			size = 1;
		} else if (f.length() > 1024 * 10 && f.length() > 1024 * 50) {
			size = 2;
		} else if (f.length() > 1024 * 50 && f.length() > 1024 * 256) {
			size = 3;
		} else {
			size = 4;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = size;
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[32 * 1024];

		bm = BitmapFactory.decodeFile(filePath, options);
		bitmapCache.put(filePath, bm);
		iv.setImageBitmap(bm);
		return bm;
	}

	public static Bitmap setImageView(String filePath, ImageView iv,
			int maxWidth) throws FileNotFoundException {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		options.inJustDecodeBounds = false;
		if (width > maxWidth) {
			int outHeight = maxWidth * height / width;
			options.outHeight = outHeight;
			options.outWidth = maxWidth;
			options.inSampleSize = width / maxWidth + 1;
		}
		bm = BitmapFactory.decodeFile(filePath, options);
		iv.setImageBitmap(bm);
		return bm;
	}
}
