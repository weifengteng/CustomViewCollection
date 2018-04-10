package com.twf.customview.scrollconflict;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.twf.customview.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 *https://www.jianshu.com/p/982a83271327
 *
 * @author twf
 * @date 2018/4/8
 */

public class ScrollConflictActivity extends Activity {
    private BadViewPager mViewPager;
    private List<View> mViews;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_confict);

        context = this;
        initViews();
        initData(true);

    }

    protected void initViews() {
        mViewPager = findViewById(R.id.bad_viewpager);
        mViews = new ArrayList<>();

    }

    protected void initData(final boolean isListView) {
        // TODO:
        Flowable.just("view1", "view2", "view3", "view4").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                View view = null;
                if (isListView) {
//                    ListView listView = new ListView(context);
                    ListView listView = new FixListView(context);
                    final ArrayList<String> datas = new ArrayList<>();

                    Flowable.range(0, 50).subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            datas.add("data" + integer);
                        }
                    });

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, datas);
                    listView.setAdapter(adapter);
                    view = listView;
                } else {
                    //
                    TextView textView = new TextView(context);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(s);
                    textView.setClickable(true);
                    view = textView;
                }

                mViews.add(view);
            }
        });

        mViewPager.setAdapter(new BasePagerAdapter(mViews));
    }

}
