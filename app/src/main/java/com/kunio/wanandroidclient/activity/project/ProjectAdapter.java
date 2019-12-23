package com.kunio.wanandroidclient.activity.project;

import android.content.Context;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kunio.wanandroidclient.R;
import com.kunio.wanandroidclient.bean.Project;
import com.kunio.wanandroidclient.util.DensityUtils;

import java.util.List;

/**
 * Created by zhc on 2018/3/8 0008.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Project.DataBean.DatasBean> result;
    private ClickListener listener;

    public ProjectAdapter(Context context, List<Project.DataBean.DatasBean> result) {
        this.context = context;
        this.result = result;
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public void setProject(List<Project.DataBean.DatasBean> result) {
        this.result = result;
    }

    public void addProject(List<Project.DataBean.DatasBean> result) {
        this.result.addAll(result);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Project.DataBean.DatasBean bean = result.get(position);
        int w = DensityUtils.dip2px(context, 100);
        int h = DensityUtils.dip2px(context, 160);
        Glide.with(context).load(bean.getEnvelopePic()).asBitmap().override(w, h).placeholder(R.drawable.default_banner).into(holder.image);
        holder.title.setText(bean.getTitle());
        holder.desc.setText(bean.getDesc());
        holder.author.setText(bean.getAuthor());
        String projectLink = bean.getProjectLink();
        holder.address.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.address.getPaint().setAntiAlias(true);
        holder.address.setText(projectLink);
        holder.address.setTag(projectLink);
        holder.address.setOnClickListener(this);
        holder.time.setText(bean.getNiceDate());
        holder.content.setTag(bean.getLink());
        holder.content.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            String url = (String) v.getTag();
            if (v instanceof TextView) {
                listener.childAddressClick(url);
            } else {
                listener.ItemWholeClick(url);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView desc;
        private final TextView address;
        private final TextView time;
        private final TextView author;
        private final View content;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            address = itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
        }
    }

    public interface ClickListener {
        void childAddressClick(String url);

        void ItemWholeClick(String url);
    }
}
