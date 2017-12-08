package com.twf.customview.headzoomscrollview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.twf.customview.R;
import com.twf.customview.util.LogUtil;

/**
 *
 * @author twf
 * @date 2017/12/6
 */

public class HeadZoomScrollActivity extends AppCompatActivity {
    public static final String TAG = HeadZoomScrollActivity.class.getSimpleName();
    // TODO: 重写 onCreate 方法错误导致界面无法渲染，界面出不来。
    // 因为这个错误捣鼓了半宿没搞好，衰！
    // 用法参考：
    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_head_zoom_scroll);

        Log.d("TAG", "HeadZoomScrollActivity onCreate");
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Glide.with(this).load("http://seopic.699pic.com/photo/50008/2836.jpg_wh1200.jpg").into(iv);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_zoom_scroll);

        LogUtil.d(TAG, "HeadZoomScrollActivity onCreate");
        ImageView iv = (ImageView) findViewById(R.id.iv);
        // TODO: 开始图片一直不显示，原因是没有添加访问呢 internet 权限！！！
        Glide.with(this).load("http://seopic.699pic.com/photo/50008/2836.jpg_wh1200.jpg").into(iv);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(TAG, "onSaveInstanceState persistentState");
        outPersistentState.putString("key1", "twf");

        super.onSaveInstanceState(outState, outPersistentState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.d(TAG, "onRestoreInstanceState persistentState");
        if (persistentState != null) {
            LogUtil.d(TAG, "onRestoreInstanceState persistentState key1= " + persistentState.get("key1"));
        }
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        Log.d(TAG, "onCreate persistentState");
        if (persistentState != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d(TAG, "onCreate persistentState key1= " + persistentState.get("key1"));
            }
        }
        super.onCreate(savedInstanceState, persistentState);
    }
}
