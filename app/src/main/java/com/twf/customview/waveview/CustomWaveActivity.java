package com.twf.customview.waveview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twf.customview.R;

/**
 *
 * https://blog.csdn.net/u013087553/article/details/68490170
 * 需要使用的知识大概有自定义view、贝塞尔曲线、valueAnimator（属性动画）、Xfermode等
 * @author twf
 * @date 2018/4/10
 */

public class CustomWaveActivity extends AppCompatActivity {

    private CustomWaveView waveView;
    private int currentProgress = 0;
    private int maxProgress = 100;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    waveView.start();
                    waveView.setCurrentProgress(currentProgress);
                    currentProgress++;
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutsom_wave_view);
        waveView = findViewById(R.id.customcirclewaveview);
        waveView.setRadius(150);
        waveView.setMaxProgress(maxProgress);
        waveView.setCurrentProgress(currentProgress);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < maxProgress) {
                    try{
                        Thread.sleep(100);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        waveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetProgress();
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void resetProgress() {
        currentProgress = 0;
    }
}
