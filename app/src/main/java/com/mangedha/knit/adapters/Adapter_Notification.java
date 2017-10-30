package com.mangedha.knit.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.activities.DetailActivity;
import com.mangedha.knit.activities.NotificationActivity;
import com.mangedha.knit.helpers.DateHelper;
import com.mangedha.knit.http.models.ProductsModel;


public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.ViewHolder> {
    NotificationActivity notificationActivity;
    ProductsModel productsModel;
    public Adapter_Notification(NotificationActivity notificationActivity, ProductsModel productsModel) {
        super();
        this.notificationActivity = notificationActivity;
        this.productsModel = productsModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notification, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        final ProductsModel.Product product = productsModel.getProductList().get(i);

        viewHolder.columnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MangedhaApplication.setProduct(product);
                MangedhaApplication.setProduct_index(i);
                Intent intent = new Intent(notificationActivity, DetailActivity.class);
                notificationActivity.startActivity(intent);
                viewHolder.columnview.setBackgroundResource(R.drawable.read_notification);
            }
        });

        if(product.getNotificationStatus() == null) {
            viewHolder.columnview.setBackgroundResource(R.drawable.notification_un_read);
        }else{
            viewHolder.columnview.setBackgroundResource(R.drawable.read_notification);
        }

        viewHolder.noti_date.setText(DateHelper.getTimeAgo(Long.parseLong(product.getCreated_at())));
        viewHolder.noti_name.setText(product.getName());

    }

    @Override
    public int getItemCount() {
        return productsModel.getProductList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout columnview;
        TextView noti_date, noti_name;

        public ViewHolder(View itemView) {
            super(itemView);
            columnview = (RelativeLayout) itemView.findViewById(R.id.rowview);
            noti_date = (TextView) itemView.findViewById(R.id.noti_date);
            noti_name = (TextView) itemView.findViewById(R.id.noti_name);
        }
    }

    public ProductsModel getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(ProductsModel productsModel) {
        this.productsModel = productsModel;
    }
}

