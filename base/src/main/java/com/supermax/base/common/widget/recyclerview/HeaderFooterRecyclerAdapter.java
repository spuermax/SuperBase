package com.supermax.base.common.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
 * @Author yinzh
 * @Date   2018/10/14 15:48
 * @Description
 */
public class HeaderFooterRecyclerAdapter extends RecyclerView.Adapter {


    private List<View> headerView;
    private List<View> footerView;
    private RecyclerView.Adapter tagAdapter;

    //定义FooterView类型 和 HeaderView类型
    private final static int HEADER_VIEW_TYPE = Integer.MAX_VALUE / 123;
    private final static int FOOTER_VIEW_TYPE = Integer.MAX_VALUE / 321;

    public HeaderFooterRecyclerAdapter(RecyclerView.Adapter tagAdapter, List<View> headerViews, List<View> footerViews) {
        this.tagAdapter = tagAdapter;
        this.headerView = headerViews;
        this.footerView = footerViews;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int headerNum = headerView.size();
        if (position < headerNum) {
            return HEADER_VIEW_TYPE;
        } else if (position >= headerNum) {
            int itemCount = tagAdapter.getItemCount();
            int realPosition = position - headerNum;
            if (realPosition < itemCount) {
                return tagAdapter.getItemViewType(realPosition);
            }
        }
        return FOOTER_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                return new HeaderOrFooterView(headerView.get(0));
            case FOOTER_VIEW_TYPE:
                return new HeaderOrFooterView(headerView.get(0));
            default:
                return tagAdapter.onCreateViewHolder(viewGroup, viewType);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int headerNum = headerView.size();
        if(position < headerNum){
            onHeaderFooterBindViewHolder(viewHolder);
        } else if (position >= headerNum){
            int itemCount = tagAdapter.getItemCount();
            int realPosition = position - headerNum;
            if (realPosition < itemCount) {
                tagAdapter.onBindViewHolder(viewHolder, realPosition);
            }
            return;
        }
        onHeaderFooterBindViewHolder(viewHolder);
    }

    private void onHeaderFooterBindViewHolder(RecyclerView.ViewHolder viewHolder){
        if (viewHolder instanceof HeaderOrFooterView) {
            HeaderOrFooterView headerHolder = (HeaderOrFooterView) viewHolder;
            headerHolder.bindData();
        }
    }

    @Override
    public int getItemCount() {
        return tagAdapter.getItemCount() + headerView.size() + footerView.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderOrFooterView) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    public void registerDataSetObserver(RecyclerView.AdapterDataObserver mDataObserver) {
        if (tagAdapter != null) {
            tagAdapter.registerAdapterDataObserver(mDataObserver);
        }
    }

    public void unregisterDataSetObserver(RecyclerView.AdapterDataObserver mDataObserver) {
        if (tagAdapter != null) {
            tagAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
    }



    public interface OnRecyclerViewAdapterBindViewHolder {

        void onAdapterBindViewHolder();

    }


    /**
     * headerView 和 footerView  ViewHolder的持有类
     */
    private class HeaderOrFooterView extends RecyclerView.ViewHolder {
        View view;

        HeaderOrFooterView(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.view = itemView;
        }

        void bindData() {
            if (view != null && view instanceof OnRecyclerViewAdapterBindViewHolder) {
                OnRecyclerViewAdapterBindViewHolder view = (OnRecyclerViewAdapterBindViewHolder) this.view;
                view.onAdapterBindViewHolder();
            }
        }
    }
}
