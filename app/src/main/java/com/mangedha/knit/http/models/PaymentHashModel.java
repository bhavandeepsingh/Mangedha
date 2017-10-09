package com.mangedha.knit.http.models;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;
import com.payumoney.core.entity.TransactionResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 10/8/17.
 */

public class PaymentHashModel extends MangedhaModel {

    @SerializedName("hash")
    @Expose
    String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public static void getHash(Map<String, String> stringStringMap, final PaymentHashInterface paymentHashInterface){
        RestAdapter.get().getPaymentHash(stringStringMap).enqueue(new Callback<PaymentHashModel>() {
            @Override
            public void onResponse(Call<PaymentHashModel> call, Response<PaymentHashModel> response) {
                if(response != null && response.body() != null){
                    paymentHashInterface.onSuccess(response.body());
                }else{
                    paymentHashInterface.onFail("Error, Please try again later");
                }
            }

            @Override
            public void onFailure(Call<PaymentHashModel> call, Throwable t) {
                paymentHashInterface.onFail(t.getMessage());
            }
        });
    }

    public interface PaymentHashInterface{
        void onSuccess(PaymentHashModel paymentHashModel);
        void onFail(String error);
    }

    public static void processPayment(TransactionResponse transactionResponse, final ProcessPayment processPayment) {
        RestAdapter.get().paymentSuccess(makeCall(transactionResponse)).enqueue(new Callback<SettingModel>() {
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                if(response != null && response.body() != null){
                    processPayment.onSuccess(response.body());
                }else{
                    processPayment.onFail("Error, Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<SettingModel> call, Throwable t) {
                processPayment.onFail(t.getMessage());
            }

        });
    }

    private static Map<String, String> makeCall(TransactionResponse transactionResponse) {
        Map<String, String> stringStringMap = new ArrayMap<>();
        stringStringMap.put("payment_response", transactionResponse.getPayuResponse());
        return stringStringMap;
    }

    public interface ProcessPayment{
        void onSuccess(SettingModel settingModel);
        void onFail(String error);
    }

}
