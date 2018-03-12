package com.kunio.wanandroidclient.fragment.knowledge;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.Knowledge;
import com.kunio.wanandroidclient.fragment.search.SearchContract;
import com.kunio.wanandroidclient.fragment.search.SearchModel;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class KnowledgePresenter implements KnowledgeContract.Presenter {
    private KnowledgeContract.Model model;
    private KnowledgeContract.View view;

    public KnowledgePresenter(KnowledgeContract.View view) {
        this.view = view;
        model = new KnowledgeModel();
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public void loadData(int cid) {
        view.showLoading();
        model.loadData(cid, new Callback<Article>() {
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
                    view.showErrorToast(msg);
                }
            }
        });
    }

    @Override
    public void loadMoreArticle(int page, int cid) {
        view.showLoadMoreLoadingView();
        model.loadMoreArticle(page, cid, new Callback<Article>() {
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
