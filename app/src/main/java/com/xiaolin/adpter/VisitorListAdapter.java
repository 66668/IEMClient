package com.xiaolin.adpter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaolin.R;
import com.xiaolin.bean.VisitorBean;
import com.xiaolin.utils.DebugUtil;
import com.xiaolin.utils.GlideCircleTransform;

import java.util.List;

/**
 * fragment显示list适配
 */

public class VisitorListAdapter extends RecyclerView.Adapter {
    public static final String TAG = "visitor";

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;//是否滑动到顶部的标记

    private List<VisitorBean> listBean;
    private boolean showFooter = true;
    private Context context;
    private VisitorOnItemClickListener listener;

    public VisitorListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //设置最后一个item为footerView
        if (!showFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setListBean(List<VisitorBean> listBean) {
        this.listBean = listBean;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_visitor, parent, false);
            ItemViewHolder vh = new ItemViewHolder(view);
            return vh;
        } else {//TYPE_FOOTERs
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            VisitorBean bean = listBean.get(position);
            DebugUtil.d(TAG, "visitorAdapter--VisitorBean=" + bean.toString());
            if (bean == null) {
                return;
            }

            ((ItemViewHolder) holder).tvName.setText(bean.getVisitorName());
            ((ItemViewHolder) holder).tvTime.setText(bean.getArrivalTimePlan());

            //图片加载
            Glide.with(context)
                    .load(bean.getImagePath())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(ContextCompat.getDrawable(context, R.mipmap.default_photo))
                    .error(ContextCompat.getDrawable(context, R.mipmap.default_photo))
                    .crossFade()//动画效果显示
                    .transform(new GlideCircleTransform(context))//自定义圆形图片
                    .into(((ItemViewHolder) holder).img);

        }
    }

    @Override
    public int getItemCount() {
        int begin = showFooter ? 1 : 0;
        if (listBean == null) {
            return begin;
        }
        return listBean.size() + begin;
    }

    public VisitorBean getItem(int position) {
        return listBean == null ? null : listBean.get(position);
    }

    public boolean isShowFooter(boolean showFooter) {
        return this.showFooter = showFooter;
    }

    public boolean isShowFooter() {
        return this.showFooter;
    }

    public void setOnItemClickListener(VisitorOnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    //自定义点击接口
    public interface VisitorOnItemClickListener {
        void VisitorOnItemClick(View view, int position);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private TextView tvTime;
        private ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.VisitorOnItemClick(view, this.getPosition());
            }

        }
    }
}
