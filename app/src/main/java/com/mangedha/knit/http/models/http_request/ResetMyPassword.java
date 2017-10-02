package com.mangedha.knit.http.models.http_request;

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
 * Created by admin on 4/26/2017.
 */

public class ResetMyPassword extends MangedhaModel{

    @SerializedName("requestSend")
    @Expose
    String requestSend;

    @SerializedName("license_no")
    @Expose
    String license_no;

    public String getRequestSend() {
        return requestSend;
    }

    public void setRequestSend(String requestSend) {
        this.requestSend = requestSend;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public static void resetPassword(String email, final ResetMyPasswordInterface resetMyPasswordInterface){

        RestAdapter.get().resetMyPassword(makeCall(email)).enqueue(new Callback<ResetMyPassword>() {
            @Override
            public void onResponse(Call<ResetMyPassword> call, Response<ResetMyPassword> response) {
                ResetMyPassword resetMyPassword = response.body();
                if(resetMyPassword.is_success) {
                    resetMyPasswordInterface.onResetPasswordSuccess(resetMyPassword);
                }else{
                    resetMyPasswordInterface.onResetPasswordFailure(resetMyPassword);
                }
            }

            @Override
            public void onFailure(Call<ResetMyPassword> call, Throwable t) {
                resetMyPasswordInterface.onResetPasswordError(t);
            }
        });

    }

    private static Map<String, String> makeCall(String email) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("RequestResetPassword[email]", email);
        return stringStringHashMap;
    }

    public interface ResetMyPasswordInterface {

        void onResetPasswordSuccess(ResetMyPassword resetMyPassword);

        void onResetPasswordError(Throwable t);

        void onResetPasswordFailure(ResetMyPassword resetMyPassword);

    }

}
