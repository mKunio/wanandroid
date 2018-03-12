package com.kunio.wanandroidclient.fragment.search;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.RetrofitFactory;
import com.kunio.wanandroidclient.http.SearchService;
import com.kunio.wanandroidclient.util.StringUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class SearchModel implements SearchContract.Model {
    @Override
    public void loadData(String keyWord, final Callback<Article> callback) {
        SearchService searchService = RetrofitFactory.getDefault().create(SearchService.class);
        Observable<Article> observable = searchService.getArticle(0, keyWord);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        if (article.isSuccessful()) {
                            for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
                                bean.setTitle(StringUtil.removeParentheses(bean.getTitle()));
                            }
                            callback.onSuccess(article);
                        }else {
                            onError(new Throwable(article.getMessage()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadMoreArticle(int page, String keyWord, final Callback<Article> callback) {
        SearchService searchService = RetrofitFactory.getDefault().create(SearchService.class);
        Observable<Article> observable = searchService.getArticle(page, keyWord);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
                            bean.setTitle(StringUtil.removeParentheses(bean.getTitle()));
                        }
                        callback.onSuccess(article);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
