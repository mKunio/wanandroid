package com.kunio.wanandroidclient.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.WebActivity;
import com.kunio.wanandroidclient.activity.hot.HotUsuallyActivity;
import com.kunio.wanandroidclient.activity.knowledgecontent.KnowledgeDetailedActivity;
import com.kunio.wanandroidclient.activity.login.LoginActivity;
import com.kunio.wanandroidclient.activity.search.SearchActivity;
import com.kunio.wanandroidclient.bean.Article;
import com.kunio.wanandroidclient.bean.HomeBanner;
import com.kunio.wanandroidclient.bean.HomeData;
import com.kunio.wanandroidclient.bean.Knowledge;
import com.kunio.wanandroidclient.manager.LoginManager;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ItemDecoration;
import com.kunio.wanandroidclient.util.NetUtil;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.LoadMoreRecyclerView;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.OnLoadMoreEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhc on 2018/3/2 0002.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements HomeContract.View,
        SwipeRefreshLayout.OnRefreshListener, OnLoadMoreEvent, HomeAdapter.ChildClickListener, HomeAdapter.BannerClickListener {
    public static String RELOAD = "reload_home_data";
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.fire)
    ImageView fire;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.error_view)
    LinearLayout errorView;
    @BindView(R.id.normal_view)
    SwipeRefreshLayout swipeRefreshLayout;
    private HomePresenter presenter;
    private int currentPage = 0;
    private Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        presenter = new HomePresenter();
        presenter.attach(this);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4, context));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreEvent(this);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.loadData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.loadData(false);
    }

    @Override
    public void loadMore() {
        presenter.loadMoreArticle(currentPage);
    }

    @Override
    public void showHomeView(HomeData result) {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        currentPage = result.getArticle().getData().getCurPage();
        if (recyclerView.getAdapter() != null) {
            HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
            HomeAdapter innerAdapter = (HomeAdapter) adapter.getInnerAdapter();
            innerAdapter.setHomeData(result);
            adapter.notifyDataSetChanged();
        } else {
            HomeAdapter innerAdapter = new HomeAdapter(context, result);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            HeaderAndFooterRecyclerViewAdapter adapter = new HeaderAndFooterRecyclerViewAdapter(innerAdapter);
            recyclerView.setAdapter(adapter);
            innerAdapter.setChildClickListener(this);
            innerAdapter.setBannerClickListener(this);
            recyclerView.addItemDecoration(new ItemDecoration(DensityUtils.dip2px(context, 5), false, false, false, true));
        }
    }

    @Override
    public void addArticle(Article article) {
        currentPage = article.getData().getCurPage();
        HeaderAndFooterRecyclerViewAdapter adapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
        HomeAdapter innerAdapter = (HomeAdapter) adapter.getInnerAdapter();
        innerAdapter.addArticle(article);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData(true);
            }
        });
    }

    @Override
    public void showErrorToast(String msg) {
        if (NetUtil.isNetConnect(context)) {
            ToastUtil.showMessage(msg);
        } else {
            ToastUtil.showError(context);
        }
    }

    @Override
    public void showLoadMoreLoading() {
        recyclerView.setLoadMoreStateLoading();
    }

    @Override
    public void showLoadMoreError() {
        recyclerView.setLoadMoreResultNetworkError(null);
    }

    @Override
    public void showLoadMoreNoMoreData() {
        recyclerView.setLoadMoreResultNoMoreData();
    }

    @Override
    public void showLoadMoreComplete() {
        if (recyclerView.getAdapter()!=null) {
            recyclerView.setLoadMoreResultCompleted();
        }
    }

    @Override
    public void collectSuccess(Article.DataBean.DatasBean bean) {
        recyclerView.getAdapter().notifyDataSetChanged();
        ToastUtil.showMessage("收藏成功");
    }

    @OnClick({R.id.search, R.id.fire})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                SearchActivity.start(null, context);
                break;
            case R.id.fire:
                Intent intent = new Intent(context, HotUsuallyActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.error_view)
    public void onViewClicked() {
        presenter.loadData(true);
    }

    @Override
    public void childClick(View v, Article.DataBean.DatasBean bean) {
        if (v instanceof ViewGroup) {
            WebActivity.start(context, bean.getLink());
        }
        if (v instanceof TextView) {
            ArrayList<Knowledge> list = new ArrayList<>();
            Knowledge knowledge = new Knowledge();
            knowledge.setChildTitle(bean.getChapterName());
            knowledge.setChildId(bean.getChapterId());
            list.add(knowledge);
            KnowledgeDetailedActivity.start(context, bean.getChapterName(), list);
        }
        if (v instanceof ImageView) {
            if (LoginManager.isLogin()) {
                collectionArticle(bean);
            } else {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            presenter.loadData(false);
        }
    }

    private void collectionArticle(Article.DataBean.DatasBean bean) {
        if (bean.isCollect()) {
            ToastUtil.showMessage("不支持取消收藏，请至电脑取消");
        } else {
            presenter.collectArticle(bean);
        }
    }

    @Override
    public void bannerClick(HomeBanner.DataBean bean) {
        WebActivity.start(context, bean.getUrl());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event.equals(RELOAD)) {
            presenter.loadData(false);
        }
    }
}
