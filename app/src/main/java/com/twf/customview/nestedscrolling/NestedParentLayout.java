package com.twf.customview.nestedscrolling;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.support.v4.view.NestedScrollingParent;
import android.widget.FrameLayout;

/**
 * @author twf
 * @date 2018/4/9
 */

public class NestedParentLayout extends FrameLayout implements NestedScrollingParent {
    private static final String TAG = "NestedParentLayout";
    private NestedScrollingParentHelper mScrollingParentHelper;

    public NestedParentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        Log.d(TAG, "onStartNestedScroll() called with: " + "child = [" + child + "], target = [" + target + "], nestedScrollAxes = [" + axes + "]");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        mScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        mScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        final View child = target;
        // 实现 当子View滑动到父View边缘时，会带动父View一起滑动 的效果。
        if (dx > 0) {
            if (child.getRight() + dx > getWidth()) {
                dx = child.getRight() + dx - getWidth();
                offsetLeftAndRight(dx);
                consumed[0] += dx;
            }
        } else {
            if (child.getLeft() + dx < 0) {
                dx = dx + child.getLeft();
                offsetLeftAndRight(dx);
                Log.d(TAG, "dx: " + dx);
                consumed[0] += dx;
            }
        }

        if (dy > 0) {
            if (child.getBottom() + dy > getHeight()) {
                dy = child.getBottom() + dy - getHeight();
                offsetTopAndBottom(dy);
                consumed[1] += dy;
            }
        } else {
            if (child.getTop() + dy < 0) {
                dy = dy + child.getTop();
                offsetTopAndBottom(dy);
                Log.d(TAG, "dy: " + dy);
                consumed[1] += dy;
            }
        }
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }
}
