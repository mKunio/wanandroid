package com.kunio.wanandroidclient.fragment.knowledge;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface KnowledgeContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showArticle(Article result);

        void refreshArticle(Article result);

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

        void loadData(int cid);

        void loadMoreArticle(int page, int cid);
    }

    interface Model {

        void loadData(int cid, Callback<Article> callback);

        void loadMoreArticle(int page, int cid, Callback<Article> callback);
    }
}
