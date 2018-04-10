package com.twf.customview.customview2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.twf.customview.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author twf
 * @date 2018/4/5
 */

public class CustomView2Activity extends AppCompatActivity {

    @BindView(R.id.lv1)
    ListView mCenterListView;
    @BindView(R.id.lv2)
    ListView mLeftListView;
    @BindView(R.id.lv3)
    ListView mRightListView;

    private String[] mCenterDataArray;
    private String[] mLeftDataArray;
    private String[] mRightDataArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview2);
        ButterKnife.bind(this);

        initDataList();
        bindData(mCenterListView, mCenterDataArray);
        bindData(mLeftListView, mLeftDataArray);
        bindData(mRightListView, mRightDataArray);
    }

    private void bindData(ListView listView, String[] dataSource) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dataSource);
        listView.setAdapter(adapter);
    }

    private void initDataList() {
        mCenterDataArray = new String[26];
        mLeftDataArray = new String[26];
        mRightDataArray = new String[26];

        for (int i=0; i<26; i++) {
            char ch = (char)('a' + i);

            mCenterDataArray[i] = String.valueOf(ch);
            mLeftDataArray[i] = String.valueOf(ch);
            mRightDataArray[i] = String.valueOf(ch);
        }
    }

}
