package com.kunio.wanandroidclient.fragment.project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.project.ProjectActivity;
import com.kunio.wanandroidclient.bean.ProjectTree;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhc on 2018/3/2 0002.
 */

public class ProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProjectContract.View, ProjectTreeAdapter.ItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    private ProjectPresenter presenter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4, context));
        swipeLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ProjectPresenter(this);
        presenter.loadProject();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        presenter.loadProject();
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
    public void showProject(List<ProjectTree.DataBean> result) {
        if (result != null && result.size() != 0) {
            RecyclerView.Adapter recyclerViewAdapter = recyclerView.getAdapter();
            if (recyclerViewAdapter == null) {
                ProjectTreeAdapter adapter = new ProjectTreeAdapter(context, result);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                int space = DensityUtils.dip2px(context, 1);
                recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(adapter);
                adapter.setItemClickListener(this);
            } else {
                ProjectTreeAdapter adapter = (ProjectTreeAdapter) recyclerViewAdapter;
                adapter.setTrees(result);
                adapter.notifyDataSetChanged();
            }
        } else {
            ToastUtil.showMessage("暂无数据");
        }
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void itemClick(ProjectTree.DataBean bean) {
        ProjectActivity.start(context, bean.getId(), bean.getName());
    }
}
