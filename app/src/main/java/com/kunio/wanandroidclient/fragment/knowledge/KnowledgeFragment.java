package com.kunio.wanandroidclient.fragment.knowledge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.kunio.wanandroidclient.base.SimpleViewFragment;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.fragment.search.ArticleAdapter;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ItemDecoration;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;

/**
 * Created by zhc on 2018/3/7 0007.
 */

@SuppressLint("ValidFragment")
public class KnowledgeFragment extends SimpleViewFragment implements KnowledgeContract.View {
    private int cid;
    private int currentPage = 0;
    private Article article;
    private KnowledgePresenter presenter;

    public KnowledgeFragment(Context context, int childId) {
        super(context);
        cid = childId;
    }

    @Override
    protected void pullDownRefresh() {
        presenter.loadData(cid);
    }

    @Override
    protected void pullUpLoadMore() {
        presenter.loadMoreArticle(currentPage, cid);
    }

    @Override
    protected void loadData() {
        super.loadData();
        presenter = new KnowledgePresenter(this);
        presenter.loadData(cid);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        int space = DensityUtils.dip2px(context, 5);
        recyclerView.addItemDecoration(new ItemDecoration(space, false, false, false, true));
        ArticleAdapter adapter = new ArticleAdapter(context, article.getData().getDatas());
        adapter.setNeedShowKindOf(false);
        return adapter;
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
    public void refreshArticle(Article result) {
        currentPage = result.getData().getCurPage();
        if (recyclerView == null) {
            showArticle(result);
        } else {
            HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
            ArticleAdapter innerAdapter = (ArticleAdapter) adapter.getInnerAdapter();
            innerAdapter.setArticles(result.getData().getDatas());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addArticle(Article article) {
        currentPage = article.getData().getCurPage();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }

}
