package com.supermax.base.common.widget.indicator.navigator.impl;

/**
 * @Author yinzh
 * @Date   2018/10/24 16:53
 * @Description: ViewPager 的导航器
 */
public interface IPagerNavigator {

    /***************ViewPager 的回调********************/

    /**
     * @param position 当前页
     * @param positionOffset 移动页百分比
     * @param positionOffsetPixel 移动像素点
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixel);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();


    /**
     *当IPagerNavigator从MagicIndicator上移除时调用
     */
    void onDetachFromMagicIndicator();

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    void notifyDataSetChanged();


}
