package com.mangedha.knit.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.http.models.http_request.ResetMyPassword;
import com.mangedha.knit.layouts.TextInputLayout;

public class ForgotPasswordActivity extends MangedhaKnitActivity implements View.OnClickListener {

    Context context = ForgotPasswordActivity.this;
    ImageView backarrow;
    EditText forgot_password_email;
    TextView forgot_password_password_send_button;
    TextInputLayout forgot_password_email_layout;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context,R.color.clr_white));
        }
        init();
        backarrow = (ImageView) findViewById(R.id.backarrow);
        backarrow.setOnClickListener(this);

        forgot_password_email = (EditText) findViewById(R.id.forgot_password_email);
        forgot_password_password_send_button = (TextView) findViewById(R.id.forgot_password_password_send_button);
        forgot_password_password_send_button.setOnClickListener(this);

        forgot_password_email_layout = (TextInputLayout) findViewById(R.id.forgot_password_email_layout);

        mangedhaLoader = MangedhaLoader.init(this);
    }

    void init() {
        backarrow = (ImageView) findViewById(R.id.backarrow);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backarrow){
            finish();
        }
        if(view.getId() == R.id.forgot_password_password_send_button){
            passwordResnd();
        }
    }

    void passwordResnd(){
        String email = forgot_password_email.getText().toString().trim();
        if(email.equals("")){
            forgot_password_email_layout.setError("Email is Required!");
            return;
        }else{
            forgot_password_email_layout.setError(null);
        }

        mangedhaLoader.start();
        ResetMyPassword.resetPassword(email, new ResetMyPassword.ResetMyPasswordInterface() {
            @Override
            public void onResetPasswordSuccess(ResetMyPassword resetMyPassword) {
                mangedhaLoader.stop();
                AlertHelper.success("New Password send in your mail. Please check.", ForgotPasswordActivity.this);
            }

            @Override
            public void onResetPasswordError(Throwable t) {
                mangedhaLoader.stop();
                AlertHelper.error(t.getMessage(), ForgotPasswordActivity.this);
            }

            @Override
            public void onResetPasswordFailure(ResetMyPassword resetMyPassword) {
                mangedhaLoader.stop();
                AlertHelper.error(resetMyPassword.getError(), ForgotPasswordActivity.this);
            }
        });
    }
}
