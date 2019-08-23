package  com.weparty.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-7-18
 * @desc   : 图片工具。从Android 2.2 版本的BitmapUtil中扣出来的，兼容到1.6版本
 */
public class BitmapScale {

	public interface Option{
		int NONE = 0x0;
		int SCALE_UP = 0x1;
		int RECYCLE_INPUT = 0x2;
	}

	/**
	 * 指定长度宽度进行缩放
	 * @param source
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static Bitmap extract(Bitmap source, int targetWidth, int targetHeight) {
		return extractThumbnail(source, targetWidth, targetHeight, Option.NONE);
	}
	
	/**
	 * <b>description :</b>		
	 * </br><b>time :</b>		2012-8-10 下午10:30:27
	 * @param source			源图
	 * @param targetWidth		目标宽度
	 * @param targetHeight		目标高度
	 * @return
	 */
	/**
	 * 按指定比例缩放
	 * @param source
	 * @param targetWidth
	 * @param targetHeight
	 * @return
	 */
	public static Bitmap prorate(Bitmap source, int targetWidth, int targetHeight) {
		if (source == null) return null;
		
		int srcWidth = source.getWidth();
		int srcHeight = source.getHeight();
		if (srcWidth < srcHeight) {
			targetHeight = srcHeight * targetWidth / srcWidth;
		} else { 
			targetWidth = srcWidth * targetHeight / srcHeight;
		}
		return extractThumbnail(source, targetWidth, targetHeight, Option.NONE);
	}

	private static Bitmap extractThumbnail(Bitmap source, int targetWidth,int targetHeight, int options) {
		if (source == null) {
			return null;
		}
		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = targetWidth / (float) source.getWidth();
		} else {
			scale = targetHeight / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, targetWidth, targetHeight,Option.SCALE_UP | options);
		return thumbnail;
	}

	private static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, int options) {
		boolean scaleUp = (options & Option.SCALE_UP) != 0;
		boolean recycle = (options & Option.RECYCLE_INPUT) != 0;

		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);
			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}
		if (recycle && b1 != source) {
			source.recycle();
		}
		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);
		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);
		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}
		return b2;
	}
}