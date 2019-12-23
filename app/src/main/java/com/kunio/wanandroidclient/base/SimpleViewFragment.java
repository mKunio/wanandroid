package com.kunio.wanandroidclient.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.manager.FastScrollLinearLayoutManager;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.LoadMoreRecyclerView;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.OnLoadMoreEvent;

/**
 * 提供显示网络出错，空，以及含有RecyclerView三种内容的方法。作为viewpager的每一个fragment的父类使用，
 * <p>
 * 1.子类继承的时候，不要重写onCreateView方法，若是重写此方法，那么子类将变成一个崭新的fragment，父类基本上所有功能将失效
 * 2.重写父类的onActivityCreated方法分时候，不要覆盖父类方法，即需要调用super。
 * <p>
 * 上拉加载，下拉刷新以及adapter等相关的abstract方法必须要被实现。
 * 当显示的是列表页的时候，自动会有右下角回顶部的按钮功能，该功能是可以被选择性使用，默认是实现了此功能
 * 子类可以重写以下方法以及方法定义：
 * <p>
 * isDownRefreshRequired：该页面是否需要下拉刷新的功能，默认为true
 * isNeedListToTopRequired 该页面是否需要实现列表页滑动超过一屏，显示按钮点击回列表顶部功能，默认为true
 * setEmptyView：该页面的空布局
 * setErrorView：该页面的网络错误页面，一般可以不重写，如果此页面的网络错误页和全局规范一致的话
 * reLoad：当没有重写网络错误页面，并且点击了默认的错误页中的重新加载按钮调用
 * loadData: 该方法是页面可见并且相应的activity已经加载完毕，可以进行数据加载操作，
 * 如果每次切换都需要拉数据，请将super去掉，如果当且仅当切换到页面只拉取一次数据，将super添上即可。
 * <p>
 * Created by zhc on 17/4/26.
 */

public abstract class SimpleViewFragment extends Fragment {
    /**
     * 该页面是否达到加载数据的条件（可见因素除外）
     */
    private boolean canLoadData;
    /**
     * 页面是否需要加载数据
     */
    private boolean needLoadData = true;
    /**
     * 该页面对用户是否可见
     */
    private boolean isVisibleToUser = false;

    protected Context context;

    private FrameLayout mContainer;
    private SwipeRefreshLayout mRefreshLayout;
    protected LoadMoreRecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
    private View view;
    protected boolean needCheckVisible = true;

    public SimpleViewFragment(Context context) {
        this.context = context;
    }

