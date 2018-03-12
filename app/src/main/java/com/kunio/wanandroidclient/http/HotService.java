package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface HotService {
    /**
     * 热搜
     * @return
     */
    @GET("hotkey/json")
    Observable<Word> getHotKey();

    /**
     * 常用网站
     * @return
     */
    @GET("friend/json")
    Observable<Word> getCommonWebsites();
}
