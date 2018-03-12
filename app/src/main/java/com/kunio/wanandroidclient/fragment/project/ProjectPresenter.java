package com.kunio.wanandroidclient.fragment.project;

import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;

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
    public void loadProject() {
        view.showLoading();
        model.loadProject(new Callback<ProjectTree>() {
            @Override
            public void onSuccess(ProjectTree result) {
                if (view != null) {
                    view.hideLoading();
                    view.showProject(result.getData());
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
