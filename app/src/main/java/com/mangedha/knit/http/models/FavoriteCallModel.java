package com.mangedha.knit.http.models;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 9/25/17.
 */

public class FavoriteCallModel extends MangedhaModel {

    @SerializedName("favorites")
    @Expose
    FavoriteModel favoriteModel;

    public FavoriteModel getFavoriteModel() {
        return favoriteModel;
    }

    public void setFavoriteModel(FavoriteModel favoriteModel) {
        this.favoriteModel = favoriteModel;
    }

    public static void favoriteAdd(int product_id, final FavoriteCallInteface favoriteCallInteface){

        RestAdapter.get().favoriteAdd(makeCall(product_id)).enqueue(new Callback<FavoriteCallModel>() {
            @Override
            public void onResponse(Call<FavoriteCallModel> call, Response<FavoriteCallModel> response) {

                FavoriteCallModel favoriteCallModel = response.body();
                if(favoriteCallModel !=null && favoriteCallModel.is_success){
                    favoriteCallInteface.onSuccess(favoriteCallModel);
                }else{
                    favoriteCallInteface.onFail("Please try again later.");
                }

            }

            @Override
            public void onFailure(Call<FavoriteCallModel> call, Throwable t) {
                favoriteCallInteface.onFail(t.getMessage());
            }
        });

    }

    public static void favoriteRemove(int product_id, final FavoriteCallInteface favoriteCallInteface){

        RestAdapter.get().favoriteRemove(makeCall(product_id)).enqueue(new Callback<FavoriteCallModel>() {
            @Override
            public void onResponse(Call<FavoriteCallModel> call, Response<FavoriteCallModel> response) {

                FavoriteCallModel favoriteCallModel = response.body();
                if(favoriteCallModel !=null && favoriteCallModel.is_success){
                    favoriteCallInteface.onSuccess(favoriteCallModel);
                }else{
                    favoriteCallInteface.onFail("Please try again later.");
                }

            }

            @Override
            public void onFailure(Call<FavoriteCallModel> call, Throwable t) {
                favoriteCallInteface.onFail(t.getMessage());
            }
        });

    }

    public interface FavoriteCallInteface{
        void onSuccess(FavoriteCallModel favoriteCallModel);
        void onFail(String error);
    }

    static Map<String, String> makeCall(int product_id){
        Map<String, String> stringStringMap = new ArrayMap<>();
        stringStringMap.put("product_id", String.valueOf(product_id));
        return stringStringMap;
    }
}
