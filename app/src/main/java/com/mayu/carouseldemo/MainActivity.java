package com.mayu.carouseldemo;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mayu.carouseldemo.entity.Wallet;
import com.mayu.carouseldemo.widget.Carousel;
import com.mayu.carouseldemo.widget.CarouselAdapter;
import com.mayu.carouseldemo.widget.CountNumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.circle)
    ImageView mCircle;
    @Bind(R.id.total)
    CountNumber mTotal;
    @Bind(R.id.carousel)
    Carousel mCarousel;

    Wallet mWalletData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWallet();
    }

    private void circleStart() {
        RotateAnimation anim = new RotateAnimation(360f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(600);
        anim.setRepeatCount(2);
        anim.setInterpolator(new LinearInterpolator());
        mCircle.startAnimation(anim);
    }

    private void getWallet() {
        new AsyncTask<Void, Integer, Wallet>() {
            @Override
            protected Wallet doInBackground(Void... voids) {
                Type listType = new TypeToken<Wallet>() {
                }.getType();
                JSONObject obj = readFromSimulate("WALLET").optJSONObject("data");
                mWalletData = new Gson().fromJson(obj.toString(), listType);
                return mWalletData;
            }

            @Override
            protected void onPostExecute(Wallet mWalletData) {
                super.onPostExecute(mWalletData);
                loadData();
            }
        }.execute();
    }

    private void loadData() {
        mTotal.showNumberWithAnimation(Integer.valueOf(mWalletData.accumulatedIncome));
        if (mWalletData.subscribeInfo == null) {
        } else {
            mCarousel.setAdapter(mWalletData.subscribeInfo);
            mCarousel.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(CarouselAdapter<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
        circleStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private JSONObject readFromSimulate(String TAG) {
        String content = readTextFromAssets("simulate_data/" + TAG + ".json");
        try {
            return new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 读取assets中的文本文件内容
     *
     * @param resourceFileName
     * @return
     */
    private String readTextFromAssets(String resourceFileName) {
        AssetManager am = this.getAssets();
        InputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            fis = am.open(resourceFileName);
            os = new ByteArrayOutputStream();

            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            os.flush();
            byte[] byteArray = os.toByteArray();
            return new String(byteArray, "UTF-8");
        } catch (IOException e) {
        } finally {
            try {
                os.close();
                fis.close();
            } catch (IOException e) {
            }
        }

        return null;
    }
}
