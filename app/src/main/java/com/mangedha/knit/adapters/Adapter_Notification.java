package com.mangedha.knit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.DetailActivity;


public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.ViewHolder> {
    Context context;
    public Adapter_Notification(Context ctx) {
        super();
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notification, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.columnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 56;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout columnview;

        public ViewHolder(View itemView) {
            super(itemView);
           columnview = (RelativeLayout) itemView.findViewById(R.id.rowview);
//            title = (TextView) itemView.findViewById(R.id.title);
//            title_val = (TextView) itemView.findViewById(R.id.title_val);
        }
    }

}

