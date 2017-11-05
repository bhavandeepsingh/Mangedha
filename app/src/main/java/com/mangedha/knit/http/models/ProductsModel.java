package com.mangedha.knit.http.models;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.http.RestAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 9/24/17.
 */

public class ProductsModel extends MangedhaModel {


    public static int PRODUCT_TYPE_FREE = 0;
    public static int PRODUCT_TYPE_KNIT = 1;
    public static int PRODUCT_TYPE_BUY = 2;

    @SerializedName("list")
    @Expose
    List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public class Product{

        @SerializedName("id")
        @Expose
        int id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("description")
        @Expose
        String description;

        @SerializedName("category")
        @Expose
        CategoriesModel.Category category;

        @SerializedName("type")
        @Expose
        int type;

        @SerializedName("status")
        @Expose
        int status;

        @SerializedName("price")
        @Expose
        int price;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

        @SerializedName("favorite")
        @Expose
        FavoriteModel favoriteModel;

        @SerializedName("files")
        @Expose
        List<ProductFiles> productFiles;

        @SerializedName("buyProduct")
        @Expose
        SettingModel.Membership buyProduct;

        @SerializedName("notification")
        @Expose
        NotificationStatus notificationStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public CategoriesModel.Category getCategory() {
            return category;
        }

        public void setCategory(CategoriesModel.Category category) {
            this.category = category;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public FavoriteModel getFavoriteModel() {
            return favoriteModel;
        }

        public void setFavoriteModel(FavoriteModel favoriteModel) {
            this.favoriteModel = favoriteModel;
        }

        public List<ProductFiles> getProductFiles() {
            return productFiles;
        }

        public void setProductFiles(List<ProductFiles> productFiles) {
            this.productFiles = productFiles;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public SettingModel.Membership getBuyProduct() {
            return buyProduct;
        }

        public void setBuyProduct(SettingModel.Membership buyProduct) {
            this.buyProduct = buyProduct;
        }

        public boolean isProductVisible(){
            SettingModel.Membership membership = MangedhaApplication.getMembership();
            if(type == PRODUCT_TYPE_KNIT) {
                if(membership != null) return membership.is_valid();
                else return false;
            }else if(type == PRODUCT_TYPE_BUY) {
                if(getBuyProduct() != null) return true;
                else return false;
            }else {
                return true;
            }
        }

        public String getFirstImage(){
            return getImageFiles().get(0).getImage_path();
        }

        public NotificationStatus getNotificationStatus() {
            return notificationStatus;
        }

        public void setNotificationStatus(NotificationStatus notificationStatus) {
            this.notificationStatus = notificationStatus;
        }

        public List<ProductFiles> getImageFiles(){
            List<ProductFiles> productFiles = new ArrayList<>();
            for(int i = 0; i < getProductFiles().size(); i++){
                String type = getProductFiles().get(i).getType();
                if(type.equals("png") || type.equals("jpg")) {
                    productFiles.add(getProductFiles().get(i));
                }
            }
            return productFiles;
        }
    }

    @SerializedName("member_ship")
    @Expose
    SettingModel.Membership membership;

    public SettingModel.Membership getMembership() {
        return membership;
    }

    public void setMembership(SettingModel.Membership membership) {
        this.membership = membership;
    }

    public static void getList(final ProductInterface productInterface, Map<String, String> map, int page_no){
        RestAdapter.get().products(map, getPageParam(page_no)).enqueue(new Callback<ProductsModel>() {
            @Override
            public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {
                if(response != null && response.body() != null){
                    productInterface.onSuccess(response.body());
                }else{
                    productInterface.onFail("Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<ProductsModel> call, Throwable t) {
                productInterface.onFail(t.getMessage());
            }
        });
    }

    private static Map<String, Integer> getPageParam(int page_no) {
        Map<String, Integer> stringIntegerMap = new ArrayMap<>();
        stringIntegerMap.put("page", page_no);
        return stringIntegerMap;
    }


    public interface ProductInterface{
        void onSuccess(ProductsModel productsModel);
        void onFail(String error);
    }

}
