package com.kunio.wanandroidclient.fragment.navigation;

import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.NavigationService;
import com.kunio.wanandroidclient.http.ProjectService;
import com.kunio.wanandroidclient.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/8 0008.
 */

class NavigationModel implements NavigationContract.Model {
    @Override
    public void loadNavigation(final Callback<Navigation> callback) {
        NavigationService navigationService = RetrofitFactory.getDefault().create(NavigationService.class);
        Observable<Navigation> tree = navigationService.getNavigation();
        tree.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Navigation>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Navigation navigation) {
                        if (navigation.getErrorCode()!=-1) {
                            callback.onSuccess(navigation);
                        }else {
                            onError(new Throwable(navigation.getMessage()));
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
