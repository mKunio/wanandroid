package com.kunio.wanandroidclient.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kunio.wanandroidclient.AndroidApplication;
import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.fragment.home.HomeFragment;
import com.kunio.wanandroidclient.fragment.MineFragment;
import com.kunio.wanandroidclient.fragment.navigation.NavigationFragment;
import com.kunio.wanandroidclient.fragment.project.ProjectFragment;
import com.kunio.wanandroidclient.fragment.system.SystemFragment;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.home_tv)
    TextView homeTv;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;
    @BindView(R.id.system_iv)
    ImageView systemIv;
    @BindView(R.id.system_tv)
    TextView systemTv;
    @BindView(R.id.system_ll)
    LinearLayout systemLl;
    @BindView(R.id.navigation_iv)
    ImageView navigationIv;
    @BindView(R.id.navigation_tv)
    TextView navigationTv;
    @BindView(R.id.navigation_ll)
    LinearLayout navigationLl;
    @BindView(R.id.project_iv)
    ImageView projectIv;
    @BindView(R.id.project_tv)
    TextView projectTv;
    @BindView(R.id.project_ll)
    LinearLayout projectLl;
    @BindView(R.id.mine_iv)
    ImageView mineIv;
    @BindView(R.id.mine_tv)
    TextView mineTv;
    @BindView(R.id.mine_ll)
    LinearLayout mineLl;
    private int currentFragment = 0;
    private final int HOME = 0;
    private final int SYSTEM = 1;
    private final int NAVIGATION = 2;
    private final int PROJECT = 3;
    private final int MINE = 4;
    SparseArray<Fragment> fragments = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        HomeFragment fragment = new HomeFragment(this);
        fragments.put(HOME, fragment);
        setImageAndColor(HOME);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content, fragment);
        transaction.commit();
    }

    @OnClick({R.id.home_ll, R.id.system_ll, R.id.navigation_ll, R.id.project_ll, R.id.mine_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_ll:
                if (checkDifferent(HOME)) {
                    setImageAndColor(HOME);
                    setCurrentFragment(HOME);
                }
                break;
            case R.id.system_ll:
                if (checkDifferent(SYSTEM)) {
                    setImageAndColor(SYSTEM);
                    setCurrentFragment(SYSTEM);
                }
                break;
            case R.id.navigation_ll:
                if (checkDifferent(NAVIGATION)) {
                    setImageAndColor(NAVIGATION);
                    setCurrentFragment(NAVIGATION);
                }
                break;
            case R.id.project_ll:
                if (checkDifferent(PROJECT)) {
                    setImageAndColor(PROJECT);
                    setCurrentFragment(PROJECT);
                }
                break;
            case R.id.mine_ll:
                if (checkDifferent(MINE)) {
                    setImageAndColor(MINE);
                    setCurrentFragment(MINE);
                }
                break;
        }
    }

    private void setImageAndColor(int will) {
        switch (currentFragment) {
            case HOME:
                homeIv.setImageResource(R.drawable.home_uncheck);
                homeTv.setTextColor(ColorUtil.getColorWithResId(R.color.c2, this));
                break;
            case SYSTEM:
                systemIv.setImageResource(R.drawable.system_uncheck);
                systemTv.setTextColor(ColorUtil.getColorWithResId(R.color.c2, this));
                break;
            case NAVIGATION:
                navigationIv.setImageResource(R.drawable.navigation_uncheck);
                navigationTv.setTextColor(ColorUtil.getColorWithResId(R.color.c2, this));
                break;
            case PROJECT:
                projectIv.setImageResource(R.drawable.project_uncheck);
                projectTv.setTextColor(ColorUtil.getColorWithResId(R.color.c2, this));
                break;
            case MINE:
                mineIv.setImageResource(R.drawable.mine_uncheck);
                mineTv.setTextColor(ColorUtil.getColorWithResId(R.color.c2, this));
                break;
        }
        switch (will) {
            case HOME:
                homeIv.setImageResource(R.drawable.home_check);
                homeTv.setTextColor(ColorUtil.getColorWithResId(R.color.c1, this));
                break;
            case SYSTEM:
                systemIv.setImageResource(R.drawable.system_check);
                systemTv.setTextColor(ColorUtil.getColorWithResId(R.color.c1, this));
                break;
            case NAVIGATION:
                navigationIv.setImageResource(R.drawable.navigation_check);
                navigationTv.setTextColor(ColorUtil.getColorWithResId(R.color.c1, this));
                break;
            case PROJECT:
                projectIv.setImageResource(R.drawable.project_check);
                projectTv.setTextColor(ColorUtil.getColorWithResId(R.color.c1, this));
                break;
            case MINE:
                mineIv.setImageResource(R.drawable.mine_check);
                mineTv.setTextColor(ColorUtil.getColorWithResId(R.color.c1, this));
                break;
        }
    }

    private boolean checkDifferent(int will) {
        return currentFragment != will;
    }

    private void setCurrentFragment(int will) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(fragments.keyAt(i));
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        Fragment fragment = fragments.get(will);
        if (fragment == null) {
            fragment = createFragment(will);
            transaction.add(R.id.content, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
        currentFragment = will;
    }

    private Fragment createFragment(int will) {
        Fragment fragment;
        switch (will) {
            case SYSTEM:
                fragment = new SystemFragment();
                break;
            case NAVIGATION:
                fragment = new NavigationFragment();
                break;
            case PROJECT:
                fragment = new ProjectFragment();
                break;
            case MINE:
                fragment = new MineFragment();
                break;
            default:
                fragment = new HomeFragment(this);
                break;
        }
        fragments.put(will, fragment);
        return fragment;
    }

    private long lastPreTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastPreTime > 2000) {
                ToastUtil.showMessage("再按一次退出WanAndroid");
                lastPreTime = System.currentTimeMillis();
            } else {
                AndroidApplication.exitAPP();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
