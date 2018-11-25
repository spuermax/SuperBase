package com.supermax.base.common.widget.viewpager.headerpager.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @Author yinzh
 * @Date   2018/10/18 09:39
 * @Description  A LinearLayout, its onSizeChanged() in mSizeChangedListener will be called When sizeChanged.
 */
public class SizeSensitiveLinearLayout extends LinearLayout{

    public static final String TAG = "SizeSensitiveLinearLayout";

    private SizeChangedListener mSizeChangedListener;

    public SizeSensitiveLinearLayout(Context context) {
        super(context);
    }

    public SizeSensitiveLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mSizeChangedListener != null) {
            mSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    public void setOnSizeChangedListener(SizeChangedListener sizeChangedListener) {
        mSizeChangedListener = sizeChangedListener;
    }

    public interface SizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.UNSPECIFIED;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
