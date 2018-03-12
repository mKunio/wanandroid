package com.kunio.wanandroidclient.activity.collect;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface CollectContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showArticle(Article result);

        void addArticle(Article article);

        void showErrorToast(String msg);

        void showEmptyView();

        void showLoadMoreLoadingView();

        void showLoadMoreErrorView();

        void showLoadMoreNoMoreDataView();

        void showLoadMoreCompleteView();
    }

    interface Presenter {

        void detach();

        void loadData();

        void loadMoreArticle(int page);
    }

    interface Model {

        void loadData(Callback<Article> callback);

        void loadMoreArticle(int page, Callback<Article> callback);
    }
}
