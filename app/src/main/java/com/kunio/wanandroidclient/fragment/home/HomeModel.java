package com.kunio.wanandroidclient.fragment.home;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeBanner;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.CollectService;
import com.kunio.wanandroidclient.http.HomeService;
import com.kunio.wanandroidclient.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class HomeModel implements HomeContract.Model {

    @Override
    public void loadData(final Callback<HomeData> callback) {
        HomeService service = RetrofitFactory.getDefault().create(HomeService.class);
        Observable<HomeBanner> bannerObservable = service.getBanner().subscribeOn(Schedulers.io());
        Observable<Article> articleObservable = service.getArticle(0).subscribeOn(Schedulers.io());
        Observable.zip(bannerObservable, articleObservable, new BiFunction<HomeBanner, Article, HomeData>() {
            @Override
            public HomeData apply(HomeBanner banner, Article article) throws Exception {
                HomeData data = new HomeData();
                data.setArticle(article);
                data.setBanner(banner);
                return data;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeData homeData) {
                        if (homeData.getArticle().getErrorCode() != -1 && homeData.getBanner().getErrorCode() != -1) {
                            callback.onSuccess(homeData);
                        }else {
                            onError(new Throwable("请求失败"));
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
    public void loadMoreArticle(int page, final Callback<Article> callback) {
        HomeService service = RetrofitFactory.getDefault().create(HomeService.class);
        Observable<Article> articleObservable = service.getArticle(page);
        articleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        if (article.getErrorCode()!=-1) {
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
    public void collectArticle(int id, final Callback<HttpResult> callback) {
        CollectService service = RetrofitFactory.getDefault().create(CollectService.class);
        Observable<HttpResult> observable = service.collectArticle(id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult result) {
                        if (result.isSuccessful()) {
                            callback.onSuccess(result);
                        }else {
                            onError(new Throwable(result.getMessage()));
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
