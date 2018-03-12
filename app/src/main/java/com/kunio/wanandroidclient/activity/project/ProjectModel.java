package com.kunio.wanandroidclient.activity.project;

import com.kunio.wanandroidclient.bean.Project;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.http.Callback;
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

class ProjectModel implements ProjectContract.Model {
    @Override
    public void loadProject(int page,int cid,final Callback<Project> callback) {
        ProjectService projectService = RetrofitFactory.getDefault().create(ProjectService.class);
        Observable<Project> tree = projectService.getProject(page,cid);
        tree.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Project>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Project project) {
                        if (project.getErrorCode()!=-1) {
                            callback.onSuccess(project);
                        }else {
                            onError(new Throwable(project.getMessage()));
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
