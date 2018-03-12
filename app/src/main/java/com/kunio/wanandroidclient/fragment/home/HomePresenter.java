package com.kunio.wanandroidclient.fragment.home;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View view;
    private HomeContract.Model model;

    HomePresenter() {
        model = new HomeModel();
    }

    @Override
    public void attach(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadData(final boolean firstTime) {
        view.showLoading();
        view.showLoadMoreComplete();
        model.loadData(new Callback<HomeData>() {
            @Override
            public void onSuccess(HomeData result) {
                if (view != null) {
                    view.hideLoading();
                    view.showHomeView(result);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.hideLoading();
                    if (firstTime) {
                        view.showErrorView();
                    } else {
                        view.showErrorToast(msg);
                    }
                }
            }
        });
    }

    @Override
    public void loadMoreArticle(int page) {
        view.showLoadMoreLoading();
        model.loadMoreArticle(page, new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                view.showLoadMoreComplete();
                if (result.getData().getDatas().size() == 0) {
                    view.showLoadMoreNoMoreData();
                } else {
                    view.addArticle(result);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.showLoadMoreError();
                }
            }
        });
    }

    @Override
    public void collectArticle(final Article.DataBean.DatasBean bean) {
        model.collectArticle(bean.getId(), new Callback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult result) {
                if (view!=null){
                    bean.setCollect(true);
                    view.collectSuccess(bean);
                }
            }

            @Override
            public void onFailed(String msg) {
               if (view!=null){
                   view.showErrorToast(msg);
               }
            }
        });
    }
}
