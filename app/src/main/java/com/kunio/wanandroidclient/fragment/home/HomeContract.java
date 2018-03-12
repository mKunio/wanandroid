package com.kunio.wanandroidclient.fragment.home;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface HomeContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showHomeView(HomeData result);

        void addArticle(Article article);

        void showErrorView();

        void showErrorToast(String msg);

        void showLoadMoreLoading();

        void showLoadMoreError();

        void showLoadMoreNoMoreData();

        void showLoadMoreComplete();

        void collectSuccess(Article.DataBean.DatasBean bean);
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void loadData(boolean firstTime);

        void loadMoreArticle(int page);

        void collectArticle(Article.DataBean.DatasBean id);
    }

    interface Model {

        void loadData(Callback<HomeData> callback);

        void loadMoreArticle(int page, Callback<Article> callback);

        void collectArticle(int id,Callback<HttpResult> callback);
    }
}
