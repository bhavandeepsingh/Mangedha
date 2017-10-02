package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 9/23/17.
 */

public class CategoriesModel extends MangedhaModel{

    @SerializedName("list")
    @Expose
    List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public class Category{

        @SerializedName("id")
        @Expose
        int id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("desc")
        @Expose
        String desc;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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
    }

    public static void getList(final CategoryInterface categoryInterface){
        RestAdapter.get().categoryList().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                if(response.body() != null){
                    categoryInterface.onSuccess(response.body());
                }else{
                    categoryInterface.onError("Please try again!");
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                categoryInterface.onError(t.getMessage());
            }
        });
    }

    public interface CategoryInterface{
        void onSuccess(CategoriesModel categoriesModel);
        void onError(String error);
    }
}
