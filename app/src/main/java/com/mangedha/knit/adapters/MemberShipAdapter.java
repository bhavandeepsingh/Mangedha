package com.mangedha.knit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangedha.knit.R;
import com.mangedha.knit.http.models.MemmberShipModel;

/**
 * Created by bhavan on 10/2/17.
 */

public class MemberShipAdapter extends RecyclerView.Adapter<MemberShipAdapter.ViewHolder>{

    MemmberShipModel memmberShipModel;

    public MemberShipAdapter(MemmberShipModel memmberShipModel) {
        super();
        this.memmberShipModel = memmberShipModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_ship_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
