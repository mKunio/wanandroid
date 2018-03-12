package com.kunio.wanandroidclient.activity.hot;

import com.kunio.wanandroidclient.bean.HotWordWeb;
import com.kunio.wanandroidclient.bean.Word;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/6 0006.
 */

public interface HotContract {
    interface View {
        void showLoading();

        void hideLoading();

        void hideHotKey();

        void hideCommonWeb();

        void addHotKey(List<Word.DataBean> data);

        void addCommonWeb(List<Word.DataBean> commonWebData);

        void showErrorToast(String msg);
    }

    interface Presenter {
        void loadData();
    }

    interface Model {
        void loadData(Callback<HotWordWeb> callback);
    }
}
