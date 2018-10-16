package com.supermax.base.common.widget.viewpager;

import android.view.View;

/*
 * @Author yinzh
 * @Date   2018/10/14 09:56
 * @Description
 */
public interface InnerScroller {
    /**
     * （as its name）
     */
    void triggerOuterScroll();

    /**
     * （as its name）
     * @hide
     */
    void recordScrollPosition(int firstVisibleItem);

    /**
     * Called by OuterScroller. verification inside. And will not directly produce scrolling. It will call performScroll() to produce substantial rolling.
     */
    void syncScroll();

    /**
     * （as its name）
     */
    void adjustEmptyHeaderHeight();

    /**************
     * Methods for use both in and out
     ****************/

    int getInnerScrollY();

    /*********
     * Methods exploded for customization
     *************/
    OuterScroller getOuterScroller();

    /**
     * Everytime when you initialize and innerScroller, you must
     * call register2Outer(), or else this innerScroller would act
     * like ordinary Scroller(ListView/ScrollView).
     */
    void register2Outer(OuterScroller mOuterScroller, int mIndex);

    /**
     * Get the view to receive touch event. Defaults to this.
     */
    View getReceiveView();

    /**
     * （as its name）
     */
    void scrollToTop();

    /**
     * （as its name）
     */
    boolean isScrolling();

    /**
     * Scroll to innerScroller's top.
     */
    void scrollToInnerTop();

    /**
     * Add inner header view
     */
    void addHeaderView(View headerView);

    /**
     * PermissionListener to be implemented to inform OuterScroller
     */
    void onRefresh(boolean isRefreshing);

    /**
     * Customize empty content view
     */
    void setCustomEmptyView(View emptyView);

    /**
     * Customize  empty content view's height.
     */
    void setCustomEmptyViewHeight(int height, int offset);

    /**
     * Customize color of auto completion.
     */
    void setContentAutoCompletionColor(int color);

    View get();
}
