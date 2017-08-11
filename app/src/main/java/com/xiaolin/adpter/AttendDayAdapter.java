package com.xiaolin.adpter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaolin.R;
import com.xiaolin.bean.AttendDaysOFMonthBean;
import com.xiaolin.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * AttendDayActivity中listView的适配
 */

public class AttendDayAdapter extends BaseAdapter {
    private static final String TAG = "calendar";
    List<AttendDaysOFMonthBean> list;
    Context context;
    public LayoutInflater inflater;

    public AttendDayAdapter(Context context, ArrayList<AttendDaysOFMonthBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public class WidgetHolder {
        public TextView tv_moringTime;
        public TextView tv_lateTime;
        public TextView tv_moringType;
        public TextView tv_lateType;
        public TextView tv_moringState;
        public TextView tv_lateState;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i
        );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //listView赋值
    public void setEntityList(ArrayList entityList) {
        this.list.clear();
        this.list.addAll(entityList);
        notifyDataSetChanged();
    }

    WidgetHolder holder;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            //该布局上的控件
            holder = new WidgetHolder();
            view = inflater.inflate(R.layout.item_attend_detail, new LinearLayout(context), false);
            holder.tv_moringTime = (TextView) view.findViewById(R.id.tv_moringTime);
            holder.tv_lateTime = (TextView) view.findViewById(R.id.tv_lateTime);

            holder.tv_moringType = (TextView) view.findViewById(R.id.tv_morningType);
            holder.tv_lateType = (TextView) view.findViewById(R.id.tv_lateType);

            holder.tv_moringState = (TextView) view.findViewById(R.id.tv_morningState);
            holder.tv_lateState = (TextView) view.findViewById(R.id.tv_lateState);
            view.setTag(holder);
        } else {
            holder = (WidgetHolder) view.getTag();
        }

        AttendDaysOFMonthBean bean = list.get(i);

        //签到
        holder.tv_moringTime.setText(bean.getFirstAttendDateTime());
        holder.tv_moringType.setText(bean.getFirstAttendType());

        if (bean.getFirstState().contains("迟到")) {
            DebugUtil.d(TAG, "签到--迟到");
            holder.tv_moringState.setText(bean.getFirstState());
            holder.tv_moringState.setTextColor(ContextCompat.getColor(context, R.color.orange));
        } else if (bean.getFirstState().contains("正常")) {
            DebugUtil.d(TAG, "签到--正常");
            holder.tv_moringState.setText(bean.getFirstState());
            holder.tv_moringState.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            DebugUtil.d(TAG, "签到--缺勤");
            holder.tv_moringState.setText(bean.getFirstState());
            holder.tv_moringState.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        //签退
        holder.tv_lateTime.setText(bean.getLastAttendDateTime());
        holder.tv_lateType.setText(bean.getLastAttendType());
        if (bean.getLastState().contains("早退")) {
            DebugUtil.d(TAG, "签退--早退");
            holder.tv_lateState.setText(bean.getLastState());
            holder.tv_lateState.setTextColor(ContextCompat.getColor(context, R.color.orange));
        } else if (bean.getLastState().contains("正常")) {
            DebugUtil.d(TAG, "签退--正常");
            holder.tv_lateState.setText(bean.getLastState());
            holder.tv_lateState.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            DebugUtil.d(TAG, "签退--缺勤");
            holder.tv_lateState.setText(bean.getLastState());
            holder.tv_lateState.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        return view;
    }
}
