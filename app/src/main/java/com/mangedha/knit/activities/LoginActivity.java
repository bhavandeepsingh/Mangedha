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
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.http.models.UserLoginModel;

public class LoginActivity extends MangedhaKnitActivity implements View.OnClickListener {

    TextView newuser_text, forgot_password, login_button;
    Context context = LoginActivity.this;
    EditText user_email, user_password;
    MangedhaLoader mangedhaLoader;
    TextView google_login_button;

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
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(this);

        login_button = (TextView) findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);

        mangedhaLoader = MangedhaLoader.init(this);
        google_login_button = (TextView) findViewById(R.id.google_login_button);
        google_login_button.setOnClickListener(this);
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
    }

    void loginFormSubmit(){

        String email = user_email.getText().toString();
        String password = user_password.getText().toString();
        String error_txt = "";
        if(email.length() <= 0){
            error_txt = "Email Required! \n";
        }
        if(password.length() <= 0){
            error_txt  += "Password Required!\n";
        }
        if(error_txt.length() > 0){
            AlertHelper.error(error_txt, this);
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
                AlertHelper.error(message, LoginActivity.this);
            }

            @Override
            public void onFail(Throwable t) {
                mangedhaLoader.stop();
                AlertHelper.error(t.getMessage(), LoginActivity.this);
            }
        });
    }


}
