package com.kunio.wanandroidclient.fragment.knowledge;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.fragment.search.SearchContract;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.RetrofitFactory;
import com.kunio.wanandroidclient.http.SearchService;
import com.kunio.wanandroidclient.http.SystemService;
import com.kunio.wanandroidclient.util.StringUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class KnowledgeModel implements KnowledgeContract.Model {
    @Override
    public void loadData(int cid, final Callback<Article> callback) {
        SystemService systemService = RetrofitFactory.getDefault().create(SystemService.class);
        Observable<Article> observable = systemService.getArticle(0, cid);
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
    public void loadMoreArticle(int page, int cid, final Callback<Article> callback) {
        SystemService systemService = RetrofitFactory.getDefault().create(SystemService.class);
        Observable<Article> observable = systemService.getArticle(page, cid);
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
