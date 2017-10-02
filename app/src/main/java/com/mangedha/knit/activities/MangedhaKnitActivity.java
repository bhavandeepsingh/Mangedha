package com.mangedha.knit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.RestAdapter;
import com.mangedha.knit.http.models.UserLoginModel;
import com.mangedha.knit.http.models.http_request.RegisterUser;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gurpreetsingh on 26/08/17.
 */

public class MangedhaKnitActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    int RC_SIGN_IN = 302;
    GoogleApiClient mGoogleApiClient;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mangedhaLoader = MangedhaLoader.init(this);
        googleLoginSetup();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void googleLoginSetup(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("125493381394-gro1m0vn6tbh2ikq8qooq3qsi7vs9k02.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    void googleLogin(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AlertHelper.error("Google Login failed. Please try again!", this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            mangedhaLoader.start();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                Map<String, String> registerUser  = RegisterUser.call(account.getEmail(), account.getDisplayName(), "", "", "", "");
                RestAdapter.get().socialLogin(registerUser).enqueue(new Callback<UserLoginModel>() {
                    @Override
                    public void onResponse(Call<UserLoginModel> call, Response<UserLoginModel> response) {
                        UserLoginModel userLoginModel = response.body();
                        mangedhaLoader.stop();
                        if(userLoginModel.is_success()){
                            UserHelper.login(userLoginModel);
                            Intent intent = new Intent(MangedhaKnitActivity.this, ProductsActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            AlertHelper.error(userLoginModel.getError(), MangedhaKnitActivity.this);
                        }

                    }

                    @Override
                    public void onFailure(Call<UserLoginModel> call, Throwable t) {
                        mangedhaLoader.stop();
                        AlertHelper.error(t.getMessage(), MangedhaKnitActivity.this);
                    }
                });
            } else {
                mangedhaLoader.stop();
                AlertHelper.error("Google Login failed. Please try again!", this);
            }
        }
    }


}
