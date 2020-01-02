package com.example.funtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funtest.R;
import com.example.funtest.bean.DeviceBean;
import com.example.funtest.interfaces.OnItemClickListener;

import java.util.List;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 11:16
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DeviceBean> list;
    private OnItemClickListener onItemTouchListener;

    public MyAdapter(List<DeviceBean> list) {
        this.list = list;
    }

    public void setList(List<DeviceBean> list) {
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sp;
        TextView devip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sp = itemView.findViewById(R.id.tv_Devname);
            devip = itemView.findViewById(R.id.tv_Devip);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemTouchListener != null) {
                        onItemTouchListener.onItemLongClicked(v,getAdapterPosition());
                    }
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemTouchListener != null) {
                        onItemTouchListener.onItemClicked(v,getAdapterPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DeviceBean deviceBean = list.get(position);
        holder.devip.setText(deviceBean.getDevip());
        holder.sp.setText(deviceBean.getDevmac());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemTouchListener = clickListener;
    }

}
