package com.kunio.wanandroidclient.fragment.navigation;

import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class NavigationPresenter implements NavigationContract.Presenter {
    private NavigationContract.Model model;
    private NavigationContract.View view;

    public NavigationPresenter(NavigationContract.View view) {
        this.view = view;
        model = new NavigationModel();
    }

    @Override
    public void loadNavigation() {
        view.showLoading();
        model.loadNavigation(new Callback<Navigation>() {
            @Override
            public void onSuccess(Navigation result) {
                if (view != null) {
                    view.hideLoading();
                    view.showNavigation(result);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view!=null) {
                    view.hideLoading();
                    view.showErrorToast(msg);
                }
            }
        });
    }
}
