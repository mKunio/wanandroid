package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Article;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public interface SearchService {
    /**
     * 搜索
     * @param currentPage 页码
     * @param keyWord 搜索关键字
     * @return
     */
    @POST("article/query/{currentPage}/json")
    @FormUrlEncoded
    Observable<Article> getArticle(@Path("currentPage") int currentPage, @Field("k") String keyWord);
}
