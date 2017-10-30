package com.mangedha.knit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.http.models.UserLoginModel;
import com.mangedha.knit.layouts.TextInputLayout;

public class LoginActivity extends MangedhaKnitActivity implements View.OnClickListener {

    TextView newuser_text, forgot_password, login_button, login_error_message;
    Context context = LoginActivity.this;
    EditText user_email, user_password;
    TextInputLayout user_email_layout, user_password_layout;
    MangedhaLoader mangedhaLoader;
    TextView google_login_button, button_facebook_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context,R.color.clr_white));
        }
        setContentView(R.layout.activity_login);
        init();
        setUpFacebookLogin();
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(this);

        login_button = (TextView) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        user_email_layout = (TextInputLayout) findViewById(R.id.user_email_layout);
        user_password_layout = (TextInputLayout) findViewById(R.id.user_password_layout);

        login_error_message = (TextView) findViewById(R.id.login_error_message);

        mangedhaLoader = MangedhaLoader.init(this);
        google_login_button = (TextView) findViewById(R.id.google_login_button);
        google_login_button.setOnClickListener(this);

        button_facebook_login = (TextView) findViewById(R.id.button_facebook_login);
        button_facebook_login.setOnClickListener(this);

    }

    void init(){
        newuser_text=(TextView)findViewById(R.id.newuser_text);
        spanableaText();
    }

    void spanableaText(){
        Spannable wordtoSpan = new SpannableString(getString(R.string.newuser));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(context, RegisterActiviy.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(context,R.color.colorPrimary));
            }
        };
        wordtoSpan.setSpan(clickableSpan, 10, wordtoSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        newuser_text.setText(wordtoSpan);
        newuser_text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.forgot_password){
            Intent intent = new Intent(context, ForgotPasswordActivity.class);
            startActivity(intent);
        }

        if(view.getId() == R.id.login_button){
            loginFormSubmit();
        }

        if(view.getId() == R.id.google_login_button){
            googleLogin();
        }

        if(view.getId() == R.id.button_facebook_login){
            facebookLogin();
        }
    }

    void loginFormSubmit(){

        login_error_message.setVisibility(View.GONE);

        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        boolean submit = true;
        if(email.equals("")){
            user_email_layout.setError("Email is required!");
            submit = false;
        }else if(!email.equals("") && !email.matches(email_pattern)){
            user_email_layout.setError("Email is not valid!");
            submit = false;
        }else {
            user_email_layout.setError(null);
        }

        if(password.equals("")){
            user_password_layout.setError("Password Required!");
            submit = false;
        }else {
            user_password_layout.setError(null);
        }

        if(!submit){
            return;
        }

        mangedhaLoader.start();
        UserLoginModel.login(email, password, new UserLoginModel.Login() {
            @Override
            public void onSuccess(UserLoginModel userLoginModel) {
                mangedhaLoader.stop();
                Intent intent = new Intent(context, ProductsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoginFail(String message) {
                mangedhaLoader.stop();
                login_error_message.setText(message);
                login_error_message.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable t) {
                mangedhaLoader.stop();
            }
        });
    }



}
