package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.bean.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public interface NavigationService {
    /**
     * 导航
     * @return
     */
    @GET("navi/json")
    Observable<Navigation> getNavigation();
}
