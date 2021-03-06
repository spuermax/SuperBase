package com.supermax.base.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.supermax.base.R;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.listview.LoadingFooter;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2019/3/21 13:55
 * @Description listView滑动到顶部和底部都能加载更多数据
 */
public abstract class QsTopBottomLoadListFragment<P extends QsPresenter, D> extends QsListFragment<P, D> implements QsITopBottomLoadListFragment<D> {
    public static final byte          LOAD_WHEN_SCROLL_TO_BOTTOM = 0;
    public static final byte          LOAD_WHEN_SECOND_TO_LAST   = 1;
    private boolean       canTopLoading;
    private boolean       canBottomLoading;
    private LoadingFooter topLoadingView;
    private LoadingFooter bottomLoadingView;

    @Override protected void initListView(LayoutInflater inflater, View view) {
        super.initListView(inflater, view);
        View headerView = getHeaderView();
        if (headerView instanceof LoadingFooter) {
            this.topLoadingView = (LoadingFooter) headerView;
        }
        View footerView = getFooterView();
        if (footerView instanceof LoadingFooter) {
            this.bottomLoadingView = (LoadingFooter) footerView;
        }
    }

    @Override public int getHeaderLayout() {
        return R.layout.super_loading_footer;
    }

    @Override public int getFooterLayout() {
        return R.layout.super_loading_footer;
    }

    @Override public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        if (onLoadTriggerCondition() == LOAD_WHEN_SCROLL_TO_BOTTOM && scrollState == SCROLL_STATE_IDLE) {
            if (canTopLoading && !canListScrollDown()) {
                loadingTopData();
            }

            if (canBottomLoading && !canListScrollUp()) {
                L.i(initTag(), "onScrollStateChanged...... scroll to bottom, so loadingBottomData()");
                loadingBottomData();
            }
        }
    }

    @Override public void onAdapterGetView(int position, int totalCount) {
        super.onAdapterGetView(position, totalCount);
        if (onLoadTriggerCondition() == LOAD_WHEN_SECOND_TO_LAST) {
            if (canTopLoading && position == 0) {
                loadingTopData();
            }

            if (canBottomLoading && position == totalCount - 2 || totalCount == 1) {
                loadingBottomData();
            }
        }
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override public void setTopLoadingState(LoadingFooter.State state) {
        L.i(initTag(), "setTopLoadingState：" + state);
        if (topLoadingView != null) {
            topLoadingView.setState(state);
        }
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override public void setBottomLoadingState(LoadingFooter.State state) {
        L.i(initTag(), "setBottomLoadingState：" + state);
        if (bottomLoadingView != null) {
            bottomLoadingView.setState(state);
        }
    }

    @Override public LoadingFooter.State getTopLoadingState() {
        return topLoadingView == null ? null : topLoadingView.getState();
    }

    @Override public LoadingFooter.State getBottomLoadingState() {
        return bottomLoadingView == null ? null : bottomLoadingView.getState();
    }

    @Override public void openTopLoading() {
        canTopLoading = true;
    }

    @Override public void closeTopLoading() {
        canTopLoading = false;
    }

    @Override public void openBottomLoading() {
        canBottomLoading = true;
    }

    @Override public void closeBottomLoading() {
        canBottomLoading = false;
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override public void addTopData(List<D> list) {
        if (list != null && !list.isEmpty()) {
            int index = getListView().getFirstVisiblePosition();
            View childAt = getListView().getChildAt(0);
            int topMargin = (childAt == null) ? 0 : childAt.getHeight() + childAt.getTop();

            mList.addAll(0, list);
            updateAdapter(true);

            getListView().setSelectionFromTop(list.size() + index + 1, topMargin);
        }
    }

    @Override public void addBottomData(List<D> list) {
        addData(list);
    }

    protected int onLoadTriggerCondition() {
        return LOAD_WHEN_SECOND_TO_LAST;
    }

    private void loadingBottomData() {
        if (canBottomLoading && bottomLoadingView != null) {
            LoadingFooter.State state = bottomLoadingView.getState();
            if (state == LoadingFooter.State.Loading) {
                L.i(initTag(), "Under bottom loading..........");
                return;
            } else if (state == LoadingFooter.State.TheEnd) {
                L.i(initTag(), "no more data...........");
                return;
            }
            setBottomLoadingState(LoadingFooter.State.Loading);
            onBottomLoading();
        }
    }

    private void loadingTopData() {
        if (canTopLoading && topLoadingView != null) {
            LoadingFooter.State state = topLoadingView.getState();
            if (state == LoadingFooter.State.Loading) {
                L.i(initTag(), "Under top loading..........");
                return;
            } else if (state == LoadingFooter.State.TheEnd) {
                L.i(initTag(), "no more data...........");
                return;
            }
            setTopLoadingState(LoadingFooter.State.Loading);
            onTopLoading();
        }
    }
}
