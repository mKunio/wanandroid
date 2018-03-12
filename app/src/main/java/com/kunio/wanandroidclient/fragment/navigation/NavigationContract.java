package com.kunio.wanandroidclient.fragment.navigation;

import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public interface NavigationContract {
    interface View {

        void showLoading();

        void hideLoading();

        void showNavigation(Navigation result);

        void showErrorToast(String msg);
    }

    interface Presenter {
        void loadNavigation();
    }

    interface Model {
        void loadNavigation(Callback<Navigation> callback);
    }
}
