package com.supermax.base.mvp.fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.supermax.base.R;
import com.supermax.base.common.widget.viewpager.ViewPagerHelper;
import com.supermax.base.common.widget.viewpager.headerpager.HeaderViewPager;
import com.supermax.base.common.widget.viewpager.headerpager.help.MagicHeaderUtils;
import com.supermax.base.mvp.model.QsModelPager;
import com.supermax.base.mvp.presenter.QsPresenter;

/*
 * @Author yinzh
 * @Date   2018/10/18 09:32
 * @Description
 */
public abstract class QsHeaderViewPagerFragment<P extends QsPresenter> extends QsViewPagerFragment<P> implements QsIHeaderViewPagerFragment {

    protected HeaderViewPager headerViewPager;

    @Override
    public int layoutId() {
        return R.layout.super_fragment_header_viewpager;
    }

    @Override
    protected void initTabAndPager(View view) {
        if (view instanceof HeaderViewPager) {
            headerViewPager = (HeaderViewPager) view;
        } else {
            headerViewPager = view.findViewById(R.id.pager);
        }

        ViewGroup tabView = createTabView();
        if (tabView == null) throw new RuntimeException("tabView should not be null!!");
        headerViewPager.setTabsLayout(tabView);
        headerViewPager.initView();

        if (getHeaderLayout() > 0) {
            headerViewPager.addPagerHeaderView(View.inflate(getContext(), getHeaderLayout(), null));
        }
        pager = headerViewPager.getViewPager();
        tabs = headerViewPager.getPagerSlidingTabStrip();
        initTabsValue(tabs);
        initViewPager(getModelPagers(), 3);
    }

    @Override public ViewGroup createTabView() {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.super_layout_tabs, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MagicHeaderUtils.dp2px(getContext(), 48));
        viewGroup.setLayoutParams(lp);
        return viewGroup;
    }

    @Override public void initViewPager(QsModelPager[] modelPagers, int offScreenPageLimit) {
        if (modelPagers != null && modelPagers.length > 0) {
            ViewPagerHelper pagerHelper = new ViewPagerHelper(this, pager, tabs, modelPagers);
            adapter = createPagerAdapter(pagerHelper);
            adapter.setModelPager(modelPagers);
            pager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
            pager.setOffscreenPageLimit(offScreenPageLimit);
            headerViewPager.setPagerAdapter(adapter);
        }
    }


}
