package com.lyx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderUtils {

	private static ImageLoader loader = null;
	private static int defaultImage = 0 ;

	public static void init(Context context,int defaultImage) {
		if (ImageLoader.getInstance().isInited()) {
			ImageLoaderUtils.loader = ImageLoader.getInstance() ;
			ImageLoaderUtils.defaultImage = defaultImage ;
			return;
		}
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(new WeakMemoryCache())
				// 防止内存OOM
				.memoryCacheSize(4 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config); // Initialize ImageLoader with configuration.
		ImageLoaderUtils.loader = ImageLoader.getInstance() ;
		ImageLoaderUtils.defaultImage = defaultImage ;
	}
	
	public static void displayPicWithAutoStretch(String uri,ImageView imageView){
		loader.displayImage(uri, imageView, getAutoStretchOptions()) ;
	}
	
	
	private static DisplayImageOptions autoStretchOptions = null ;
	/**
     * 可实现根据原图大小自动拉伸控件
     * @return
     */
    private static DisplayImageOptions getAutoStretchOptions() {
        if(autoStretchOptions == null){
        	autoStretchOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true)
                    .resetViewBeforeLoading(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showStubImage(defaultImage).build();
        }
        return autoStretchOptions;
    }

}
