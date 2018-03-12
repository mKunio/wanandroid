package com.kunio.wanandroidclient.activity.hot;

import com.kunio.wanandroidclient.bean.HotWordWeb;
import com.kunio.wanandroidclient.bean.Word;
import com.kunio.wanandroidclient.http.Callback;
import com.kunio.wanandroidclient.http.HotService;
import com.kunio.wanandroidclient.http.RetrofitFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/6 0006.
 */

public class HotModel implements HotContract.Model {
    @Override
    public void loadData(final Callback<HotWordWeb> callback) {
        HotService hotService = RetrofitFactory.getDefault().create(HotService.class);
        Observable<Word> hotKey = hotService.getHotKey().subscribeOn(Schedulers.io());
        Observable<Word> commonWebsites = hotService.getCommonWebsites().subscribeOn(Schedulers.io());
        Observable.zip(hotKey, commonWebsites, new BiFunction<Word, Word, HotWordWeb>() {
            @Override
            public HotWordWeb apply(Word word, Word word2) throws Exception {
                HotWordWeb hotWordWeb = new HotWordWeb();
                hotWordWeb.setHotKey(word);
                hotWordWeb.setCommonWeb(word2);
                return hotWordWeb;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotWordWeb>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotWordWeb hotWordWeb) {
                        if (hotWordWeb.getCommonWeb().getErrorCode()!=-1&&hotWordWeb.getHotKey().getErrorCode()!=-1) {
                            callback.onSuccess(hotWordWeb);
                        }else {
                            onError(new Throwable(hotWordWeb.getCommonWeb().getMessage()+"-"+hotWordWeb.getHotKey().getMessage()));
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
