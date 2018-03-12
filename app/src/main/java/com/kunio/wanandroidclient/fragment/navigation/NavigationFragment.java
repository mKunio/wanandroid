package com.kunio.wanandroidclient.fragment.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 该页面不允许刷新，因为惰性，当请求成功之后会将刷新控件设置为不可用
 * Created by zhc on 2018/3/2 0002.
 */

public class NavigationFragment extends Fragment implements NavigationContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private Context context;
    private NavigationPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        swipeLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4, context));
        swipeLayout.setOnRefreshListener(this);
        presenter = new NavigationPresenter(this);
        presenter.loadNavigation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void showNavigation(Navigation result) {
        swipeLayout.setEnabled(false);
        NavigationAdapter adapter = new NavigationAdapter(context, result);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void onRefresh() {
        presenter.loadNavigation();
    }
}
