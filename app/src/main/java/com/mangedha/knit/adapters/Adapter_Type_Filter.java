package com.mangedha.knit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.ProductsActivity;
import com.mangedha.knit.helpers.SmoothCheckBox;
import com.mangedha.knit.http.models.CategoriesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavan on 10/26/17.
 */

public class Adapter_Type_Filter extends RecyclerView.Adapter<Adapter_Type_Filter.ViewHolder>{

    Context context;
    CategoriesModel categoriesModel;

    ProductsActivity productsActivity;

    boolean allChecked = false;
    boolean unChecked = false;

    public Adapter_Type_Filter (ProductsActivity productsActivity) {
        super();
        this.productsActivity= productsActivity;
    }

    String filter_ids;

    @Override
    public Adapter_Type_Filter .ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_filters, viewGroup, false);
        Adapter_Type_Filter.ViewHolder viewHolder = new Adapter_Type_Filter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_Type_Filter.ViewHolder viewHolder, final int i) {

        viewHolder.category_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothCheckBox smoothCheckBox = (SmoothCheckBox) view;
                String id = String.valueOf(smoothCheckBox.getTag());
                if(smoothCheckBox.isChecked()){
                    smoothCheckBox.setChecked(false);
                    filter_ids = "";
                }else{
                    allUnchecked();
                    smoothCheckBox.setChecked(true);
                    filter_ids = id;
                }
            }
        });


        Data data = getDataList().get(i);

        if(unChecked){

            if(data.value != filter_ids) viewHolder.category_check_box.setChecked(false);

        }

        viewHolder.category_check_box.setTag(data.value);

        viewHolder.category_name.setText(data.name);

    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout columnview;
        TextView category_name;
        SmoothCheckBox category_check_box;

        public ViewHolder(View itemView) {
            super(itemView);
            columnview = (LinearLayout) itemView.findViewById(R.id.columnview);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_check_box = (SmoothCheckBox) itemView.findViewById(R.id.category_check_box);
        }
    }

    public String getFilter_ids() {
        return filter_ids;
    }

    public void allUnchecked(){
        unChecked = true;
        notifyDataSetChanged();
    }

    List<Data> getDataList(){
        List<Data> datas = new ArrayList<>();
        datas.add(new Data("Free", "0"));
        datas.add(new Data("Membership", "1"));
        datas.add(new Data("Paid", "2"));
        return datas;
    }

    public class Data{
        String name;
        String value;
        public Data (String name, String value){
            this.name = name;
            this.value = value;
        }
    }
}
