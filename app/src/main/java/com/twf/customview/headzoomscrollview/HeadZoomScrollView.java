package com.twf.customview.headzoomscrollview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.twf.customview.util.LogUtil;

/**
 * android 下拉放大头部图片的ScrollView
 * http://blog.csdn.net/anyfive/article/details/52575262
 * @author twf
 * @date 2017/12/6
 */

public class HeadZoomScrollView extends ScrollView {
    public static final String TAG = HeadZoomScrollView.class.getSimpleName();

    public HeadZoomScrollView(Context context) {
        super(context);
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 记录下拉位置
    private float y = 0f;
    // zoomView 原本的宽高
    private int zoomViewWidth = 0;
    private int zoomViewHeight = 0;

    // 是否正在放大
    private boolean mScaling;
    // 放大的 view，默认为第一个子 view
    private View zoomView;
    public void setZoomView(View zoomView) {
        this.zoomView = zoomView;
    }

    // 滑动放大系数，系数越大，滑动时放大程度越大
    private float mScaleRatio = 0.4f;
    public void setmScaleRatio(float mScaleRatio) {
        this.mScaleRatio = mScaleRatio;
    }
    // 最大的放大倍数
    private float mScaleTimes = 2f;
    public void setmScaleTimes(float mScaleTimes) {
        this.mScaleTimes = mScaleTimes;
    }
    // 回弹时间系数，系数越小，回弹越快
    private float mReplyRatio = 0.5f;
    public void setmReplyRatio(float mReplyRatio) {
        this.mReplyRatio = mReplyRatio;
    }

    //

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     * <p>
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtil.d(TAG, "onFinishInflate");
        // TODO:不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);

        if(getChildAt(0) != null && getChildAt(0) instanceof ViewGroup && zoomView == null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if(vg.getChildCount() > 0) {
                zoomView = vg.getChildAt(0);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG, "onTouchEvent  zoomViewWidth= " + zoomViewWidth + " zoomViewHeight= " + zoomViewHeight);
        // TODO:
        if(zoomViewWidth <=0 || zoomViewHeight <= 0) {
            zoomViewWidth = zoomView.getMeasuredWidth();
            zoomViewHeight = zoomView.getMeasuredHeight();
        }

        if(zoomView == null || zoomViewWidth <= 0 || zoomViewHeight <= 0) {
            return super.onTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG, "ACTION_UP");
                mScaling = false;
                replyView();
                // 会突然恢复至原来大小，很突兀。
//                setZoom(0);
                break;
            case MotionEvent.ACTION_MOVE:

                if(!mScaling) {
                    if(getScrollY() == 0) {
                        y = ev.getY();
                    } else {
                        break;
                    }
                }
                LogUtil.d(TAG, "ACTION_MOVE y= " + y + " ev.getY()= " + ev.getY());
                int distance = (int) (mScaleRatio * (ev.getY() - y));
                if(distance < 0) {
                    break;
                }
                mScaling = true;
                setZoom(distance);
                return true;
            default:
                break;
        }


        return super.onTouchEvent(ev);
    }

    /**
     * 放大 view
     * @param s
     */
    private void setZoom(float s) {
        // TODO:
        float scaleTimes = (float) ((zoomViewWidth + s) / (zoomViewWidth * 1.0));
        if(scaleTimes > mScaleTimes) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = zoomView.getLayoutParams();
        layoutParams.width = (int) (zoomViewWidth + s);
        layoutParams.height = (int) (zoomViewHeight * scaleTimes);

        // 若不设置次 负的left margin，图片放大后会向右下角扩展
//        ((MarginLayoutParams)layoutParams).setMargins(-(layoutParams.width - zoomViewWidth)/2, 0, 0 ,0);
        // 这样设置 margin的效果是图片中心保持不变，图片保持中心向四周扩大
        ((MarginLayoutParams)layoutParams).setMargins(-(layoutParams.width - zoomViewWidth)/2, -(layoutParams.height - zoomViewHeight)/2, 0 ,0);
        zoomView.requestLayout();
//        zoomView.setLayoutParams(layoutParams);
        // 或者调用 requestLayout（）
    }

    /**
     * 回弹
     */
    private void replyView() {
        // TODO:
        final float distance = zoomView.getMeasuredWidth() - zoomViewWidth;
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long)(distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    /**
     * This is called in response to an internal scroll in this view (i.e., the
     * view scrolled its own contents). This is typically as a result of
     * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
     * called.
     *
     * @param l    Current horizontal scroll origin.
     * @param t    Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null) {
            onScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    private OnScrollListener onScrollListener;
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /*滑动监听*/
    public interface OnScrollListener{
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
