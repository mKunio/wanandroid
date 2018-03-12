package com.kunio.wanandroidclient.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.base.BaseActivity;
import com.kunio.wanandroidclient.fragment.search.SearchFragment;
import com.kunio.wanandroidclient.util.SoftKeyBoardListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.back)
    ImageView back;
    private final String fTag = "search";

    public static void start(@Nullable String keyWords, Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("key", keyWords);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initEditText();
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.search_content, new SearchFragment(this), fTag);
        transaction.commit();
    }

    private void initEditText() {
        String key = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(key)) {
            etSearch.setText(key);
            etSearch.setSelection(key.length());
        }
        etSearch.setOnEditorActionListener(this);
    }

    public String getSearchKeyWords() {
        return etSearch.getText().toString().trim();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected boolean hideSoftKeyBoardWhenClickOtherPoints() {
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyWords = v.getText().toString();
            if (keyWords.isEmpty()) {
                return true;
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(fTag);
                if (fragment != null) {
                    SearchFragment searchFragment = (SearchFragment) fragment;
                    searchFragment.refreshData(keyWords);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
