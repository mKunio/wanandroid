package com.kunio.wanandroidclient.http;

import com.kunio.wanandroidclient.bean.Project;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.bean.Word;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public interface ProjectService {
    /**
     * 项目分类
     * @return
     */
    @GET("project/tree/json")
    Observable<ProjectTree> getProjectTree();

    /**
     * 项目分类下的列表
     * @param page 页码
     * @param cid 分类中的id
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<Project> getProject(@Path("page") int page, @Query("cid") int cid);
}
