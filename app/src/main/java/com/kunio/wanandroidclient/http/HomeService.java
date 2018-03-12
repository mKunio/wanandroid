package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeBanner;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface HomeService {
    /**
     * 首页banner接口
     * @return
     */
    @GET("banner/json")
    Observable<HomeBanner> getBanner();

    /**
     * 首页列表
     * @param currentPage
     * @return
     */
    @GET("article/list/{currentPage}/json")
    Observable<Article> getArticle(@Path("currentPage") int currentPage);
}
