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


public class Adapter_Filters extends RecyclerView.Adapter<Adapter_Filters.ViewHolder> {

    Context context;
    CategoriesModel categoriesModel;

    ProductsActivity productsActivity;

    boolean allChecked = false;
    boolean unChecked = false;

    public Adapter_Filters(ProductsActivity productsActivity) {
        super();
        this.productsActivity= productsActivity;
        setup();
    }

    List<String> filter_ids;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_filters, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        filter_ids = new ArrayList<>();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        CategoriesModel.Category category = categoriesModel.getCategoryList().get(i);

        viewHolder.category_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmoothCheckBox smoothCheckBox = (SmoothCheckBox) view;
                String id = String.valueOf(smoothCheckBox.getTag());
                if(smoothCheckBox.isChecked()){
                    smoothCheckBox.setChecked(false);
                    while (filter_ids.remove(id)){}
                    productsActivity.unCheckSelectAll();
                }else{
                    smoothCheckBox.setChecked(true);
                    filter_ids.add(id);
                }
            }
        });

        if(allChecked){
            viewHolder.category_check_box.setChecked(true);
            filter_ids.add(String.valueOf(category.getId()));
        }

        if(unChecked){
            viewHolder.category_check_box.setChecked(false);
                while (filter_ids.remove(String.valueOf(category.getId()))) {
            }
        }

        viewHolder.category_check_box.setTag(category.getId());

        viewHolder.category_name.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        if(categoriesModel != null && categoriesModel.getCategoryList() != null){
            return categoriesModel.getCategoryList().size();
        }
        return 0;

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

    void setup(){
        CategoriesModel.getList(new CategoriesModel.CategoryInterface() {
            @Override
            public void onSuccess(CategoriesModel categoriesModel) {
                Adapter_Filters.this.categoriesModel = categoriesModel;
                Adapter_Filters.this.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void allUnchecked(){
        unChecked = true;
        allChecked = false;
        filter_ids = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void allChecked(){
        allChecked = true;
        unChecked = false;
        notifyDataSetChanged();
    }

    public List<String> getFilter_ids() {
        return filter_ids;
    }
}

