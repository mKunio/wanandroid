package com.kunio.wanandroidclient.fragment.home;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeBanner;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.imageloader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class HomeAdapter extends RecyclerView.Adapter implements View.OnClickListener, OnBannerListener {
    private Context context;
    private List<HomeBanner.DataBean> banners;
    private List<Article.DataBean.DatasBean> articles;
    private ChildClickListener listener;

    public void setBannerClickListener(BannerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    private BannerClickListener bannerClickListener;

    public void setChildClickListener(ChildClickListener listener) {
        this.listener = listener;
    }

    private final int HEADER = 0;
    private final int ITEM = 1;
    private boolean bannerInit = false;

    public HomeAdapter(Context context, HomeData data) {
        banners = data.getBanner().getData();
        articles = data.getArticle().getData().getDatas();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == HEADER) {
            View headerView = inflater.inflate(R.layout.item_home_banner, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            View headerView = inflater.inflate(R.layout.item_home_list, parent, false);
            return new ItemViewHolder(headerView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            if (!bannerInit) {
                reFreshBanner(viewHolder.banner);
                bannerInit = true;
            }
        } else {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            reFreshArticle(viewHolder, position);
        }
    }

    private void reFreshArticle(ItemViewHolder viewHolder, int position) {
        Article.DataBean.DatasBean bean = articles.get(position - 1);
        viewHolder.name.setText(bean.getAuthor());
        viewHolder.time.setText(bean.getNiceDate());
        viewHolder.title.setText(bean.getTitle());
        viewHolder.kind.setText(bean.getChapterName());
        viewHolder.collect.setImageResource(bean.isCollect() ? R.drawable.ic_action_collect : R.drawable.ic_action_no_collect);
        viewHolder.content.setTag(bean);
        viewHolder.kind.setTag(bean);
        viewHolder.collect.setTag(bean);
        viewHolder.content.setOnClickListener(this);
        viewHolder.kind.setOnClickListener(this);
        viewHolder.collect.setOnClickListener(this);
    }

    private void reFreshBanner(Banner banner) {
        List<String> title = new ArrayList<>();
        List<String> imageUrl = new ArrayList<>();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.setOffscreenPageLimit(banners.size());
        for (HomeBanner.DataBean bean : banners) {
            title.add(bean.getTitle());
            imageUrl.add(bean.getImagePath());
        }
        banner.setImages(imageUrl);
        banner.setBannerTitles(title);
        banner.start();
        banner.setOnBannerListener(this);
    }

    @Override
    public int getItemCount() {
        return articles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    public void setHomeData(HomeData data) {
        banners = data.getBanner().getData();
        articles = data.getArticle().getData().getDatas();
        bannerInit = false;
    }

    public void addArticle(Article article) {
        articles.addAll(article.getData().getDatas());
    }

    @Override
    public void onClick(View v) {
        Article.DataBean.DatasBean bean = (Article.DataBean.DatasBean) v.getTag();
        if (listener!=null){
            listener.childClick(v,bean);
        }
    }

    @Override
    public void OnBannerClick(int position) {
        if (bannerClickListener!=null){
            bannerClickListener.bannerClick(banners.get(position));
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final Banner banner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView time;
        private final TextView title;
        private final TextView kind;
        private final ImageView collect;
        private final View content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            kind = itemView.findViewById(R.id.kind);
            collect = itemView.findViewById(R.id.collect);
            content = itemView.findViewById(R.id.content);
        }
    }
    interface ChildClickListener{
        void childClick(View v, Article.DataBean.DatasBean bean);
    }

    interface BannerClickListener {
        void bannerClick(HomeBanner.DataBean bean);
    }
}
