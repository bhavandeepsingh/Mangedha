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
    private String typeString;
    public boolean load_more;

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
        if(product.getType() == ProductsModel.PRODUCT_TYPE_BUY) {
            viewHolder.list_product_price.setText("\u20B9"  + String.valueOf(product.getPrice()));
        }else if(product.getType() == ProductsModel.PRODUCT_TYPE_KNIT){
            viewHolder.list_product_price.setVisibility(View.GONE);
        }else{
            viewHolder.list_product_price.setText("Free");
        }

        if(product.getProductFiles().size() > 0){
            if(product.isProductVisible()) {
                ImageHelper.loadImage(product.getFirstImage(), viewHolder.list_product_image);
            }else{
                viewHolder.list_product_image.setImageResource(R.mipmap.lock_image);
            }
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
                }else {
                    Adapter_MyProducts.this.productsModel = productsModel;
                    if (productsModel != null && productsModel.getProductList() != null && productsModel.getProductList().size() <= 0) {
                        showNoProduct();
                    }
                }
                MangedhaApplication.setMembership(productsModel.getMembership());
                Adapter_MyProducts.this.notifyDataSetChanged();
                productsActivity.afterNextPage(productsModel.getPagination().isLoad_more());
            }

            @Override
            public void onFail(String error) {
                stopLoading();
            }
        }, request, productsActivity.getPage_no());
    }


    public void myProduct(){
        product_request.remove("ProductsSearch[favorite]");
        product_request.put("ProductsSearch[my_product]", "1");
        fetchProduct(getCategoryFilters(), false);
    }

    public void favoriteList(){
        product_request.remove("ProductsSearch[my_product]");
        product_request.put("ProductsSearch[favorite]", "1");
        fetchProduct(getCategoryFilters(), false);
    }

    public void allList(){
        product_request.remove("ProductsSearch[favorite]");
        product_request.remove("ProductsSearch[my_product]");
        fetchProduct(getCategoryFilters(), false);
    }

    public void searchProduct(String text){
        product_request.put("ProductsSearch[name]", text);
        fetchProduct(getCategoryFilters(), false);
    }

    public void searchCategory(List<String> stringList, String typeList){
        this.categoryString = stringList;
        this.typeString = typeList;
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
        if(typeString != null && typeString.length() > 0){
            stringStringMap.put("ProductsSearch[type]", typeString);
        }

        if(product_request.get("ProductsSearch[favorite]") != null){
            stringStringMap.put("ProductsSearch[favorite]", "1");
        }
        if(product_request.get("ProductsSearch[name]") != null){
            stringStringMap.put("ProductsSearch[name]", product_request.get("ProductsSearch[name]"));
        }
        if(product_request.get("ProductsSearch[type]") != null){
            stringStringMap.put("ProductsSearch[type]", product_request.get("ProductsSearch[type]"));
        }
        if(product_request.get("ProductsSearch[my_product]") != null){
            stringStringMap.put("ProductsSearch[my_product]", product_request.get("ProductsSearch[my_product]"));
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

