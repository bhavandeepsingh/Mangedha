package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 10/16/17.
 */

public class ContactModel extends MangedhaModel{

    @SerializedName("message")
    @Expose
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void submit(Map<String, String> stringStringMap, final ContactInterface contactInterface){
        RestAdapter.get().enquirySubmit(stringStringMap).enqueue(new Callback<ContactModel>() {
            @Override
            public void onResponse(Call<ContactModel> call, Response<ContactModel> response) {
                ContactModel contactModel = response.body();
                if(contactModel != null && contactModel.is_success){
                    contactInterface.onSuccess(response.body());
                }else if(contactModel != null && contactModel.is_success == false){
                    contactInterface.onFail(contactModel.getError());
                }else{
                    contactInterface.onFail("Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ContactModel> call, Throwable t) {
                contactInterface.onFail(t.getMessage());
            }
        });
    }

    public interface ContactInterface{
        void onSuccess(ContactModel contactModel);
        void onFail(String error);
    }
}
