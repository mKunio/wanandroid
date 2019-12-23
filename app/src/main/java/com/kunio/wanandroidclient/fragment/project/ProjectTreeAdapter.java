package com.kunio.wanandroidclient.fragment.project;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.bean.ProjectTree;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class ProjectTreeAdapter extends RecyclerView.Adapter<ProjectTreeAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<ProjectTree.DataBean> trees;
    private ItemClickListener listener;

    public ProjectTreeAdapter(Context context, List<ProjectTree.DataBean> trees) {
        this.context = context;
        this.trees = trees;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public void setTrees(List<ProjectTree.DataBean> trees) {
        this.trees = trees;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_single_text_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProjectTree.DataBean bean = trees.get(position);
        holder.name.setText(bean.getName());
        holder.name.setTag(bean);
        holder.name.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return trees == null ? 0 : trees.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            ProjectTree.DataBean bean = (ProjectTree.DataBean) v.getTag();
            listener.itemClick(bean);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    interface ItemClickListener {
        void itemClick(ProjectTree.DataBean bean);
    }
}
