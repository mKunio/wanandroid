package com.kunio.wanandroidclient.activity.hot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.WebActivity;
import com.kunio.wanandroidclient.activity.search.SearchActivity;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.bean.Word;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.kunio.wanandroidclient.util.NetUtil;
import com.kunio.wanandroidclient.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/6 0006.
 */

public class HotUsuallyActivity extends BaseActivity implements HotContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.all_search_flow_layout)
    FlowLayout allSearchFlowLayout;
    @BindView(R.id.usual_use_flow_layout)
    FlowLayout usualUseFlowLayout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_all_search)
    TextView tvAllSearch;
    @BindView(R.id.tv_usual_web)
    TextView tvUsualWeb;
    private HotPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_usually);
        ButterKnife.bind(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.c4));
        swipeLayout.setOnRefreshListener(this);
        hideHotKey();
        hideCommonWeb();
        initData();
    }

    private void initData() {
        presenter = new HotPresenter(this);
        presenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
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
    public void hideHotKey() {
        tvAllSearch.setVisibility(View.GONE);
        allSearchFlowLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideCommonWeb() {
        tvUsualWeb.setVisibility(View.GONE);
        usualUseFlowLayout.setVisibility(View.GONE);
    }

    @Override
    public void addHotKey(List<Word.DataBean> data) {
        tvAllSearch.setVisibility(View.VISIBLE);
        allSearchFlowLayout.setVisibility(View.VISIBLE);
        allSearchFlowLayout.removeAllViews();
        addTextView(data, allSearchFlowLayout, 0);
    }

    @Override
    public void addCommonWeb(List<Word.DataBean> commonWebData) {
        tvUsualWeb.setVisibility(View.VISIBLE);
        usualUseFlowLayout.setVisibility(View.VISIBLE);
        usualUseFlowLayout.removeAllViews();
        addTextView(commonWebData, usualUseFlowLayout, 1);
    }

    private void addTextView(List<Word.DataBean> data, FlowLayout layout, int type) {
        for (Word.DataBean hot : data) {
            TextView textView = new TextView(this);
            textView.setTextColor(ColorUtil.getRandomColor());
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.shape_hot_text_stoke);
            textView.setText(hot.getName());
            textView.setTag(hot);
            switch (type) {
                case 1:
                textView.setTag(hot.getLink());
                    break;
                default:
                    textView.setTag(hot);
                    break;
            }
            textView.setOnClickListener(this);
            int padding = DensityUtils.dp2px(this, 8);
            textView.setPadding(padding, padding, padding, padding);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = DensityUtils.dp2px(this, 10);
            params.setMargins(margin, margin, 0, margin);
            layout.addView(textView, params);
        }
    }

    @Override
    public void showErrorToast(String msg) {
        if (NetUtil.isNetConnect(this)) {
            ToastUtil.showError(this);
        } else {
            ToastUtil.showMessage(msg);
        }
    }

    @Override
    public void onRefresh() {
        presenter.loadData();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof String){
            String link = (String) tag;
            WebActivity.start(this,link);
        }
        if (tag instanceof Word.DataBean){
            TextView textView = (TextView) v;
            SearchActivity.start(textView.getText().toString(),this);
        }
    }
}
