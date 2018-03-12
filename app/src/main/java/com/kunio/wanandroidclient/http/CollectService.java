package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.KnowledgeSystem;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface CollectService {

    /**
     * 收藏的文章
     * @param currentPage
     * @return
     */
    @GET("lg/collect/list/{currentPage}/json")
    Observable<Article> getArticle(@Path("currentPage") int currentPage);

    @POST("lg/collect/{id}/json")
    Observable<HttpResult> collectArticle(@Path("id") int id);
}
