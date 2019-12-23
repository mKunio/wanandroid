package com.kunio.wanandroidclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;
import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/6 0006.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.web_container)
    FrameLayout webContainer;
    @BindView(R.id.title)
    TextView title;
    private AgentWeb agentWeb;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        openWeb(url);
    }

    private void openWeb(String url) {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback(callback)
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
       if (!agentWeb.back()){
           finish();
       }
    }

    @Override
    protected void onPause() {
        super.onPause();
        agentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        agentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        agentWeb.getWebLifeCycle().onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private ChromeClientCallbackManager.ReceivedTitleCallback callback = new ChromeClientCallbackManager.ReceivedTitleCallback() {

        @Override
        public void onReceivedTitle(WebView view, String titles) {
            title.setText(titles);
        }
    };
}
