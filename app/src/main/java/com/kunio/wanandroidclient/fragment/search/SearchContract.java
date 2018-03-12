package com.kunio.wanandroidclient.fragment.search;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface SearchContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showArticle(Article result);

        void refreshArticle(Article article);

        void addArticle(Article article);

        void showErrorToast(String msg);

        void showErrorView();

        void showEmptyView();

        void showLoadMoreLoadingView();

        void showLoadMoreErrorView();

        void showLoadMoreNoMoreDataView();

        void showLoadMoreCompleteView();
    }

    interface Presenter {

        void detach();

        void loadData(String keyWord);

        void refreshData(String keyWord);

        void loadMoreArticle(int page, String keyWord);
    }

    interface Model {

        void loadData(String keyWord, Callback<Article> callback);

        void loadMoreArticle(int page, String keyWord, Callback<Article> callback);
    }
}
