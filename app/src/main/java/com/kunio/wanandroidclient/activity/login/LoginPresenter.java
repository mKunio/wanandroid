package com.kunio.wanandroidclient.activity.login;

import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.User;
import com.kunio.wanandroidclient.http.Callback;

import java.util.Map;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.Model model;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new LoginModel();
    }

    @Override
    public void register(Map<String, String> param) {
        view.showLoading();
        model.register(param, new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                if (view != null) {
                    view.registerSuccess(result);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.showErrorToast(msg);
                    view.hideLoading();
                }
            }
        });
    }

    @Override
    public void login(final Map<String, String> param) {
        view.showLoading();
        model.login(param, new Callback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult result) {
                if (view != null) {
                    view.hideLoading();
                    view.loginSuccess(param.get("username"));
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

    public void detach() {
        view = null;
    }
}
