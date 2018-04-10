package com.twf.customview.customview1;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author twf
 * @date 2018/4/1
 */

public class TwoIncreasingRadiusCircleView extends AppCompatImageView {
    private ValueAnimator mAnimator;
    private float factor;
    private int width, height, leftX, rightX;
    private int distance;
    private int margin = 160;
    private int radius = 80;
    private float y;
    private Paint mPaint;

    public TwoIncreasingRadiusCircleView(Context context) {
        super(context);
        initData();
    }

    public TwoIncreasingRadiusCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public TwoIncreasingRadiusCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        leftX = margin;
        rightX = width - margin;

        y = height / 2.0f;

        distance = width - 2 * (margin + radius);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(leftX, y, radius * factor, mPaint);

        canvas.drawCircle(rightX, y, radius * (1-factor), mPaint);

        mPaint.setStrokeWidth(5);

        canvas.drawLine(margin, y, margin + radius + ((radius + distance) * (1-factor)), y, mPaint);

        super.onDraw(canvas);
    }

    public void startAnimation() {
        mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                factor = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.setDuration(1500);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.start();
    }
}
