package com.twf.customview.nestedscrolling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twf.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://blog.csdn.net/mchenys/article/details/51541306
 *
 * @author twf
 * @date 2018/4/8
 */

public class NestedScrollingActivity1 extends AppCompatActivity {

    private List<String> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscrolling_layout1);

        initData(1);
        initView();
    }

    private void initView() {
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(R.drawable.feed_fengfeng);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        for (int i = 0; i < 20; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(tab.getPosition() + 1);
                setScrollViewContent();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setScrollViewContent();
    }

    /**
     * 刷新 Scrollview的内容
     */
    private void setScrollViewContent() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_sc_content);
        layout.removeAllViews();
        for (int i=0; i < mData.size(); i++) {
            View view = View.inflate(NestedScrollingActivity1.this, android.R.layout.simple_list_item_1, null);
            ((TextView)view.findViewById(android.R.id.text1)).setText(mData.get(i));
            layout.addView(view, i);
        }
    }

    private void initData(int pager) {
        //
        mData = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            mData.add("pager" + pager + "第" + i + "个 item");
        }
    }
}
