package com.kunio.wanandroidclient.fragment.project;

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
    public void loadProject(final Callback<ProjectTree> callback) {
        ProjectService projectService = RetrofitFactory.getDefault().create(ProjectService.class);
        Observable<ProjectTree> tree = projectService.getProjectTree();
        tree.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProjectTree>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProjectTree projectTree) {
                        if (projectTree.getErrorCode()!=-1) {
                            callback.onSuccess(projectTree);
                        }else {
                            onError(new Throwable(projectTree.getMessage()));
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
