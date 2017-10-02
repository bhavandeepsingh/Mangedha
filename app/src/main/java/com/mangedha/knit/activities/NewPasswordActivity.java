package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mangedha.knit.R;

public class NewPasswordActivity extends AppCompatActivity {

    Context context = NewPasswordActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }

    void click_login(View view){
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }

    void click_backarrow(View view){
        onBackPressed();
    }


}
