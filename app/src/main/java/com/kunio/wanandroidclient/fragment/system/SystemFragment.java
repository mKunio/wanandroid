package com.kunio.wanandroidclient.fragment.system;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.knowledgecontent.KnowledgeDetailedActivity;
import com.kunio.wanandroidclient.bean.Knowledge;
import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.ItemDecoration;
import com.kunio.wanandroidclient.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhc on 2018/3/2 0002.
 */

public class SystemFragment extends Fragment implements SystemContract.View,
        SwipeRefreshLayout.OnRefreshListener, KnowledgeSystemAdapter.ItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    private SystemPresenter presenter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        swipeLayout.setColorSchemeColors(ColorUtil.getColorWithResId(R.color.c4, context));
        swipeLayout.setOnRefreshListener(this);
        presenter = new SystemPresenter(this);
        presenter.loadData();
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
    public void showKnowledge(KnowledgeSystem knowledgeSystem) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            KnowledgeSystemAdapter realAdapter = new KnowledgeSystemAdapter(knowledgeSystem,context);
            int space = DensityUtils.dip2px(context, 5);
            ItemDecoration decoration = new ItemDecoration(space, false, false, false, true);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(realAdapter);
            realAdapter.setItemClickListener(this);
        }else {
            KnowledgeSystemAdapter realAdapter = (KnowledgeSystemAdapter) adapter;
            realAdapter.refreshKnowledge(knowledgeSystem);
        }
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detach();
    }

    @Override
    public void onRefresh() {
        presenter.loadData();
    }

    @Override
    public void itemClick(KnowledgeSystem.DataBean main) {
        String mainTitle = main.getName();
        List<KnowledgeSystem.DataBean.ChildrenBean> children = main.getChildren();
        ArrayList<Knowledge> child = new ArrayList<>(children.size());
        for (KnowledgeSystem.DataBean.ChildrenBean bean : children) {
            Knowledge knowledge = new Knowledge();
            knowledge.setChildId(bean.getId());
            knowledge.setChildTitle(bean.getName());
            child.add(knowledge);
        }
        KnowledgeDetailedActivity.start(context,mainTitle,child);
    }
}
