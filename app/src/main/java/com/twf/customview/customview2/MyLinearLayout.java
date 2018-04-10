package com.twf.customview.customview2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * http://www.voidcn.com/article/p-chytwunw-mm.html
 *
 * @author twf
 * @date 2018/4/5
 */

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int width = getWidth() / getChildCount();
        int height = getHeight();

        int count = getChildCount();
        float eventX = event.getX();

        if (eventX < width) {
            // setLocation方法是为了设置当前的触点的坐标，这个触点坐标系是基于当前触摸的子 view 的坐标系，原点位于当前子 view 的左上角。
            // 需要使用 setLocation 的根本原因是父 ViewGroup 和子 view 的触摸点坐标系不同。
            event.setLocation(width/2, event.getY());
            getChildAt(0).dispatchTouchEvent(event);
            return true;
        } else if (eventX > width && eventX < 2 * width) {
            // TODO:
            float eventY = event.getY();
            if (eventY < height / 2) {
                event.setLocation(width/2, event.getY());
                for (int i=0; i<count; i++) {
                    View child = getChildAt(i);
                    child.dispatchTouchEvent(event);
                }
                return true;

            } else if (eventY > height / 2) {
                event.setLocation(width/2, event.getY());
                getChildAt(1).dispatchTouchEvent(event);
                return true;
            }

        } else if (eventX > 2 * width) {
            // TODO:
            event.setLocation(width/2, event.getY());
            getChildAt(2).dispatchTouchEvent(event);
            return true;
        }

        return true;
    }
}
