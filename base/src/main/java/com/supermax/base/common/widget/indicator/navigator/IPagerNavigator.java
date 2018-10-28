package com.supermax.base.common.widget.indicator.navigator;

/*
 * @Author yinzh
 * @Date   2018/10/24 16:53
 * @Description: ViewPager 的导航器
 */
public interface IPagerNavigator {

    /**
     * ViewPager 的回调
     */

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixel);


    void onPageSelected(int position);

    void onPageSrolledStateChanged(int state);

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();


    /**
     *
     */
    void onDetachFromMagicIndicator();



}
