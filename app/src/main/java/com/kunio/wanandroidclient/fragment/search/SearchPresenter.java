package com.kunio.wanandroidclient.fragment.search;

import android.util.Log;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.util.StringUtil;

import java.util.List;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.Model model;
    private SearchContract.View view;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        model = new SearchModel();
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadData(String keyWord) {
        view.showLoading();
        model.loadData(keyWord, new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                if (view != null) {
                    List<Article.DataBean.DatasBean> datas = result.getData().getDatas();
                    view.hideLoading();
                    if (datas.size() > 0) {
                        view.showArticle(result);
                    } else {
                        view.showEmptyView();
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.hideLoading();
                    view.showErrorView();
                    view.showErrorToast(msg);
                }
            }
        });
    }

    @Override
    public void refreshData(String keyWord) {
        view.showLoading();
        view.showLoadMoreCompleteView();
        model.loadData(keyWord, new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                if (view != null) {
                    List<Article.DataBean.DatasBean> datas = result.getData().getDatas();
                    view.hideLoading();
                    if (datas.size() > 0) {
                        view.refreshArticle(result);
                    } else {
                        view.showEmptyView();
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.hideLoading();
                    view.showErrorToast(msg);
                }
            }
        });
    }

    @Override
    public void loadMoreArticle(int page, String keyWord) {
        view.showLoadMoreLoadingView();
        model.loadMoreArticle(page, keyWord, new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                if (view != null) {
                    List<Article.DataBean.DatasBean> datas = result.getData().getDatas();
                    view.showLoadMoreCompleteView();
                    if (datas.size() > 0) {
                        view.addArticle(result);
                    } else {
                        view.showLoadMoreNoMoreDataView();
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.showLoadMoreErrorView();
                    view.showErrorToast(msg);
                }
            }
        });
    }
}
