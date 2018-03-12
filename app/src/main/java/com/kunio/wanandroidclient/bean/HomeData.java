package com.kunio.wanandroidclient.bean;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class HomeData {
    public HomeBanner getBanner() {
        return banner;
    }

    public void setBanner(HomeBanner banner) {
        this.banner = banner;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    private HomeBanner banner;
    private Article article;
}
