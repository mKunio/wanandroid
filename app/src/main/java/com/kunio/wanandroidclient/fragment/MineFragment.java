package com.kunio.wanandroidclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.collect.MyCollectActivity;
import com.kunio.wanandroidclient.activity.login.LoginActivity;
import com.kunio.wanandroidclient.fragment.home.HomeFragment;
import com.kunio.wanandroidclient.manager.LoginManager;
import com.kunio.wanandroidclient.util.SharedPreUtil;
import com.kunio.wanandroidclient.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhc on 2018/3/2 0002.
 */

public class MineFragment extends Fragment {
    @BindView(R.id.collect)
    LinearLayout collect;
    @BindView(R.id.exit)
    TextView exit;
    Unbinder unbinder;
    @BindView(R.id.user_name)
    TextView userName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (LoginManager.isLogin()) {
            userName.setText(SharedPreUtil.getUserName());
            exit.setText("退出登录");
        } else {
            userName.setText("未登录");
            exit.setText("点击登录");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            userName.setText(SharedPreUtil.getUserName());
            exit.setText("退出登录");
            EventBus.getDefault().post(HomeFragment.RELOAD);
        }
    }

    @OnClick({R.id.collect, R.id.exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collect:
                if (LoginManager.isLogin()) {
                    gotoCollect();
                } else {
                    gotoLogin();
                }
                break;
            case R.id.exit:
                if (LoginManager.isLogin()) {
                    SharedPreUtil.clearCookie();
                    userName.setText("未登录");
                    exit.setText("点击登录");
                    ToastUtil.showMessage("退出成功");
                    EventBus.getDefault().post(HomeFragment.RELOAD);
                } else {
                    gotoLogin();
                }
                break;
        }
    }

    private void gotoLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, 100);
    }

    private void gotoCollect() {
        Intent intent = new Intent(getActivity(), MyCollectActivity.class);
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {/* Do something */};
}
