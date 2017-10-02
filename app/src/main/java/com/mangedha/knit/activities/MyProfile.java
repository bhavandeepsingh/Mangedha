package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.UserLoginModel;

public class MyProfile extends AppCompatActivity {

    TextView toolbartitle;
    Context context =MyProfile.this;

    UserLoginModel userLoginModel;
    TextView profile_name, profile_mobile, profile_email, profile_city, profile_state, profile_zip_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    void init(){
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("My Profile");
        profile_name = (TextView) findViewById(R.id.profile_name);
        profile_name.setText(UserHelper.getName());

        profile_email = (TextView) findViewById(R.id.profile_email);
        profile_email.setText(UserHelper.getEmail());

        profile_mobile = (TextView) findViewById(R.id.profile_mobile);
        profile_mobile.setText(UserHelper.getAppUserMobile());

        profile_city = (TextView) findViewById(R.id.profile_city);
        profile_city.setText(UserHelper.getAppUserCity());

        profile_state = (TextView) findViewById(R.id.profile_state);
        profile_state.setText(UserHelper.getAppUserState());

        profile_zip_code = (TextView) findViewById(R.id.profile_zip_code);
        profile_zip_code.setText(UserHelper.getAppUserZipCode());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_myprofile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            onBackPressed();
        }if (id==R.id.action_edit){
            Intent intent = new Intent(context,EditProfile.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
