package com.kunio.wanandroidclient.activity.login;

import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.User;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.LoginService;
import com.kunio.wanandroidclient.http.RetrofitFactory;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/9 0009.
 */

class LoginModel implements LoginContract.Model {
    @Override
    public void register(Map<String, String> param, final Callback<User> callback) {
        LoginService loginService = RetrofitFactory.getDefault().create(LoginService.class);
        Observable<User> register = loginService.register(param);
        register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                       if (user.isSuccessful()){
                           callback.onSuccess(user);
                       }else {
                           onError(new Throwable(user.getMessage()));
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
    public void login(Map<String, String> param, final Callback<HttpResult> callback) {
        LoginService loginService = RetrofitFactory.getDefault().create(LoginService.class);
        Observable<HttpResult> login = loginService.login(param);
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult httpResult) {
                         if (httpResult.isSuccessful()){
                             callback.onSuccess(httpResult);
                         }else {
                             onError(new Throwable(httpResult.getMessage()));
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
