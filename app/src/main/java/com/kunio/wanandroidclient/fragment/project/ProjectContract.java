package com.kunio.wanandroidclient.fragment.project;

import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public interface ProjectContract {
    interface View {

        void showLoading();

        void hideLoading();

        void showProject(List<ProjectTree.DataBean> result);

        void showErrorToast(String msg);
    }

    interface Presenter {
        void loadProject();
    }

    interface Model {
        void loadProject(Callback<ProjectTree> callback);
    }
}
