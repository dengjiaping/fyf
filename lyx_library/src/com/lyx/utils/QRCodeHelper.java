package com.lyx.utils;

import java.util.Hashtable;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeHelper {
	
	 public static Bitmap createQRCodeImage(String text,int width,int height) {
	        try {
	            if (text == null || "".equals(text) || text.length() < 1) {
	                return null;
	            }
	            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
	                    BarcodeFormat.QR_CODE, width, height, hints);
	            int[] pixels = new int[width * height];
	            for (int y = 0; y < height; y++) {
	                for (int x = 0; x < width; x++) {
	                    if (bitMatrix.get(x, y)) {
	                        pixels[y * width + x] = 0xff000000;
	                    } else {
	                        pixels[y * width + x] = 0xffffffff;
	                    }
	                }
	            }
	            Bitmap bitmap = Bitmap.createBitmap(width, height,
	                    Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
	            return bitmap ;
	        } catch (WriterException e) {
	            e.printStackTrace();
	        }
	        
	        return null ;
	    }
 	 

    

}
