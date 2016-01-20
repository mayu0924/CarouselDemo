package com.mayu.carouseldemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mayu.carouseldemo.R;
import com.mayu.carouseldemo.roundImageView.RoundedImageView;
import com.mayu.carouseldemo.util.ImageLoadUtil;

public class CarouselItem extends FrameLayout
	implements Comparable<CarouselItem> {

	private RoundedImageView mImage;
    private TextView mIncoming, mCode;

	private int index;
	private float currentAngle;
	private float itemX;
	private float itemY;
	private float itemZ;
	private boolean drawn;

	// It's needed to find screen coordinates
	private Matrix mCIMatrix;

	public CarouselItem(Context context) {

		super(context);

		LayoutParams params =
				new LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.MATCH_PARENT);

		this.setLayoutParams(params);

	  	LayoutInflater inflater = LayoutInflater.from(context);
		View itemTemplate = inflater.inflate(R.layout.item_carousel, this, true);

		mImage = (RoundedImageView)itemTemplate.findViewById(R.id.item_image);
        mIncoming = (TextView) itemTemplate.findViewById(R.id.inComing);
        mCode = (TextView) itemTemplate.findViewById(R.id.code);
	}


	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}


	public void setCurrentAngle(float currentAngle) {

		if(index == 0 && currentAngle > 5){
			Log.d("", "");
		}

		this.currentAngle = currentAngle;
	}

	public float getCurrentAngle() {
		return currentAngle;
	}

	public int compareTo(CarouselItem another) {
		return (int)(another.itemZ - this.itemZ);
	}

	public void setItemX(float x) {
		this.itemX = x;
	}

	public float getItemX() {
		return itemX;
	}

	public void setItemY(float y) {
		this.itemY = y;
	}

	public float getItemY() {
		return itemY;
	}

	public void setItemZ(float z) {
		this.itemZ = z;
	}

	public float getItemZ() {
		return itemZ;
	}

	public void setDrawn(boolean drawn) {
		this.drawn = drawn;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setImageBitmap(Bitmap bitmap){
		mImage.setImageBitmap(bitmap);
	}

	public void setImageDrawable(Drawable drawable){
		mImage.setImageDrawable(drawable);
	}

    public void setImageUrl(String url){
		ImageLoadUtil.displayImage(url, mImage);
    }

	public RoundedImageView getImageView() {
		return this.mImage;
	}

    public TextView getIncoming() {
        return mIncoming;
    }

    public void setIncoming(String incoming) {
        this.mIncoming.setText(incoming);
    }

    public TextView getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode.setText("分红码："+code);
    }

    Matrix getCIMatrix() {
		return mCIMatrix;
	}

	void setCIMatrix(Matrix mMatrix) {
		this.mCIMatrix = mMatrix;
	}

}
