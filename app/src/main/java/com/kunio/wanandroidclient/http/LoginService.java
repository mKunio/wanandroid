package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.bean.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public interface LoginService {
    /**
     * 导航
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<User> register(@FieldMap Map<String,String> param);

    @Headers("m_login_request: login")
    @POST("user/login")
    @FormUrlEncoded
    Observable<HttpResult> login(@FieldMap Map<String,String> param);
}
