package com.kunio.wanandroidclient.activity.login;

import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.User;
import com.kunio.wanandroidclient.http.Callback;

import java.util.Map;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public interface LoginContract {
    interface View {
        void showLoading();

        void hideLoading();

        void registerSuccess(User user);

        void loginSuccess(String username);

        void showErrorToast(String msg);
    }

    interface Presenter {
        void register(Map<String, String> param);

        void login(Map<String, String> param);
    }

    interface Model {
        void register(Map<String, String> param, Callback<User> callback);

        void login(Map<String, String> param, Callback<HttpResult> callback);
    }
}
