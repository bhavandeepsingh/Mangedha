package com.mangedha.knit.http.models.http_interface;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;
import com.mangedha.knit.http.models.MangedhaModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Akshit on 27/04/2017.
 */

public class ChangePasswordModel extends MangedhaModel{

    @SerializedName("passwordReset")
    @Expose
    String passwordReset;

    public String getPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(String passwordReset) {
        this.passwordReset = passwordReset;
    }

    public static void changePassword(String old_password, String new_password, final MangedhaChangePasswordInterface mangedhaChangePasswordInterface){
        RestAdapter.get().changePassword(makeRequest(old_password, new_password)).enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                if(response.body().is_success()) {
                    mangedhaChangePasswordInterface.onPasswordChangeSuccess(response.body());
                }else{
                    mangedhaChangePasswordInterface.onPasswordChangeFailure(response.body());
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                mangedhaChangePasswordInterface.onPasswordChangeError(t);
            }
        });

    }

    public static Map<String,String> makeRequest(String old_password, String new_password) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("PasswordResetForm[old_password]", old_password);
        stringStringMap.put("PasswordResetForm[new_password]", new_password);
        return stringStringMap;
    }

    public interface MangedhaChangePasswordInterface {

        void onPasswordChangeSuccess(ChangePasswordModel changePasswordModel);

        void onPasswordChangeError(Throwable t);

        void onPasswordChangeFailure(ChangePasswordModel changePasswordModel);

    }
}
