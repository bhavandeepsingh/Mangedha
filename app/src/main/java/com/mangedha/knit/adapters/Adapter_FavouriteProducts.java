package com.mangedha.knit.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.DetailActivity;


public class Adapter_FavouriteProducts extends RecyclerView.Adapter<Adapter_FavouriteProducts.ViewHolder> {
    Context context;
    public Adapter_FavouriteProducts(Context ctx) {
        super();
        this.context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_myproducts, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        viewHolder.columnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 56;

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout columnview;

        public ViewHolder(View itemView) {
            super(itemView);
           columnview = (LinearLayout) itemView.findViewById(R.id.columnview);
//            title = (TextView) itemView.findViewById(R.id.title);
//            title_val = (TextView) itemView.findViewById(R.id.title_val);
        }
    }

}

