package com.mangedha.knit.adapters;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.R;
import com.mangedha.knit.activities.DetailActivity;
import com.mangedha.knit.activities.ProductsActivity;
import com.mangedha.knit.helpers.ImageHelper;
import com.mangedha.knit.helpers.NetworkHelper;
import com.mangedha.knit.http.models.ProductsModel;

import java.util.List;
import java.util.Map;


public class Adapter_MyProducts extends RecyclerView.Adapter<Adapter_MyProducts.ViewHolder> {

    ProductsActivity productsActivity;
    ProductsModel productsModel;

    Map<String, String> product_request = new ArrayMap<>();
    private List<String> categoryString;
    private int page_no = 1;

    public Adapter_MyProducts(ProductsActivity productsActivity) {
        super();
        this.productsActivity = productsActivity;
        fetchProduct(new ArrayMap<String, String>(), false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_myproducts, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        final ProductsModel.Product product = productsModel.getProductList().get(i);

        viewHolder.list_product_name.setText(product.getName());
        viewHolder.list_product_category.setText(product.getCategory().getName());
        if(product.getPrice() > 0) {
            viewHolder.list_product_price.setText(String.valueOf(product.getPrice()));
        }else{
            viewHolder.list_product_price.setText("Free");
        }

        if(product.getProductFiles().size() > 0){
            ImageHelper.loadImage(product.getProductFiles().get(0).getImage_path(), viewHolder.list_product_image);
        }

        if(product.getFavoriteModel() != null){
            viewHolder.product_list_favorite.setImageResource(R.mipmap.ic_star);
        }else {
            viewHolder.product_list_favorite.setImageResource(R.mipmap.ic_star_disable);
        }

        viewHolder.columnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MangedhaApplication.setProduct(product);
                MangedhaApplication.setProduct_index(i);
                Intent intent = new Intent(productsActivity, DetailActivity.class);
                productsActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(productsModel != null && productsModel.getProductList() != null){
            return productsModel.getProductList().size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout columnview;
        ImageView list_product_image, product_list_favorite;
        TextView list_product_name, list_product_category, list_product_price;

        public ViewHolder(View itemView) {
            super(itemView);
            columnview = (LinearLayout) itemView.findViewById(R.id.columnview);
            list_product_image = (ImageView) itemView.findViewById(R.id.list_product_image);
            list_product_category = (TextView) itemView.findViewById(R.id.list_product_category);
            list_product_name = (TextView) itemView.findViewById(R.id.list_product_name);
            list_product_price = (TextView) itemView.findViewById(R.id.list_product_price);
            product_list_favorite = (ImageView) itemView.findViewById(R.id.product_list_favorite);
        }
    }


    void fetchProduct(Map<String, String> request, final boolean page_append){

        if(!NetworkHelper.state()){
            productsActivity.noInernetShow();
        }else{
            productsActivity.noInternetHide();
        }

        if(!page_append){
            productsModel = null;
            notifyDataSetChanged();
        }

        startLoading();
        ProductsModel.getList(new ProductsModel.ProductInterface() {
            @Override
            public void onSuccess(ProductsModel productsModel) {
                stopLoading();
                if(page_append) {
                    ProductsModel productsModel1 = Adapter_MyProducts.this.productsModel;
                    productsModel1.getProductList().addAll(productsModel.getProductList());
                    productsActivity.afterNextPage(productsModel.getProductList().size());
                }else {
                    Adapter_MyProducts.this.productsModel = productsModel;
                    if (productsModel != null && productsModel.getProductList() != null && productsModel.getProductList().size() <= 0) {
                        showNoProduct();
                    }
                }
                Adapter_MyProducts.this.notifyDataSetChanged();
            }

            @Override
            public void onFail(String error) {
                stopLoading();
            }
        }, request, productsActivity.getPage_no());
    }


    public void favoriteList(){
        product_request.put("ProductsSearch[favorite]", "1");
        fetchProduct(getCategoryFilters(), false);
    }

    public void allList(){
        product_request.remove("ProductsSearch[favorite]");
        fetchProduct(getCategoryFilters(), false);
    }

    public void searchProduct(String text){
        product_request.put("ProductsSearch[name]", text);
        fetchProduct(getCategoryFilters(), false);
    }

    public void searchCategory(List<String> stringList){
        this.categoryString = stringList;        
        fetchProduct(getCategoryFilters(), false);
    }

    public void nextPage(){
        fetchProduct(getCategoryFilters(), true);
    }
    
    Map<String, String > getCategoryFilters(){
        Map<String, String> stringStringMap = new ArrayMap<>();
        if(categoryString != null && categoryString.size() > 0){
            for (int i = 0; i < categoryString.size(); i++){
                stringStringMap.put("ProductsSearch[category_id]["+String.valueOf(i)+"]", categoryString.get(i));
            }

        }
        if(product_request.get("ProductsSearch[favorite]") != null){
            stringStringMap.put("ProductsSearch[favorite]", "1");
        }
        if(product_request.get("ProductsSearch[name]") != null){
            stringStringMap.put("ProductsSearch[name]", product_request.get("ProductsSearch[name]"));
        }
        return stringStringMap;
    }


    void startLoading(){
        productsActivity.showLoading();
        productsActivity.hideNoRecords();
    }

    void stopLoading(){
        productsActivity.hideLoading();
    }

    void showNoProduct(){
        productsActivity.showNoRecords();
    }

    public ProductsModel getProductsModel() {
        return productsModel;
    }
}

