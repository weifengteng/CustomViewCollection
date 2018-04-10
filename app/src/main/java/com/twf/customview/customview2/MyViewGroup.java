package com.twf.customview.customview2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * https://blog.csdn.net/OONullPointerAlex/article/details/50243557
 *
 * @author twf
 * @date 2018/4/8
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0; i<3; i++) {
            int width = getWidth()/3;
            getChildAt(i).layout(i * width, 0, (i+1) * width, getHeight());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("onInterceptTouchEvent: " + ev.getAction());
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int width = getWidth() / getChildCount();
        int evX = (int) event.getX();
        if (evX < width) {
            System.out.println("第一区域");
            getChildAt(0).dispatchTouchEvent(event);
        } else if (evX >= width && evX < 2 * width) {
            System.out.println("第二区域");
            int evY = (int) event.getY();
            event.setLocation(event.getX() - width, event.getY());
            if (evY > getHeight() / 2) {
                for (int i=0; i<3;i++) {
                    getChildAt(i).dispatchTouchEvent(event);
                }
            } else {
                getChildAt(1).dispatchTouchEvent(event);
            }
        } else {
            System.out.println("第三区域");
            event.setLocation(event.getX() - 2*width, event.getY());
            getChildAt(2).dispatchTouchEvent(event);
        }

        return true;
    }
}
