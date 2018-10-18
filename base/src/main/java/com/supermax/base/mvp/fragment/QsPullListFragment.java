package com.supermax.base.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.supermax.base.R;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.listview.LoadingFooter;
import com.supermax.base.common.widget.ptr.PtrDefaultHandler;
import com.supermax.base.common.widget.ptr.PtrFrameLayout;
import com.supermax.base.common.widget.ptr.PtrHandler;
import com.supermax.base.common.widget.ptr.PtrUIHandler;
import com.supermax.base.common.widget.ptr.header.BeautyCircleRefreshHeader;
import com.supermax.base.common.widget.viewpager.headerpager.InnerScroller;
import com.supermax.base.common.widget.viewpager.headerpager.base.OuterScroller;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/18 11:22
 * @Description
 */
public abstract class QsPullListFragment<P extends QsPresenter, D> extends QsListFragment<P, D> implements QsIPullToRefresh{

    public static final byte    LOAD_WHEN_SCROLL_TO_BOTTOM = 0;
    public static final byte    LOAD_WHEN_SECOND_TO_LAST   = 1;
    private             boolean canLoadingMore             = true;
    private PtrFrameLayout mPtrFrameLayout;
    protected LoadingFooter mLoadingFooter;

    @Override public int layoutId() {
        return (!isOpenViewState() && (getTopLayout() > 0 || getBottomLayout() > 0)) ? R.layout.super_fragment_pull_listview_with_top_bottom : R.layout.super_fragment_pull_listview;
    }

    @Override public int getFooterLayout() {
        return R.layout.super_loading_footer;
    }

    @Override public PtrUIHandler getPtrUIHandlerView() {
        return new BeautyCircleRefreshHeader(getContext());
    }

    @Override protected View initView(LayoutInflater inflater) {
        View view = super.initView(inflater);
        initPtrFrameLayout(view);
        return view;
    }

    @Override protected void initListView(LayoutInflater inflater, View view) {
        super.initListView(inflater, view);
        View footerView = getFooterView();
        if (footerView instanceof LoadingFooter) {
            mLoadingFooter = (LoadingFooter) footerView;
        } else if (footerView != null) {
            mLoadingFooter = footerView.findViewById(R.id.loading_footer);
        }
    }

    private void initPtrFrameLayout(View view) {
        if (view instanceof PtrFrameLayout) {
            mPtrFrameLayout = (PtrFrameLayout) view;
        } else {
            mPtrFrameLayout = view.findViewById(R.id.swipe_container);
        }
        if (mPtrFrameLayout == null) throw new RuntimeException("PtrFrameLayout is not exit or its id not 'R.id.swipe_container' in current layout!!");
        PtrUIHandler handlerView = getPtrUIHandlerView();
        mPtrFrameLayout.setHeaderView((View) handlerView);
        mPtrFrameLayout.addPtrUIHandler(handlerView);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh();
            }
        });
    }


    /**
     * 获取下拉刷新控件
     */
    @Override public PtrFrameLayout getPtrFrameLayout() {
        return mPtrFrameLayout;
    }

    @Override public void startRefreshing() {
        if (mPtrFrameLayout != null) mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        });
    }

    public void stopRefreshing() {
        if (mPtrFrameLayout != null) mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.refreshComplete();
            }
        });
    }

    @ThreadPoint(ThreadType.MAIN) @Override public void setLoadingState(final LoadingFooter.State state) {
        L.i(initTag(), "setLoadingState：" + state);
        if (mLoadingFooter != null) mLoadingFooter.setState(state);
    }

    @Override public LoadingFooter.State getLoadingState() {
        return mLoadingFooter == null ? null : mLoadingFooter.getState();
    }

    @Override public void openPullRefreshing() {
        if (mPtrFrameLayout != null) mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.setEnabled(true);
            }
        });
    }

    @Override public void closePullRefreshing() {
        if (mPtrFrameLayout != null) mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.setEnabled(false);
            }
        });
    }

    @Override public void openPullLoading() {
        canLoadingMore = true;
    }

    @Override public void closePullLoading() {
        canLoadingMore = false;
    }

    @Override public void setData(List<D> list) {
        mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.refreshComplete();
            }
        });
        super.setData(list);
    }

    @Override public void setData(List<D> list, boolean showEmptyView) {
        mPtrFrameLayout.post(new Runnable() {
            @Override public void run() {
                mPtrFrameLayout.refreshComplete();
            }
        });
        super.setData(list, showEmptyView);
    }

    private void loadingMoreData() {
        if (mLoadingFooter != null) {
            LoadingFooter.State state = mLoadingFooter.getState();
            if (!canLoadingMore) {
                return;
            } else if (state == LoadingFooter.State.Loading) {
                L.i(initTag(), "Under loading..........");
                return;
            } else if (state == LoadingFooter.State.TheEnd) {
                L.i(initTag(), "no more data...........");
                return;
            }
            setLoadingState(LoadingFooter.State.Loading);
            onLoad();
        }
    }

    @Override public void onAdapterGetView(int position, int totalCount) {
        super.onAdapterGetView(position, totalCount);
        if (onLoadTriggerCondition() == LOAD_WHEN_SECOND_TO_LAST && (position == totalCount - 2 || totalCount == 1)) {
            L.i(initTag(), "onAdapterGetView...... position:" + position + "  totalCount:" + totalCount + ", so loadingMoreData()");
            loadingMoreData();
        }
    }

    @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        if (onLoadTriggerCondition() == LOAD_WHEN_SCROLL_TO_BOTTOM && !canListScrollUp() && scrollState == SCROLL_STATE_IDLE) {
            L.i(initTag(), "onScrollStateChanged...... scroll to bottom, so loadingMoreData()");
            loadingMoreData();
        }
    }

    @Override public void setMyOuterScroller(OuterScroller outerScroller, int myPosition) {
        super.setMyOuterScroller(outerScroller, myPosition);
        if (getListView() instanceof InnerScroller) {
            int headerHeight = outerScroller.getHeaderHeight();
            int tabHeight = outerScroller.getTabHeight();
            L.i(initTag(), "The current Fragment is detected in the QsHeaderViewpagerFragment, so the refresh header will be Downward migration... headerHeight:" + headerHeight + "  tabHeight:" + tabHeight);
            getPtrFrameLayout().setHeaderOffsetY(headerHeight + tabHeight);
        }
    }

    @Override public boolean canPullLoading() {
        return canLoadingMore;
    }

    @Override public boolean canPullRefreshing() {
        return mPtrFrameLayout.isEnabled();
    }

    @Override public void smoothScrollToTop(boolean autoRefresh) {
        super.smoothScrollToTop(autoRefresh);
        if (autoRefresh) startRefreshing();
    }

    protected int onLoadTriggerCondition() {
        return LOAD_WHEN_SCROLL_TO_BOTTOM;
    }

}
