package com.mayu.carouseldemo.application;

import android.app.Application;

import com.mayu.carouseldemo.util.ImageLoadUtil;

import java.lang.reflect.Field;



/**
 * @author mayu
 */
public class AppContext extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoadUtil.initialize(this);
	}
}
