package com.kunio.wanandroidclient.activity.hot;

import com.kunio.wanandroidclient.bean.HotWordWeb;
import com.kunio.wanandroidclient.bean.Word;
import com.kunio.wanandroidclient.http.Callback;

import java.util.List;

/**
 * Created by zhc on 2018/3/6 0006.
 */

public class HotPresenter implements HotContract.Presenter {
    private HotContract.Model model;
    private HotContract.View view;

    public HotPresenter(HotContract.View view) {
        this.view = view;
        model = new HotModel();
    }

    public void detach() {
        view = null;
    }

    @Override
    public void loadData() {
        view.showLoading();
        model.loadData(new Callback<HotWordWeb>() {
            @Override
            public void onSuccess(HotWordWeb result) {
                if (view != null) {
                    view.hideLoading();
                    Word hotKey = result.getHotKey();
                    Word commonWeb = result.getCommonWeb();
                    List<Word.DataBean> hotKeyData = hotKey.getData();
                    if (hotKeyData.size() == 0) {
                        view.hideHotKey();
                    } else {
                        view.addHotKey(hotKeyData);
                    }
                    List<Word.DataBean> commonWebData = commonWeb.getData();
                    if (commonWebData.size() == 0) {
                        view.hideCommonWeb();
                    } else {
                        view.addCommonWeb(commonWebData);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (view != null) {
                    view.showErrorToast(msg);
                }
            }
        });
    }
}
