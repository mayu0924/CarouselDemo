package com.mayu.carouseldemo.util;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mayu.carouseldemo.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadUtil {
	private static Context mCtx;
	private static DisplayImageOptions roundDisplayOptions;
	private static DisplayImageOptions imageDisplayOptions;
	private static ImageLoaderConfiguration defaultLoaderConfig;

	public static void initialize(Context ctx) {
		mCtx = ctx;
		roundDisplayOptions = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(12)).cacheInMemory(true).cacheOnDisk(true)
				.showImageOnLoading(R.mipmap.ic_launcher).showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher)
				.build();
		imageDisplayOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
//		imageDisplayOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.drawable.default_img)
//				.showImageForEmptyUri(R.drawable.default_img).showImageOnFail(R.drawable.default_img).build();
		defaultLoaderConfig = createConfiguration(StorageUtils.getCacheDirectory(mCtx), imageDisplayOptions);
		ImageLoader.getInstance().init(defaultLoaderConfig);
	}

	public static void displayRoundCornerImage(String imageUri, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageUri, imageView, roundDisplayOptions);
	}
	
	public static void displayImage(String imageUri, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageUri, imageView, imageDisplayOptions);
	}
	
	public static void displayImage(String imageUri, ImageView imageView, ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(imageUri, imageView, imageDisplayOptions, listener);
	}
	
	private static ImageLoaderConfiguration createConfiguration(File cacheDir, DisplayImageOptions displayOpts) {
		return new ImageLoaderConfiguration.Builder(mCtx)
		.diskCacheExtraOptions(480, 800, null)
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		.diskCache(new UnlimitedDiskCache(cacheDir))
		.imageDownloader(new MyImageDownloader(mCtx))
		.defaultDisplayImageOptions(displayOpts)
		.build();
	}
	
	public static void displayCircleImage(String imageUri, ImageView imageView) {
		int roundPixel = imageView.getLayoutParams().width / 2;
		ImageLoader.getInstance().displayImage(
				imageUri,
				imageView,
				new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(roundPixel)).cacheInMemory(true).cacheOnDisk(true).build());
	}

	public static final class MyImageDownloader extends BaseImageDownloader {

		public MyImageDownloader(Context context) {
			super(context);
		}

		public MyImageDownloader(Context context, int connectTimeout, int readTimeout) {
			super(context, connectTimeout, readTimeout);
		}

		@Override
		protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
			String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
			HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			return conn;
		}
	}
	
	/**
	 * 密度Dpi转像素
	 * @param context
	 * @param dip 密度Dpi值
	 * @return 像素值
	 */
	public static int dip2px(Context context, float dip){
	    return (int)(0.5F + dip * getDensity(context));
	}
	
	/**
	 * dp转px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
	
	/**
	 * 获取屏幕密度,如0.75/1.0/1.5
	 * 
	 * @return 密度float值
	 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = getScreenInfo(context);
		return dm.density;
	}
	
	/**
	 * 获取当前机器的屏幕信息对象<br/>
	 * 另外：通过android.os.Build类可以获取当前系统的相关信息
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenInfo(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		
		//int w = dm.widthPixels;//寬度（像素）
		//int h = dm.heightPixels; //高度（像素）
		//float d = dm.density; //密度（0.75 / 1.0 / 1.5）
		//int densityDpi = dm.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
		return dm;
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
