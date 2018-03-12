package com.kunio.wanandroidclient.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.bean.User;
import com.kunio.wanandroidclient.util.SharedPreUtil;
import com.kunio.wanandroidclient.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/9 0009.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.pass_word)
    EditText passWord;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.loading_progress)
    FrameLayout loadingProgress;
    private LoginPresenter presenter;
    private boolean isLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        String userName = SharedPreUtil.getUserName();
        if (!userName.isEmpty()) {
            this.userName.setText(userName);
            this.userName.setSelection(userName.length());
        }
    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (checkNoNull()) {
                    doLogin();
                }else {
                    ToastUtil.showMessage("请输入账号密码");
                }
                break;
            case R.id.register:
                if (checkNoNull()) {
                    doRegister();
                }else {
                    ToastUtil.showMessage("请输入账号密码");
                }
                break;
        }
    }

    private void doRegister() {
        String name = userName.getText().toString().trim();
        String word = passWord.getText().toString().trim();
        Map<String, String> param = new HashMap<>();
        param.put("username", name);
        param.put("password", word);
        param.put("repassword", word);
        presenter.register(param);
    }

    private void doLogin() {
        String name = userName.getText().toString().trim();
        String word = passWord.getText().toString().trim();
        Map<String, String> param = new HashMap<>();
        param.put("username", name);
        param.put("password", word);
        presenter.login(param);
    }

    private boolean checkNoNull() {
        String name = userName.getText().toString().trim();
        String word = passWord.getText().toString().trim();
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(word);
    }

    @Override
    public void showLoading() {
        isLoading = true;
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void registerSuccess(User user) {
        Map<String, String> param = new HashMap<>();
        param.put("username", user.getData().getUsername());
        param.put("password", user.getData().getPassword());
        presenter.login(param);
    }

    @Override
    public void loginSuccess(String username) {
        ToastUtil.showMessage("登陆成功");
        setResult(RESULT_OK);
        SharedPreUtil.saveUserName(username);
        finish();
    }

    @Override
    public void showErrorToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void onBackPressed() {
        if (!isLoading) {
            super.onBackPressed();
        }
    }
}
