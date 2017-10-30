package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.UserLoginModel;
import com.mangedha.knit.http.models.http_request.RegisterUser;
import com.mangedha.knit.layouts.TextInputLayout;

import java.util.Map;

public class EditProfile extends MangedhaKnitActivity implements View.OnClickListener {

    TextView toolbartitle;
    Context context =EditProfile.this;
    EditText profile_name, profile_mobile, profile_email, profile_city, profile_state, profile_zip_code;
    TextInputLayout profile_name_layout, profile_mobile_layout, profile_email_layout, profile_city_layout, profile_state_layout, profile_zip_code_layout;
    TextView profile_save_button;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        init();

    }

    void init(){
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("Edit Profile");

        profile_name = (EditText) findViewById(R.id.profile_name);
        profile_name.setText(UserHelper.getName());

        profile_email = (EditText) findViewById(R.id.profile_email);
        profile_email.setText(UserHelper.getEmail());

        profile_mobile = (EditText) findViewById(R.id.profile_mobile);
        profile_mobile.setText(UserHelper.getAppUserMobile());

        profile_city = (EditText) findViewById(R.id.profile_city);
        profile_city.setText(UserHelper.getAppUserCity());

        profile_state = (EditText) findViewById(R.id.profile_state);
        profile_state.setText(UserHelper.getAppUserState());

        profile_zip_code = (EditText) findViewById(R.id.profile_zip_code);
        profile_zip_code.setText(UserHelper.getAppUserZipCode());

        profile_save_button = (TextView) findViewById(R.id.profile_save_button);
        profile_save_button.setOnClickListener(this);

        profile_name_layout = (TextInputLayout) findViewById(R.id.profile_name_layout);
        profile_mobile_layout = (TextInputLayout) findViewById(R.id.profile_mobile_layout);
        profile_email_layout = (TextInputLayout) findViewById(R.id.profile_email_layout);
        profile_city_layout = (TextInputLayout) findViewById(R.id.profile_city_layout);
        profile_state_layout = (TextInputLayout) findViewById(R.id.profile_state_layout);
        profile_zip_code_layout = (TextInputLayout) findViewById(R.id.profile_zip_code_layout);

        mangedhaLoader = MangedhaLoader.init(this);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    void click_saveprofile(View view){
        Intent intent = new Intent(context,MyProfile.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.profile_save_button){
            profileSave();
        }

    }

    void profileSave(){
        String mobile_pattern = "[0-9]{10}";
        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String name_text = profile_name.getText().toString().trim();
        String email_text = profile_email.getText().toString().trim();
        String mobile_text = profile_mobile.getText().toString().trim();
        String city_text = profile_city.getText().toString().trim();
        String state_text = profile_state.getText().toString().trim();
        String zip_code_text = profile_zip_code.getText().toString().trim();

        boolean submit = true;

        if(name_text.equals("")){
            profile_name_layout.setError("Name is required!");
            submit = false;
        }else{
            profile_name_layout.setError(null);
        }

        if(email_text.equals("")){
            profile_email_layout.setError("Email is required!");
            submit = false;
        }else if(!email_text.equals("") && !email_text.matches(email_pattern)){
            profile_email_layout.setError("Not a valid Email");
            submit = false;
        }else{
            profile_email_layout.setError(null);
        }

        if(mobile_text.equals("")){
            profile_mobile_layout.setError("Mobile is required!");
            submit = false;
        }else if(!mobile_text.equals("") && !mobile_text.matches(mobile_pattern)){
            profile_mobile_layout.setError("Not a valid Mobile Number");
            submit = false;
        }else{
            profile_mobile_layout.setError(null);
        }

        if(city_text.equals("")){
            profile_city_layout.setError("City is required!");
            submit = false;
        }else {
            profile_city_layout.setError(null);
        }

        if(state_text.equals("")){
            profile_state_layout.setError("State is required!");
            submit = false;
        }else{
            profile_state_layout.setError(null);
        }

        if(!submit){
            return;
        }


        hideKeyboard();

        mangedhaLoader.start();
        Map<String, String> registerUser  = RegisterUser.call(email_text, name_text, mobile_text, city_text, state_text, zip_code_text);
        UserLoginModel.update(registerUser, new UserLoginModel.Register() {
            @Override
            public void onSuccess(UserLoginModel userLoginModel) {
                mangedhaLoader.stop();
                AlertHelper.success("Update Successfully", EditProfile.this);
            }

            @Override
            public void onLoginFail(String message) {
                mangedhaLoader.stop();
                AlertHelper.error(message, EditProfile.this);
            }

            @Override
            public void onFail(Throwable t) {
                mangedhaLoader.stop();
                AlertHelper.error(t.getMessage(), EditProfile.this);
            }
        });
    }
}
