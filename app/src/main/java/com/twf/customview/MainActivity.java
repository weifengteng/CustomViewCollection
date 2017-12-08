package com.twf.customview;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twf.customview.headzoomscrollview.HeadZoomScrollActivity;
import com.twf.customview.imagepull.PullScrollActivity;
import com.twf.customview.util.LogUtil;

/**
 * @author twf
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(TAG, "MainActivity onCreate");
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        LogUtil.d(TAG, "MainActivity onCreate persistentState");
    }

    private void initView() {
    }

    public void onButtonClick(View view) {
        // TODO:
        LogUtil.d(TAG, "onButtonClick");
        Intent intent = new Intent(this, HeadZoomScrollActivity.class);
        startActivity(intent);

    }

    public void doImagePull(View view) {
        LogUtil.d(TAG, "doImagePull");
        Intent intent = new Intent(this, PullScrollActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        LogUtil.d(TAG, "MainActivity onSaveInstanceState outPersistentState");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        LogUtil.d(TAG, "MainActivity onRestoreInstanceState persistentState");
    }
}
