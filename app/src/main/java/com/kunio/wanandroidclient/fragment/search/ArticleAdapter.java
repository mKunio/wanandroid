package com.kunio.wanandroidclient.fragment.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.WebActivity;
import com.kunio.wanandroidclient.activity.knowledgecontent.KnowledgeDetailedActivity;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HttpResult;
import com.kunio.wanandroidclient.bean.Knowledge;
import com.kunio.wanandroidclient.http.CollectService;
import com.kunio.wanandroidclient.http.RetrofitFactory;
import com.kunio.wanandroidclient.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Article.DataBean.DatasBean> articles;
    private boolean needShowKindOf = true;

    public ArticleAdapter(Context context, List<Article.DataBean.DatasBean> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View headerView = inflater.inflate(R.layout.item_home_list, parent, false);
        return new ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        Article.DataBean.DatasBean bean = articles.get(position);
        viewHolder.name.setText(bean.getAuthor());
        viewHolder.time.setText(bean.getNiceDate());
        viewHolder.title.setText(bean.getTitle());
        String chapterName = bean.getChapterName();
        if (needShowKindOf) {
            if (TextUtils.isEmpty(chapterName)) {
                viewHolder.kind.setClickable(false);
            } else {
                viewHolder.kind.setOnClickListener(this);
            }
            viewHolder.kind.setText(chapterName);
            viewHolder.kind.setTag(bean);
        } else {
            viewHolder.kind.setText("");
            viewHolder.kind.setClickable(false);
        }
        viewHolder.collect.setImageResource(bean.isCollect() ? R.drawable.ic_action_collect : R.drawable.ic_action_no_collect);
        viewHolder.content.setTag(bean);
        viewHolder.collect.setTag(bean);
        viewHolder.content.setOnClickListener(this);
        viewHolder.collect.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void addArticle(List<Article.DataBean.DatasBean> articles) {
        this.articles.addAll(articles);
    }

    public void setNeedShowKindOf(boolean needShowKindOf) {
        this.needShowKindOf = needShowKindOf;
    }

    public void setArticles(List<Article.DataBean.DatasBean> articles) {
        this.articles = articles;
    }

    @Override
    public void onClick(View v) {
        Article.DataBean.DatasBean bean = (Article.DataBean.DatasBean) v.getTag();
        if (v instanceof TextView) {
            // 分类点击
            ArrayList<Knowledge> list = new ArrayList<>();
            Knowledge knowledge = new Knowledge();
            knowledge.setChildTitle(bean.getChapterName());
            knowledge.setChildId(bean.getChapterId());
            list.add(knowledge);
            KnowledgeDetailedActivity.start(context, bean.getChapterName(), list);
        } else if (v instanceof ImageView) {
            // 收藏点击
            if (bean.isCollect()) {
                ToastUtil.showMessage("不支持取消收藏，请至电脑取消");
            } else {
                CancelCollect(bean);
            }
        } else {
            // 条目点击
            WebActivity.start(context, bean.getLink());
        }
    }

    private void CancelCollect(final Article.DataBean.DatasBean bean) {
        CollectService service = RetrofitFactory.getDefault().create(CollectService.class);
        Observable<HttpResult> observable = service.collectArticle(bean.getId());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult>() {
                    @Override
                    public void accept(HttpResult httpResult) throws Exception {
                        if (httpResult.isSuccessful()) {
                            bean.setCollect(true);
                            notifyDataSetChanged();
                            ToastUtil.showMessage("收藏成功");
                        } else {
                            ToastUtil.showMessage(httpResult.getMessage());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showMessage(throwable.getMessage());
                    }
                });
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
}
