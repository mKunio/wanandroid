package com.kunio.wanandroidclient.fragment.system;

import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.RetrofitFactory;
import com.kunio.wanandroidclient.http.SystemService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class SystemModel implements SystemContract.Model {
    @Override
    public void loadData(final Callback<KnowledgeSystem> callback) {
        SystemService systemService = RetrofitFactory.getDefault().create(SystemService.class);
        Observable<KnowledgeSystem> systemObservable = systemService.getKnowledgeSystem();
        systemObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowledgeSystem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KnowledgeSystem knowledgeSystem) {
                        if (knowledgeSystem.isSuccessful()) {
                            callback.onSuccess(knowledgeSystem);
                        }else {
                            onError(new Throwable(knowledgeSystem.getMessage()));
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
