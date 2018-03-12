package com.kunio.wanandroidclient.fragment.system;

import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.http.Callback;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public interface SystemContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showKnowledge(KnowledgeSystem knowledgeSystem);

        void showErrorToast(String msg);
    }

    interface Presenter {
        void loadData();
        void detach();
    }

    interface Model {
        void loadData(Callback<KnowledgeSystem> callback);
    }
}
