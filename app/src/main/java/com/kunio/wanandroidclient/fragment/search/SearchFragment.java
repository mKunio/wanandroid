package com.kunio.wanandroidclient.fragment.search;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.kunio.wanandroidclient.activity.search.SearchActivity;
import com.kunio.wanandroidclient.base.SimpleViewFragment;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ItemDecoration;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;

/**
 * Created by zhc on 2018/3/7 0007.
 */

@SuppressLint("ValidFragment")
public class SearchFragment extends SimpleViewFragment implements SearchContract.View {
    private SearchActivity activity;
    private SearchPresenter presenter;
    private Article article;
    private int currentPage = 0;
    private String currentKeyWords;

    public SearchFragment(Context context) {
        super(context);
        needCheckVisible = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (SearchActivity) super.context;
    }

    @Override
    protected void pullDownRefresh() {
        String searchKeyWords = activity.getSearchKeyWords();
        if (!searchKeyWords.isEmpty()) {
            refreshData(searchKeyWords);
        } else {
            ToastUtil.showMessage("缺少搜索关键字");
            hideLoading();
        }
    }

    public void refreshData(String searchKeyWords) {
        currentKeyWords = searchKeyWords;
        presenter.refreshData(searchKeyWords);
    }

    @Override
    protected void pullUpLoadMore() {
        presenter.loadMoreArticle(currentPage, currentKeyWords);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        int space = DensityUtils.dip2px(context, 5);
        recyclerView.addItemDecoration(new ItemDecoration(space, false, false, false, true));
        return new ArticleAdapter(context, article.getData().getDatas());
    }

    @Override
    protected void reLoad() {
        super.reLoad();
        String searchKeyWords = activity.getSearchKeyWords();
        if (!searchKeyWords.isEmpty()) {
            currentKeyWords = searchKeyWords;
            presenter.loadData(searchKeyWords);
        } else {
            ToastUtil.showMessage("缺少搜索关键字");
            hideLoading();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter = new SearchPresenter(this);
        String searchKeyWords = activity.getSearchKeyWords();
        if (!searchKeyWords.isEmpty()) {
            currentKeyWords = searchKeyWords;
            presenter.loadData(searchKeyWords);
        }
    }

    @Override
    public void showLoading() {
        showRefreshLoading();
    }

    @Override
    public void hideLoading() {
        showRefreshComplete();
    }

    @Override
    public void showArticle(Article result) {
        currentPage = result.getData().getCurPage();
        article = result;
        showContentView();
    }

    @Override
    public void refreshArticle(Article article) {
        currentPage = article.getData().getCurPage();
        if (recyclerView != null) {
            RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
            if (recyclerViewAdapter != null) {
                HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerViewAdapter;
                ArticleAdapter innerAdapter = (ArticleAdapter) adapter.getInnerAdapter();
                innerAdapter.setArticles(article.getData().getDatas());
            }
        }
        this.article = article;
        showContentView();
    }

    @Override
    public void addArticle(Article article) {
        currentPage = article.getData().getCurPage();
        HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
        ArticleAdapter innerAdapter = (ArticleAdapter) adapter.getInnerAdapter();
        innerAdapter.addArticle(article.getData().getDatas());
        showContentView();
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void showErrorView() {
        super.showErrorView();
    }

    @Override
    public void showEmptyView() {
        super.showEmptyView();
    }

    @Override
    public void showLoadMoreLoadingView() {
        showLoadMoreLoading();
    }

    @Override
    public void showLoadMoreErrorView() {
        showLoadMoreNetError();
    }

    @Override
    public void showLoadMoreNoMoreDataView() {
        showLoadMoreNoMoreData();
    }

    @Override
    public void showLoadMoreCompleteView() {
        showLoadMoreComplete();
    }
}
