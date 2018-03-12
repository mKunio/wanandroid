package com.kunio.wanandroidclient.fragment.system;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.bean.KnowledgeSystem;
import com.kunio.wanandroidclient.util.ColorUtil;
import com.kunio.wanandroidclient.util.DensityUtils;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhc on 2018/3/7 0007.
 */

class KnowledgeSystemAdapter extends RecyclerView.Adapter<KnowledgeSystemAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private KnowledgeSystem knowledgeSystem;
    private ItemClickListener listener;

    public KnowledgeSystemAdapter(KnowledgeSystem knowledgeSystem, Context context) {
        this.context = context;
        this.knowledgeSystem = knowledgeSystem;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_knowledge_system, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KnowledgeSystem.DataBean mainTitle = knowledgeSystem.getData().get(position);
        holder.mainTitle.setText(mainTitle.getName());
        addChildTextView(holder.childContainer,mainTitle.getChildren());
        holder.wholeContainer.setTag(mainTitle);
        holder.wholeContainer.setOnClickListener(this);
    }

    private void addChildTextView(FlowLayout childContainer, List<KnowledgeSystem.DataBean.ChildrenBean> children) {
        childContainer.removeAllViews();
        for (KnowledgeSystem.DataBean.ChildrenBean child : children) {
            TextView textView = new TextView(context);
            textView.setTextColor(ColorUtil.getColorWithResId(R.color.c8,context));
            textView.setTextSize(14);
            textView.setText(child.getName());
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = DensityUtils.dp2px(context, 10);
            params.setMargins(0, 0, margin, margin);
            childContainer.addView(textView, params);
        }
    }


    @Override
    public int getItemCount() {
        return knowledgeSystem.getData().size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.itemClick((KnowledgeSystem.DataBean) v.getTag());
        }
    }

    public void refreshKnowledge(KnowledgeSystem knowledgeSystem) {
        this.knowledgeSystem = knowledgeSystem;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mainTitle;
        private final FlowLayout childContainer;
        private final View wholeContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mainTitle = itemView.findViewById(R.id.main_title);
            childContainer = itemView.findViewById(R.id.child_title_layout);
            wholeContainer = itemView.findViewById(R.id.system_knowledge_tv_layout);
        }
    }
    interface ItemClickListener{
        void itemClick(KnowledgeSystem.DataBean main);
    }
}
