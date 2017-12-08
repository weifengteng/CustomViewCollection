package com.twf.customview.imagepull;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;

import com.twf.customview.R;
import com.twf.customview.util.LogUtil;

/**高仿墨迹天气下拉拉伸图片
 * http://blog.csdn.net/wu928320442/article/details/44198157
 * Created by twf on 2017/12/8.
 */

public class PullScrollView extends ScrollView implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String TAG = PullScrollView.class.getSimpleName();
    private View mView;
    private int mSrcTopMargin;
    private float mLastY;
    private float mOffsetY;

    public PullScrollView(Context context) {
        super(context);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewTreeObserver observer = getViewTreeObserver();
        if(null != observer) {
            observer.addOnGlobalLayoutListener(this);
        }
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mView = findViewById(R.id.pull_img);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        LogUtil.d(TAG, "action = " + action + ", y = " + y);
        MarginLayoutParams params = (MarginLayoutParams) mView.getLayoutParams();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算滑动 Y 方向偏移值
                mOffsetY = y - mLastY;
                // 向下移动
                if(mOffsetY > 0) {
                    // 如果图片已经完全显示出来，不再向下拉伸
                    if(params.topMargin == 0) {
                        return super.onTouchEvent(ev);
                    }
                    // TODO: 不理解此处的写法
                    LogUtil.d(TAG, "getScrollY()= " + getScrollY());
                    if(getScrollY() != 0) {
                        return super.onTouchEvent(ev);
                    }
                    // 可以下拉图片的情况
                    params.topMargin += mOffsetY /10;
                    LogUtil.d(TAG, "topMargin= " + params.topMargin + ",mLastY=" + mLastY + ",y=" + y + ",mOffsetY= " + mOffsetY);
                    if(params.topMargin >= 0) {
                        params.topMargin = 0;
                    }
                    mView.requestLayout();
                    invalidate();
                }
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
                //不和原始 Margin 偏移一样的时候
                if(params.topMargin != -mSrcTopMargin) {
                    LogUtil.d(TAG, "moveY= " + (mSrcTopMargin + params.topMargin));
                    ObjectAnimator animator = ObjectAnimator.ofInt(this, "moveY", params.topMargin, -mSrcTopMargin);
                    animator.setDuration(200);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置移动中的 Y 值
     * @param value
     */
    public void setMoveY(int value) {
        // TODO:
        MarginLayoutParams params = (MarginLayoutParams) mView.getLayoutParams();
        params.topMargin = value;
        LogUtil.d(TAG, "topMargin= " + params.topMargin);
        mView.setLayoutParams(params);
        invalidate();
    }

    /**
     * Callback method to be invoked when the global layout state or the visibility of views
     * within the mView tree changes
     *
     * http://blog.csdn.net/lihappyangel/article/details/52870784
     * 利用ViewTreeObserver的OnGlobalLayoutListener
     优点：不需要额外的测量过程
     缺点：只有在布局加载完成后，才能得到宽和高
     OnGlobalLayoutListener是ViewTreeObserver的内部类，当一个视图树的布局发生改变时，可以被ViewTreeObserver监听到，这是一个注册监听视图树的观察者(observer)，在视图树的全局事件改变时得到通知。ViewTreeObserver不能直接实例化，而是通过getViewTreeObserver()获得。
     其中，我们可以利用OnGlobalLayoutListener来获得一个视图的真实高度，但是需要注意的是OnGlobalLayoutListener会被多次触发，因此在得到了高度之后，要将OnGlobalLayoutListener清除掉。
     */
    @Override
    public void onGlobalLayout() {
        MarginLayoutParams params = (MarginLayoutParams) mView.getLayoutParams();
        mSrcTopMargin = - params.topMargin;
        LogUtil.d(TAG, "mSrcTopMargin= " + mSrcTopMargin);
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }
}
