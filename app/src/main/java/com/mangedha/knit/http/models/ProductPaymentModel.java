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
 * Created by bhavan on 10/17/17.
 */

public class ProductPaymentModel extends MangedhaModel {

    @SerializedName("product")
    @Expose
    SettingModel.Membership product;

    public SettingModel.Membership getProduct() {
        return product;
    }

    public void setProduct(SettingModel.Membership product) {
        this.product = product;
    }

    public static void productPaymentSuccess(TransactionResponse transactionResponse, final ProductPaymentInterface productPaymentInterface){
        RestAdapter.get().paymentProductSuccess(makeCall(transactionResponse)).enqueue(new Callback<ProductPaymentModel>() {
            @Override
            public void onResponse(Call<ProductPaymentModel> call, Response<ProductPaymentModel> response) {
                ProductPaymentModel productPaymentModel = response.body();
                if(productPaymentModel!= null && productPaymentModel.is_success()){
                    productPaymentInterface.onSuccess(productPaymentModel);
                }else if(productPaymentModel != null && productPaymentModel.is_success() == false){
                    productPaymentInterface.onFail(productPaymentModel.getError());
                }else{
                    productPaymentInterface.onFail("Some error occur please try again.");
                }
            }

            @Override
            public void onFailure(Call<ProductPaymentModel> call, Throwable t) {
                productPaymentInterface.onFail(t.getMessage());
            }
        });
    }

    public interface ProductPaymentInterface{
        void onSuccess(ProductPaymentModel productPaymentModel);
        void onFail(String error);
    }

    private static Map<String, String> makeCall(TransactionResponse transactionResponse) {
        Map<String, String> stringStringMap = new ArrayMap<>();
        stringStringMap.put("payment_response", transactionResponse.getPayuResponse());
        return stringStringMap;
    }

}
