package com.twf.customview.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.twf.customview.R;

/**
 * @author twf
 * @date 2018/4/10
 */

public class CustomWaveView extends View {
    private Paint mBgCirclePaint;
    private Paint mWavePaint;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private int mCircleColor;
    private int mCircleBgColor;
    private int mProgressWaveColor;
    private int mTextColor;
    private int mTextSize;

    private float waveRipple = 0;
    private int radius = 300;
    private Path mPath;
    private boolean isRunning;
    Canvas mCanvas;
    Bitmap mBitmap;
    int width, height;
    PointF startP, nextP, threeP, fourP, endP;
    PointF controllerP1, controllerP2, controllerP3, controllerP4;

    private int currentProgress = 0;
    private int maxProgress = 100;
    ValueAnimator animator = null;
    private float depth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomWaveView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public CustomWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.custom_wave_view_attr);
        mCircleColor = ta.getColor(R.styleable.custom_wave_view_attr_circle_color, getResources().getColor(android.R.color.black));
        mCircleBgColor = ta.getColor(R.styleable.custom_wave_view_attr_circle_bg_color, getResources().getColor(android.R.color.white, null));
        mProgressWaveColor = ta.getColor(R.styleable.custom_wave_view_attr_progress_wave_color, getResources().getColor(android.R.color.holo_blue_dark, null));
        mTextColor = ta.getColor(R.styleable.custom_wave_view_attr_progress_text_color, getResources().getColor(android.R.color.black, null));
        mTextSize = (int) ta.getDimension(R.styleable.custom_wave_view_attr_progress_text_size, 30f);

        ta.recycle();

        initPaint();
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = radius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = radius * 2;
        }

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        reset();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawCircle(width/2, height/2, radius, mBgCirclePaint);

        mPath.reset();
        mPath.moveTo(startP.x, startP.y);
        mPath.quadTo(controllerP1.x, controllerP1.y, nextP.x, nextP.y);
        mPath.quadTo(controllerP2.x, controllerP2.y, threeP.x, threeP.y);
        mPath.quadTo(controllerP3.x, controllerP3.y, fourP.x, fourP.y);
        mPath.quadTo(controllerP4.x, controllerP4.y, endP.x, endP.y);
        mPath.lineTo(endP.x, height);
        mPath.lineTo(-width, height);

        mCanvas.drawPath(mPath, mWavePaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);

        canvas.drawCircle(width/2, height/2, radius, mCirclePaint);

        if (currentProgress <= 0) {
            waveRipple = 0;
        } else if (currentProgress > 0 && currentProgress < maxProgress) {
            waveRipple = 35;
        } else if (currentProgress == maxProgress) {
            waveRipple = 0;
        } else if (currentProgress > maxProgress && animator.isRunning()) {
            currentProgress = maxProgress;
            animator.cancel();
            animator = null;
        }

        String text = currentProgress + "%";
        canvas.drawText(text, (width/2 - mTextSize), (height/2 + mTextSize/2), mTextPaint);
    }

    private void initPaint() {
        mBgCirclePaint = new Paint();
        mBgCirclePaint.setAntiAlias(true);
        mBgCirclePaint.setColor(mCircleBgColor);
        mBgCirclePaint.setStyle(Paint.Style.FILL);

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setColor(mProgressWaveColor);
        mWavePaint.setStyle(Paint.Style.FILL);
        // 使用 XferMode 获取重叠部分
        mWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);
    }

    private void reset() {
        startP = new PointF(-width, height);
        nextP = new PointF(-width/2, height);
        threeP = new PointF(0, height);
        fourP = new PointF(width / 2, height);
        endP = new PointF(width, height);

        controllerP1 = new PointF(-width / 4, height);
        controllerP2 = new PointF(-width * 3 / 4, height);
        controllerP3 = new PointF(width / 4, height);
        controllerP4 = new PointF(width * 3 / 4, height);
    }

    public void start() {
        if (animator == null) {
            reset();
            startAnimator();
        }
    }

    private void startAnimator() {
        animator = ValueAnimator.ofFloat(startP.x, 0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startP.x = (Float) animation.getAnimatedValue();
                startP = new PointF(startP.x, height - depth);
                nextP = new PointF(startP.x + width / 2, height - depth);
                threeP = new PointF(nextP.x + width/2, height - depth);
                fourP = new PointF(threeP.x + width / 2, height - depth);
                endP = new PointF(fourP.x + width / 2, height - depth);

                controllerP1 = new PointF(startP.x + width / 4, height - depth + waveRipple);
                controllerP2 = new PointF(nextP.x + width / 4, height - depth - waveRipple);
                controllerP3 = new PointF(threeP.x + width / 4, height - depth + waveRipple);
                controllerP4 = new PointF(fourP.x + width/4, height - depth - waveRipple);
                invalidate();
            }
        });

        animator.start();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        depth = (float)currentProgress / (float)maxProgress * (float)height;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
