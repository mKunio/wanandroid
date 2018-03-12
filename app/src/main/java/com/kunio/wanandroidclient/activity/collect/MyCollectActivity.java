package com.kunio.wanandroidclient.activity.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.fragment.search.ArticleAdapter;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ItemDecoration;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.LoadMoreRecyclerView;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.OnLoadMoreEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public class MyCollectActivity extends BaseActivity implements CollectContract.View, OnLoadMoreEvent, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private CollectPresenter presenter;
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreEvent(this);
        swipeLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4,this));
        initData();
    }

    private void initData() {
        presenter = new CollectPresenter(this);
        presenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showLoading() {
      swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showArticle(Article result) {
        currentPage = result.getData().getCurPage();
        if (recyclerView.getAdapter() != null) {
            HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
            ArticleAdapter innerAdapter = (ArticleAdapter) adapter.getInnerAdapter();
            innerAdapter.setArticles(result.getData().getDatas());
            adapter.notifyDataSetChanged();
        } else {
            ArticleAdapter innerAdapter = new ArticleAdapter(this, result.getData().getDatas());
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            HeaderAndFooterRecyclerViewAdapter adapter = new HeaderAndFooterRecyclerViewAdapter(innerAdapter);
            recyclerView.setAdapter(adapter);
            innerAdapter.setNeedShowKindOf(true);
            recyclerView.addItemDecoration(new ItemDecoration(DensityUtils.dip2px(this, 5), false, false, false, true));
        }
    }

    @Override
    public void addArticle(Article article) {
        HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
        ArticleAdapter innerAdapter = (ArticleAdapter) adapter.getInnerAdapter();
        innerAdapter.addArticle(article.getData().getDatas());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void showEmptyView() {
        ToastUtil.showMessage("您暂时没有收藏文章哦");
    }

    @Override
    public void showLoadMoreLoadingView() {
        recyclerView.setLoadMoreStateLoading();
    }

    @Override
    public void showLoadMoreErrorView() {
        recyclerView.setLoadMoreResultNetworkError(null);
    }

    @Override
    public void showLoadMoreNoMoreDataView() {
        recyclerView.setLoadMoreResultNoMoreData();
    }

    @Override
    public void showLoadMoreCompleteView() {
        recyclerView.setLoadMoreResultCompleted();
    }

    @Override
    public void loadMore() {
        presenter.loadMoreArticle(currentPage);
    }

    @Override
    public void onRefresh() {
        presenter.loadData();
    }
}
