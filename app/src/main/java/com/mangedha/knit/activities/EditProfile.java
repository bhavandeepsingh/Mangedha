package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Map;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    TextView toolbartitle;
    Context context =EditProfile.this;
    EditText profile_name, profile_mobile, profile_email, profile_city, profile_state, profile_zip_code;
    TextView profile_save_button;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        String name_text = profile_name.getText().toString();
        String email_text = profile_email.getText().toString();
        String mobile_text = profile_mobile.getText().toString();
        String city_text = profile_city.getText().toString();
        String state_text = profile_state.getText().toString();
        String zip_code_text = profile_zip_code.getText().toString();

        if(name_text.length() <= 0){
            AlertHelper.error("Name is required!", this);
            return;
        }

        if(email_text.length() <= 0){
            AlertHelper.error("Email is required!", this);
            return;
        }

        if(!email_text.matches(email_pattern)){
            AlertHelper.error("Not a valid Email", this);
            return;
        }

        if(mobile_text.length() <= 0){
            AlertHelper.error("Mobile is required!", this);
            return;
        }

        if(!mobile_text.matches(mobile_pattern)){
            AlertHelper.error("Not a valid Mobile Number", this);
            return;
        }

        if(city_text.length() <= 0){
            AlertHelper.error("City is required!", this);
            return;
        }

        if(state_text.length() <= 0){
            AlertHelper.error("State is required!", this);
            return;
        }


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
