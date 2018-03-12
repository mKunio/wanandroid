package com.kunio.wanandroidclient.activity.project;

import com.kunio.wanandroidclient.bean.Project;
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

        void showProject(int currentPage, List<Project.DataBean.DatasBean> result);

        void showErrorToast(String msg);

        void addProject(int currentPage, List<Project.DataBean.DatasBean> result);

        void loadMoreError();

        void loadMoreComplete();

        void loadMoreNoData();

        void loadMoreLoading();
    }

    interface Presenter {
        void loadProject(int cid);

        void loadMoreProject(int page,int cid);
        void detach();
    }

    interface Model {
        void loadProject(int page,int cid, Callback<Project> callback);
    }
}
