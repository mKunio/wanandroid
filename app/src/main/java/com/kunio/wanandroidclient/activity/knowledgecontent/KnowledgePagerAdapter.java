package com.kunio.wanandroidclient.activity.knowledgecontent;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kunio.wanandroidclient.bean.Knowledge;
import com.kunio.wanandroidclient.fragment.knowledge.KnowledgeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class KnowledgePagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> fragments;

    public KnowledgePagerAdapter(FragmentManager fm, Context context, List<Knowledge> knowledge) {
        super(fm);
        titles = new ArrayList<>(knowledge.size());
        fragments = new ArrayList<>(knowledge.size());
        for (Knowledge k : knowledge) {
            titles.add(k.getChildTitle());
            fragments.add(new KnowledgeFragment(context, k.getChildId()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
