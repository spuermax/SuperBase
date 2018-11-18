package com.supermax.base.common.widget.indicator;

import android.support.v4.view.ViewPager;

/**
 * @Author yinzh
 * @Date 2018/11/18 11:18
 * @Description ViewPager的page监听  初始绑定。
 */
public class ViewPagerIndicatorHelper {
    public static void bind(final MagicIndicator indicator, ViewPager pager){
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                indicator.onPageScrolled(i, v, i1);
            }

            @Override
            public void onPageSelected(int i) {
                indicator.onPageSelected(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                indicator.onPageScrollStateChanged(i);
            }
        });
    }
}
