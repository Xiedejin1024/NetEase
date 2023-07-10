package com.example.netease.splash.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.netease.R;

@SuppressLint("AppCompatCustomView")
public class RingTextView extends View {

    private float inner_radius;
    private String content = "跳过";
    private TextPaint mTextPaint;
    private float storke_wid = 10;//到边边的距离
    private float mMeasureText;
    private Paint mInnerPaint;
    private Point mPoint;
    private Paint mArcPaint;
    private RectF mArcRectf;
    private int mWidth;
    private int mProgress;
    private Rect mRect = new Rect();
    public RingListener mRingListener;
    private int mInnerColor;
    private int mOutColor;

    public void setmRingListener(RingListener mRingListener) {
        this.mRingListener = mRingListener;
    }

    public RingTextView(Context context) {
        super(context);
        initPanit();
    }

    public RingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RingTextView);
        mInnerColor = array.getColor(R.styleable.RingTextView_RingInnerColor, Color.BLUE);
        mOutColor = array.getColor(R.styleable.RingTextView_RingOutColor, Color.RED);
        array.recycle();

        initPanit();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.RED);
        canvas.drawCircle(mPoint.x, mPoint.y, inner_radius, mInnerPaint);
        canvas.save();
        canvas.rotate(-90, mPoint.x, mPoint.y);
        canvas.drawArc(mArcRectf, 0, mProgress, false, mArcPaint);
        canvas.restore();
        int textY = mWidth / 2 + (mRect.height() / 2 - mRect.bottom);
        canvas.drawText(content, storke_wid * 2, textY, mTextPaint);
    }

    private void initPanit() {
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mMeasureText = mTextPaint.measureText(content);

        mWidth = (int) (mMeasureText + storke_wid * 4);
        mPoint = new Point(mWidth / 2, mWidth / 2);
        inner_radius = (mMeasureText + storke_wid * 2) / 2;

        mInnerPaint = new Paint();
        mInnerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setColor(mInnerColor);

        mArcPaint = new Paint();
        mArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(mOutColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(storke_wid);

        mArcRectf = new RectF(storke_wid / 2, storke_wid / 2, mWidth - storke_wid / 2, mWidth - storke_wid / 2);

        mTextPaint.getTextBounds(content, 0, content.length(), mRect);//字体的区域位置

    }

    public void setProgress(int total, int progress) {
        this.mProgress = (450 / total) * progress;
        invalidate();
    }

    public interface RingListener {
        void onclick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                if (mRingListener != null) {
                    mRingListener.onclick();
                }
                break;

        }
        return true;
    }
}
