package com.mayu.carouseldemo.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

/**
 * Created by mayu on 15/7/29,下午5:09.
 */
public class CountNumber extends TextView {
    //动画时长 ms
    int duration = 1500;
    float number;
    public CountNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void showNumberWithAnimation(int number) {
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator=ObjectAnimator.ofInt(this,"number",0,number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }
    public float getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
        setText(String.valueOf(number));
    }
}