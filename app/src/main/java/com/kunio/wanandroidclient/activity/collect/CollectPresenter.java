package com.kunio.wanandroidclient.activity.collect;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class CollectPresenter implements CollectContract.Presenter {
    private CollectContract.Model model;
    private CollectContract.View view;

    public CollectPresenter(CollectContract.View view) {
        this.view = view;
        model = new CollectModel();
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadData() {
        view.showLoading();
        view.showLoadMoreCompleteView();
        model.loadData(new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                if (view != null) {
                    List<Article.DataBean.DatasBean> datas = result.getData().getDatas();
                    view.hideLoading();
                    if (datas.size() > 0) {
                        for (Article.DataBean.DatasBean data : datas) {
                            // 收藏文章列表默认都是红心
                            data.setCollect(true);
                        }
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
                    view.showErrorToast(msg);
                }
            }
        });
    }

    @Override
    public void loadMoreArticle(int page) {
        view.showLoadMoreLoadingView();
        model.loadMoreArticle(page, new Callback<Article>() {
            @Override
            public void onSuccess(Article result) {
                if (view != null) {
                    List<Article.DataBean.DatasBean> datas = result.getData().getDatas();
                    for (Article.DataBean.DatasBean data : datas) {
                        // 收藏文章列表默认都是红心
                        data.setCollect(true);
                    }
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
