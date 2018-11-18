package com.supermax.base.common.widget.indicator.indicator;

import com.supermax.base.common.widget.indicator.model.PositionData;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/11/15 11:21
 * @Description: 抽象的viewpager指示器，适用于CommonNavigator
 */
public interface IPagerIndicator {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void onPositionDataProvide(List<PositionData> dataList);
}
