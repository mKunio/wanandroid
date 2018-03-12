package com.kunio.wanandroidclient.activity.project;

import com.kunio.wanandroidclient.bean.Project;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class ProjectPresenter implements ProjectContract.Presenter {
    private ProjectContract.Model model;
    private ProjectContract.View view;

    public ProjectPresenter(ProjectContract.View view) {
        this.view = view;
        model = new ProjectModel();
    }

    @Override
    public void loadProject(int cid) {
        view.showLoading();
        view.loadMoreComplete();
        model.loadProject(1, cid, new Callback<Project>() {
            @Override
            public void onSuccess(Project result) {
                Project.DataBean data = result.getData();
                int curPage = data.getCurPage();
                List<Project.DataBean.DatasBean> projects = data.getDatas();
                if (view != null) {
                    view.hideLoading();
                    if (projects.size() > 0) {
                        view.showProject(curPage, projects);
                    } else {
                        view.showErrorToast("暂无数据");
                    }
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

    @Override
    public void loadMoreProject(int page, int cid) {
        view.loadMoreLoading();
        model.loadProject(page, cid, new Callback<Project>() {
            @Override
            public void onSuccess(Project result) {
                Project.DataBean data = result.getData();
                int curPage = data.getCurPage();
                List<Project.DataBean.DatasBean> projects = data.getDatas();
                if (view != null) {
                    if (projects.size() > 0) {
                        view.loadMoreComplete();
                        view.addProject(curPage, projects);
                    } else {
                        view.loadMoreNoData();
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.loadMoreError();
                    view.showErrorToast(msg);
                }
            }
        });
    }

    @Override
    public void detach() {
        view = null;
    }
}
