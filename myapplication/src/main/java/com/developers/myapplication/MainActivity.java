package com.developers.myapplication;

import android.os.Bundle;
import android.view.View;

import com.supermax.base.mvp.QsViewPagerActivity;
import com.supermax.base.mvp.model.QsModelPager;

public class MainActivity extends QsViewPagerActivity {

    @Override
    public QsModelPager[] getModelPagers() {
        QsModelPager modelPager1 = new QsModelPager();
        modelPager1.fragment = new HomeFragment();
        modelPager1.title = "首页";
        modelPager1.position = 0;

        QsModelPager modelPager2 = new QsModelPager();
        modelPager2.fragment = new MainFragment();
        modelPager2.title = "测试";
        modelPager2.position = 1;

        QsModelPager modelPager3 = new QsModelPager();
        modelPager3.fragment = new AboutFragment();
        modelPager3.title = "中间";
        modelPager3.position = 2;

        return new QsModelPager[]{modelPager1, modelPager2, modelPager3};    }

    @Override
    public int getTabItemLayout() {
        return 0;
    }

    @Override
    public void initTab(View tabItem, QsModelPager modelPager) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
