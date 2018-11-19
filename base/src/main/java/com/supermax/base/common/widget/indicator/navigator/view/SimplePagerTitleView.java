package com.supermax.base.common.widget.indicator.navigator.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

import com.supermax.base.common.utils.CommonUtils;
import com.supermax.base.common.widget.indicator.navigator.impl.IMeasurablePagerTitleView;

/**
 * @Author yinzh
 * @Date 2018/11/19 16:00
 * @Description
 */
public class SimplePagerTitleView extends TextView implements IMeasurablePagerTitleView{

    protected int mSelectedColor;
    protected int mNormalColor;

    public SimplePagerTitleView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        int padding = CommonUtils.dip2px(context, 10);
        setPadding(padding, 0, padding, 0);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }



    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 - contentHeight / 2);
    }

    @Override
    public int getContentRight() {
        Rect bound = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 + contentHeight / 2);    }

    @Override
    public void onSelected(int index, int totalCount) {
        setTextColor(mSelectedColor);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        setTextColor(mNormalColor);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }
}
