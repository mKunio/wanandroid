package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.bean.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public interface SystemService {
    /**
     * 体系列表
     * @return
     */
    @GET("tree/json")
    Observable<KnowledgeSystem> getKnowledgeSystem();

    /**
     * 具体的体系下的文章
     * @param currentPage
     * @param cid
     * @return
     */
    @GET("article/list/{currentPage}/json")
    Observable<Article> getArticle(@Path("currentPage") int currentPage, @Query("cid") int cid);
}
