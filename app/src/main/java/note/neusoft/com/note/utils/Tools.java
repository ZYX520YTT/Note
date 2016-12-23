package note.neusoft.com.note.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;

import note.neusoft.com.note.R;

public class Tools {



	/**
	 * 得到指定大小，压缩后的图片。
	 *
	 * @param mcContext
	 *            上下文
	 * @param image
	 *            原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap getThumbnails(Context mcContext, Bitmap image) {
		/*
		 * getResources().getDimensionPixelSize 取出dimens中的值
		 */
		Bitmap bitmap;
		int width = mcContext.getResources().getDimensionPixelSize(R.dimen.headimage_width);
		int height = mcContext.getResources().getDimensionPixelSize(R.dimen.headimage_height);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 *
		 * @param source 源文件(Bitmap类型)
		 *
		 * @param width 压缩成的宽度
		 *
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width || w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		} else {
			bitmap = image;
		}
		return bitmap;
	}

	/**
	 * 获取控件的高度或者宽度 isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
	 * 
	 * @param view
	 * @param isHeight
	 * @return
	 */
	public static int getViewHeight(View view, boolean isHeight) {
		int result;
		if (view == null)
			return 0;
		if (isHeight) {
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(h, 0);
			result = view.getMeasuredHeight();
		} else {
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(0, w);
			result = view.getMeasuredWidth();
		}
		return result;
	}


	/**
	 * 得到指定大小，压缩后的图片。
	 * 
	 * @param mcContext
	 *            上下文
	 * @param image
	 *            原图片
	 * @return 压缩后的图片
	 */
	public static Bitmap getThumbnails(Context mcContext, Bitmap image, int widths, int heights) {
		/*
		 * getResources().getDimensionPixelSize 取出dimens中的值
		 */
		Bitmap bitmap;
		int width = mcContext.getResources().getDimensionPixelSize(widths);
		int height = mcContext.getResources().getDimensionPixelSize(heights);
		/*
		 * ThumbnailUtils.extractThumbnail 创建一个指定大小的缩略图
		 * 
		 * @param source 源文件(Bitmap类型)
		 * 
		 * @param width 压缩成的宽度
		 * 
		 * @param height 压缩成的高度
		 */
		int h = image.getHeight();
		int w = image.getWidth();
		if (h > width || w > height) {
			bitmap = ThumbnailUtils.extractThumbnail(image, width, height);
		} else {
			bitmap = image;
		}
		return bitmap;
	}



	/**
	 * 把Bitmap转换成字节数组
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 把字节数组转换成Bitmap
	 * 
	 * @param temp
	 * @return
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * 把一个Drawable转换成Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 把Bitmap转换成Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable Bitmap_Drawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}


}
