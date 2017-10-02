package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.RestAdapter;
import com.mangedha.knit.http.models.http_request.UserLoginRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 4/25/2017.
 */

public class UserLoginModel extends MangedhaModel{

    @SerializedName("user")
    @Expose
    ProfileModel profileModel;

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }


    public static void login(String user_email, String user_password, final Login login){
        RestAdapter.get().userLogin(UserLoginRequest.makeLoginRequest(user_email, user_password)).enqueue(new Callback<UserLoginModel>() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                UserLoginModel userLoginModel = response.body();
                if(userLoginModel == null){
                    login.onLoginFail("Task not complete please try again later!");
                    return;
                }
                if(userLoginModel.is_success){
                    UserHelper.login(userLoginModel);
                    login.onSuccess(userLoginModel);
                }else{
                    login.onLoginFail(userLoginModel.getError());
                }
            }

            @Override
            public void onFailure(Call<UserLoginModel> call, Throwable t) {
                login.onFail(t);
            }

        });
    }


    public interface Login {
        void onSuccess(UserLoginModel userLoginModel);
        void onLoginFail(String message);
        void onFail(Throwable t);
    }

    public static void register(Map<String, String> registerRequest, final Register register){
        RestAdapter.get().register(registerRequest).enqueue(new Callback<UserLoginModel>() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                UserLoginModel userLoginModel = response.body();
                if(userLoginModel == null){
                    register.onLoginFail("Task not complete please try again later!");
                    return;
                }
                if(userLoginModel.is_success()){
                    register.onSuccess(userLoginModel);
                }else {
                    register.onLoginFail(userLoginModel.getError());
                }

            }

            @Override
            public void onFailure(Call<UserLoginModel> call, Throwable t) {
                register.onFail(t);
            }
        });
    }

    public static void update(Map<String, String> registerRequest, final Register register){
        RestAdapter.get().update(registerRequest).enqueue(new Callback<UserLoginModel>() {
            @Override
            public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                UserLoginModel userLoginModel = response.body();
                if(userLoginModel == null){
                    register.onLoginFail("Task not complete please try again later!");
                    return;
                }
                if(userLoginModel.is_success()){
                    UserHelper.login(userLoginModel);
                    register.onSuccess(userLoginModel);
                }else {
                    register.onLoginFail(userLoginModel.getError());
                }
            }

            @Override
                public void onFailure(Call<UserLoginModel> call, Throwable t) {
                    register.onFail(t);
                }
            });
    }

    public interface Register {
        void onSuccess(UserLoginModel userLoginModel);
        void onLoginFail(String message);
        void onFail(Throwable t);
    }
}

