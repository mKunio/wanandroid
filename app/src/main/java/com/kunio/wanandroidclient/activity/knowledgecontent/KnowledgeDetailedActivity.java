package com.kunio.wanandroidclient.activity.knowledgecontent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.bean.Knowledge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class KnowledgeDetailedActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public static void start(Context context, String title, ArrayList<Knowledge> child) {
        Intent intent = new Intent(context, KnowledgeDetailedActivity.class);
        intent.putExtra("title", title);
        intent.putParcelableArrayListExtra("knowledge", child);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_detailed);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        this.title.setText(title);
        List<Knowledge> child = intent.getParcelableArrayListExtra("knowledge");
        tabs.setupWithViewPager(viewpager);
        viewpager.setAdapter(new KnowledgePagerAdapter(getSupportFragmentManager(), this, child));
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
