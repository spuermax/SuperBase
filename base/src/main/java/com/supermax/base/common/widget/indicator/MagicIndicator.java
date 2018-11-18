package com.supermax.base.common.widget.indicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.supermax.base.common.widget.indicator.navigator.impl.IPagerNavigator;

/**
 * @Author yinzh
 * @Date 2018/10/24 16:42
 * @Description: MagicIndicator 的入口
 * 链接导航器  绑定ViewPager
 */
public class MagicIndicator extends FrameLayout {
    private IPagerNavigator mNavigator;//指示器，导航器

    public MagicIndicator(@NonNull Context context) {
        super(context);
    }

    public MagicIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MagicIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNavigator != null) {
            mNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (mNavigator != null) {
            mNavigator.onPageSelected(position);
        }
    }


    public void onPageScrollStateChanged(int state) {
        if (mNavigator != null) {
            mNavigator.onPageScrollStateChanged(state);
        }
    }

    public IPagerNavigator getNavigator() {
        return mNavigator;
    }


    public void setNavigator(IPagerNavigator navigator) {
        if (mNavigator == navigator) {
            return;
        }

        if (mNavigator != null) {
            mNavigator.onDetachFromMagicIndicator();
        }

        mNavigator = navigator;
        removeAllViews();

        if (mNavigator instanceof View) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView((View) mNavigator, lp);
            mNavigator.onAttachToMagicIndicator();
        }

    }

}
