package com.twf.customview.customview1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;

import com.twf.customview.R;

/**
 * 动画效果来自于这里
 * https://juejin.im/post/5a9241fef265da4e721c96f2?utm_source=gold_browser_extension
 *
 * @author twf
 * @date 2018/4/1
 */

public class TwoIncreasingRadiusActivity extends Activity {

    private TwoIncreasingRadiusCircleView view;
    boolean isRunning;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twoincreasingradius);
        view = findViewById(R.id.two_increasing_circle_img);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.startAnimation();
            }
        });
    }

    public void doOnClickStartBtn(View v) {
        if (!isRunning) {
            view.setVisibility(View.VISIBLE);
            view.performClick();
            isRunning = true;
        }

    }
}
