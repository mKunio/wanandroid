package com.kunio.wanandroidclient.fragment.navigation;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.activity.WebActivity;
import com.kunio.wanandroidclient.bean.Navigation;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Navigation.DataBean> navigationData;
    private int margin;

    public NavigationAdapter(Context context, Navigation navigation) {
        this.context = context;
        navigationData = navigation.getData();
        margin = DensityUtils.dp2px(context, 8);
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_navigation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Navigation.DataBean bean = navigationData.get(position);
        holder.name.setText(bean.getName());
        holder.childContent.removeAllViews();
        for (Navigation.DataBean.ArticlesBean data : bean.getArticles()) {
            TextView textView = new TextView(context.getApplicationContext());
            textView.setTextColor(ColorUtil.getRandomColor());
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.shape_hot_text_stoke);
            textView.setText(data.getTitle());
            textView.setTag(data.getLink());
            textView.setPadding(margin, margin, margin, margin);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, 0, margin);
            holder.childContent.addView(textView, params);
            textView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return navigationData.size();
    }

    @Override
    public void onClick(View v) {
        WebActivity.start(context, (String) v.getTag());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView name;
        private final FlowLayout childContent;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            childContent = itemView.findViewById(R.id.child_title_content);
        }
    }
}
