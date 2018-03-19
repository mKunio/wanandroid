package com.kunio.wanandroidclient.activity.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.WebActivity;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.bean.Project;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.HeaderAndFooterRecyclerViewAdapter;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.LoadMoreRecyclerView;
import com.kunio.wanandroidclient.widget.loadMoreRecycleView.OnLoadMoreEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class ProjectActivity extends BaseActivity implements ProjectContract.View, OnLoadMoreEvent,
        SwipeRefreshLayout.OnRefreshListener, ProjectAdapter.ClickListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private ProjectPresenter presenter;
    private int cid;
    private int currentPage = 1;

    public static void start(Context context, int cid,String title) {
        Intent intent = new Intent(context, ProjectActivity.class);
        intent.putExtra("cid", cid);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        swipeLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4, this));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreEvent(this);
        initData();
    }

    private void initData() {
        cid = getIntent().getIntExtra("cid", -1);
        title.setText(getIntent().getStringExtra("title"));
        presenter = new ProjectPresenter(this);
        presenter.loadProject(cid);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showLoading() {
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showProject(int currentPage, List<Project.DataBean.DatasBean> result) {
        this.currentPage = currentPage + 1;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            ProjectAdapter innerAdapter = new ProjectAdapter(this, result);
            adapter = new HeaderAndFooterRecyclerViewAdapter(innerAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
            innerAdapter.setClickListener(this);
        } else {
            HeaderAndFooterRecyclerViewAdapter realAdapter = (HeaderAndFooterRecyclerViewAdapter) adapter;
            ProjectAdapter innerAdapter = (ProjectAdapter) realAdapter.getInnerAdapter();
            innerAdapter.setProject(result);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void addProject(int currentPage, List<Project.DataBean.DatasBean> result) {
        this.currentPage = currentPage + 1;
        HeaderAndFooterRecyclerViewAdapter realAdapter = (HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter();
        ProjectAdapter innerAdapter = (ProjectAdapter) realAdapter.getInnerAdapter();
        innerAdapter.addProject(result);
        realAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreError() {
        recyclerView.setLoadMoreResultNetworkError(null);
    }

    @Override
    public void loadMoreComplete() {
        if (recyclerView.getAdapter()!=null) {
            recyclerView.setLoadMoreResultCompleted();
        }
    }

    @Override
    public void loadMoreNoData() {
        recyclerView.setLoadMoreResultNoMoreData();
    }

    @Override
    public void loadMoreLoading() {
        recyclerView.setLoadMoreStateLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void loadMore() {
        presenter.loadMoreProject(currentPage, cid);
    }

    @Override
    public void onRefresh() {
        presenter.loadProject(cid);
    }

    @Override
    public void childAddressClick(String url) {
        WebActivity.start(this,url);
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(url);
//        intent.setData(uri);
//        startActivity(intent);
    }

    @Override
    public void ItemWholeClick(String url) {
        WebActivity.start(this,url);
    }
}
