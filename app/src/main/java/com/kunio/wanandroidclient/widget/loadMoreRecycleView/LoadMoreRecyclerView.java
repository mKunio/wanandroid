package com.kunio.wanandroidclient.widget.loadMoreRecycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinyuanzhong on 16/9/6.
 */
public final class LoadMoreRecyclerView extends RecyclerView {
    private OnLoadMoreEvent onLoadMoreEvent;

    private Context mContext;

    //总数少于此数值就不显示底部布局
    private int mPageCount = 15;

    //当数据少于一页  底部是否留出空白区域
    private boolean showBottomExtraSpace = false;

    private View customEmptyView;

    //总数少于此数值上拉就不会触发加载更多事件
    private int countToTrigger = 15;

    public void setShowEmptyViewWhenFewData(boolean showEmptyViewWhenFewData) {
        if (showEmptyViewWhenFewData) {
            this.mPageCount = 1;
        }
    }

    public void setShowBottomExtraSpace(boolean showBottomExtraSpace) {
        this.showBottomExtraSpace = showBottomExtraSpace;
//        setFooterViewState(LoadingFooter.State.Normal, null, false);
    }

    public void setCountToTrigger(int countToTrigger) {
        this.countToTrigger = countToTrigger;
        removeOnScrollListener(mOnScrollListener);
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 触发加载更多后的事件,一般为分页请求网络数据
     */
    public void setOnLoadMoreEvent(OnLoadMoreEvent onLoadMoreEvent) {
        this.onLoadMoreEvent = onLoadMoreEvent;
    }

    public void setOnScrollListener(OnScrollListener mOnScrollListener) {
        removeOnScrollListener(this.mOnScrollListener);
        this.mOnScrollListener = mOnScrollListener;
        addOnScrollListener(mOnScrollListener);
    }

    public OnScrollListener mOnScrollListener =
            new EndlessRecyclerOnScrollListener(countToTrigger) {
                @Override
                public void onLoadNextPage(View view) {
                    super.onLoadNextPage(view);
                    if (onLoadMoreEvent != null) {
                        onLoadMoreEvent.loadMore();
                    }
                }
            };

    public LoadMoreRecyclerView(Context context) {
        super(context);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        addOnScrollListener(mOnScrollListener);
    }

    /**
     * 设置加载更多状态为网络错误
     */
    public void setLoadMoreResultNetworkError(OnClickListener onClickListener) {
        setFooterViewState(LoadingFooter.State.NetWorkError, onClickListener, true);
    }

    /**
     * 设置加载更多状态为没有更多数据
     */
    public void setLoadMoreResultNoMoreData() {
        setFooterViewState(LoadingFooter.State.TheEnd, null, true);
    }

    public void showEmptyViewWithoutScroll() {
        setFooterViewState(LoadingFooter.State.TheEnd, null, false);
    }

    /**
     * 设置加载更多状态为正在加载
     */
    public void setLoadMoreStateLoading() {
        setFooterViewState(LoadingFooter.State.Loading, null, true);
    }

    /**
     * 设置加载更多状态为完成
     */
    public void setLoadMoreResultCompleted() {
        setFooterViewState(LoadingFooter.State.Normal, null, false);
    }

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     * @param scrollToEnd   是否滑到底部
     */
    public void setFooterViewState(LoadingFooter.State state, OnClickListener errorListener, boolean scrollToEnd) {

        if (mContext == null) {
            return;
        }

        Adapter outerAdapter = getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        final HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;

        if (headerAndFooterAdapter.getInnerAdapter().getItemCount() < mPageCount
                && !showBottomExtraSpace) {
            return;
        }

        LoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();

            initFooterState(state, errorListener, footerView);
        } else {
            footerView = new LoadingFooter(mContext);

            initFooterState(state, errorListener, footerView);

            headerAndFooterAdapter.addFooterView(footerView);
        }
        if (scrollToEnd) {
            scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    void initFooterState(LoadingFooter.State state,
                         OnClickListener errorListener, LoadingFooter footerView) {
        if (customEmptyView != null) {
            footerView.setEmptyView(customEmptyView);
        }
        footerView.setState(state);
        if (state == LoadingFooter.State.NetWorkError) {
            footerView.setOnClickListener(errorListener);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     */
    public LoadingFooter.State getFooterViewState() {
        LoadingFooter footer = getCurrentFooterView();
        if (footer == null) {
            return LoadingFooter.State.Normal;
        } else {
            return footer.getState();
        }
    }

    private LoadingFooter getCurrentFooterView() {
        Adapter outerAdapter = getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                return (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
            }
        }
        return null;
    }

    public void setEmptyView(View view) {
        customEmptyView = view;
    }
}