    protected abstract void pullDownRefresh();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (needCheckVisible) {
            this.isVisibleToUser = isVisibleToUser;
            isCanLoadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_pager_content, container, false);
            mContainer = view.findViewById(R.id.pager_container);
            mRefreshLayout = view.findViewById(R.id.income);
            mRefreshLayout.setColorSchemeResources(R.color.c4);
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullDownRefresh();
                }
            });
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        canLoadData = true;
        isCanLoadData();
    }

    protected View setEmptyView() {
        return null;
    }

    protected View setErrorView() {
        TextView reloadButton = new TextView(context);
        reloadButton.setText("网络出错了，点击屏幕重新加载");
        reloadButton.setPadding(20, 20, 20, 20);
        reloadButton.setTextSize(13);
        reloadButton.setClickable(true);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
        return reloadButton;
    }

    protected void showEmptyView() {
        View emptyView = setEmptyView();
        if (emptyView == null) {
            emptyView = View.inflate(context, R.layout.empty_view, null);
        }
        mContainer.removeAllViews();
        mContainer.addView(emptyView);
        mRefreshLayout.setEnabled(isDownRefreshRequired());
    }

    protected void showErrorView() {
        View errorView = setErrorView();
        if (errorView == null) {
            return;
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        errorView.setLayoutParams(params);
        ((TextView) errorView).setGravity(Gravity.CENTER);
        mContainer.removeAllViews();
        mContainer.addView(errorView);
        mRefreshLayout.setEnabled(false);
    }

    private void showCustomView(View customView) {
        mContainer.removeAllViews();
        ViewGroup parent = (ViewGroup) customView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        mContainer.addView(customView);
        mRefreshLayout.setEnabled(isDownRefreshRequired());
    }

    protected void addContentView(View view) {
        showCustomView(view);
    }

    protected void addContentView(int layoutResID) {
        View view = View.inflate(context, layoutResID, null);
        showCustomView(view);
    }

    protected void reLoad() {

    }

    protected void showRefreshLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    protected void showRefreshComplete() {
        mRefreshLayout.setRefreshing(false);
    }

    protected void showLoadMoreLoading() {
        if (recyclerView != null) {
            recyclerView.setLoadMoreStateLoading();
        }
    }

    protected void showLoadMoreComplete() {
        if (recyclerView != null) {
            recyclerView.setLoadMoreResultCompleted();
        }
    }

    protected void showLoadMoreNoMoreData() {
        if (recyclerView != null) {
            recyclerView.setLoadMoreResultNoMoreData();
        }
    }

    protected void showLoadMoreNetError() {
        if (recyclerView != null) {
            recyclerView.setLoadMoreResultNetworkError(null);
        }
    }

    protected void showContentView() {
        mRefreshLayout.setEnabled(isDownRefreshRequired());

        int childCount = mContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mContainer.getChildAt(i);
            if (childAt instanceof RecyclerView) {
                adapter.notifyDataSetChanged();
                wrapAdapter.notifyDataSetChanged();
                return;
            }
        }
        //添加recycleView
        recyclerView = new LoadMoreRecyclerView(context);
        recyclerView.setShowEmptyViewWhenFewData(true);
        final FastScrollLinearLayoutManager manager = new FastScrollLinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setFocusable(false);
        adapter = getAdapter();
        if (adapter == null) {
            Log.e("NullPointerException", "com.hqyxjy.common.widget.SimpleViewFragment:    You need to provide an adapter that belongs to RecyclerView.Adapter ！");
            return;
        }
        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(wrapAdapter);
        mContainer.removeAllViews();
        mContainer.addView(recyclerView);
        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);
//        recyclerView.setPadding(recyclerView.getPaddingLeft(),
//                recyclerView.getPaddingTop() + DensityUtils.dp2px(context, 15),
//                recyclerView.getPaddingRight(),
//                recyclerView.getPaddingBottom());
        recyclerView.setOnLoadMoreEvent(new OnLoadMoreEvent() {
            @Override
            public void loadMore() {
                pullUpLoadMore();
            }
        });
        //添加回顶部按钮
        if (isNeedListToTopRequired()) {
            final ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.icon_to_top);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            params.bottomMargin = 90;
            params.rightMargin = 45;
            mContainer.addView(imageView, params);
            imageView.setVisibility(View.GONE);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                    if (firstVisibleItemPosition != 0) {
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
        }
    }

    protected boolean isNeedListToTopRequired() {
        return true;
    }

    protected boolean isDownRefreshRequired() {
        return true;
    }

    protected abstract void pullUpLoadMore();

    protected abstract RecyclerView.Adapter getAdapter();

    private void isCanLoadData() {
        if (needCheckVisible) {
            if (isVisibleToUser && canLoadData && needLoadData) {
                loadData();
            }
        } else {
            if (canLoadData && needLoadData) {
                loadData();
            }
        }
    }

    /**
     * 当viewpager当前页面对用户可见，并且activity的onCreate方法已经执行完毕，该方法就会被调用
     * 其实真正调用时机是该fragment可见并且该fragment执行到了onActivityCreated方法
     */
    protected void loadData() {
        needLoadData = false;
    }

    public void requestLoadDataOneMoreTime() {
        needLoadData = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewParent parent = null;
        if (view != null) {
            parent = view.getParent();
        }
        if (parent != null) {
            ((ViewGroup) parent).removeView(view);
        }
    }
}
