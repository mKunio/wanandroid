package com.kunio.wanandroidclient.activity.collect;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.CollectService;
import com.kunio.wanandroidclient.http.RetrofitFactory;
import com.kunio.wanandroidclient.http.SystemService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class CollectModel implements CollectContract.Model {
    @Override
    public void loadData(final Callback<Article> callback) {
        CollectService collectService = RetrofitFactory.getDefault().create(CollectService.class);
        Observable<Article> observable = collectService.getArticle(0);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        if (article.isSuccessful()) {
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
    public void loadMoreArticle(int page,final Callback<Article> callback) {
        CollectService collectService = RetrofitFactory.getDefault().create(CollectService.class);
        Observable<Article> observable = collectService.getArticle(page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        if (article.isSuccessful()) {
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
}
