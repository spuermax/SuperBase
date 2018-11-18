package com.supermax.base.common.widget.indicator.navigator.impl;

/**
 * @Author yinzh
 * @Date 2018/11/16 11:54
 * @Description
 * 可测量内容区域的指示器标题
 */
public interface IMeasurablePagerTitleView extends IPagerTitleView{
    int getContentLeft();

    int getContentTop();

    int getContentRight();

    int getContentBottom();
}
