package com.lyx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.text.TextUtils;

public class BitmapUtil {
	
	public static final String TAG = "BitmapUtil";
	
	public static Bitmap big(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(1.5f, 1.5f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}
	
	public static Bitmap small(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(0.8f, 0.8f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}
	
	/**返回固定大小bitmap*/
	public static Bitmap fix(String path,int w,int h){
		
		if(TextUtils.isEmpty(path))
			return null;
		BitmapFactory.Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int imageH = options.outHeight;
		int imageW = options.outWidth;

		int scaleX = imageH / w;
		int scaleY = imageW / h;
		int scale = 1;
		if (scaleX >= scaleY & scaleY >= 1) {
			scale = scaleX;
		}
		if (scaleY >= scaleX & scaleX >= 1) {
			scale = scaleY;
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeFile(path, options);
		
	}
	/**返回固定大小bitmap*/
	public static Bitmap fix(Context context , int rid,int w,int h){
		BitmapFactory.Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), rid, options);
		int imageH = options.outHeight;
		int imageW = options.outWidth;
		
		int scaleX = imageH / w;
		int scaleY = imageW / h;
		int scale = 1;
		if (scaleX >= scaleY & scaleY >= 1) {
			scale = scaleX;
		}
		if (scaleY >= scaleX & scaleX >= 1) {
			scale = scaleY;
		}
		
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeResource(context.getResources(), rid, options);
		
	}
	
}
