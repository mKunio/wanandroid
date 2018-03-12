package com.kunio.wanandroidclient.fragment.system;

import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class SystemPresenter implements SystemContract.Presenter {
    private SystemContract.Model model;
    private SystemContract.View view;

    public SystemPresenter(SystemContract.View view) {
        this.view = view;
        model = new SystemModel();
    }

    @Override
    public void loadData() {
       view.showLoading();
       model.loadData(new Callback<KnowledgeSystem>() {
           @Override
           public void onSuccess(KnowledgeSystem result) {
               if (view!=null){
                   view.hideLoading();
                   view.showKnowledge(result);
               }
           }

           @Override
           public void onFailed(String msg) {
               if (view!=null){
                   view.showErrorToast(msg);
                   view.hideLoading();
               }
           }
       });
    }

    @Override
    public void detach() {
        view = null;
    }
}
