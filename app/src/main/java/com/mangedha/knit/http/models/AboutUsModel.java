package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 10/26/17.
 */

public class AboutUsModel extends MangedhaModel {

    @SerializedName("about_us")
    @Expose
    String about_us;

    public String getAbout_us() {
        return about_us;
    }

    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    public static void get(final AboutUsModelInterface aboutUsModelInterface){
        RestAdapter.get().aboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                AboutUsModel aboutUsModel = response.body();
                if(aboutUsModel != null){
                    aboutUsModelInterface.onSuccess(aboutUsModel);
                }else{
                    aboutUsModelInterface.onFail("Try again later.");
                }
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {
                aboutUsModelInterface.onFail(t.getMessage());
            }
        });
    }

    public interface AboutUsModelInterface{

        void onSuccess(AboutUsModel aboutUsModel);
        void onFail(String error);

    }
}
