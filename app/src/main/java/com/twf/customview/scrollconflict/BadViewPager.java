package com.twf.customview.scrollconflict;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * @author twf
 * @date 2018/4/8
 */

public class BadViewPager extends ViewPager {
    private static final String TAG = "BadViewPager";

    private int mLastXIntercept;
    private int mLastYIntercept;


    public BadViewPager(Context context) {
        super(context);
    }

    public BadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
//
//        switch (action) {
//            case ACTION_DOWN:
//                intercepted = false;
//                super.onInterceptTouchEvent(ev);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = x - mLastXIntercept;
//                int deltaY = y - mLastYIntercept;
//
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    intercepted = true;
//                } else {
//                    intercepted = false;
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                intercepted = false;
//                break;
//            default:
//                break;
//        }
//
//        mLastXIntercept = x;
//        mLastYIntercept = y;
//        Log.e(TAG, "intercepted = " + intercepted);
//        return intercepted;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }
}
