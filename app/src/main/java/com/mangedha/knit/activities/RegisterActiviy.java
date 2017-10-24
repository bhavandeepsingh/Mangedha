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
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.UserLoginModel;
import com.mangedha.knit.http.models.http_request.RegisterUser;

import java.util.Map;

public class RegisterActiviy extends MangedhaKnitActivity implements View.OnClickListener {

    TextView alredyhave_text;
    Context context = RegisterActiviy.this;
    ImageView backarroww;

    EditText register_name, register_email, register_mobile, register_city, register_zip_code, register_state, register_password;
    TextView register_button;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activiy);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context,R.color.clr_white));
        }
        init();
        backarroww = (ImageView) findViewById(R.id.backarroww);
        backarroww.setOnClickListener(this);
    }

    void init(){
        alredyhave_text=(TextView)findViewById(R.id.alredyhave_text);
        spanableaText();

        register_name = (EditText) findViewById(R.id.register_name);
        register_email = (EditText) findViewById(R.id.register_email);
        register_mobile = (EditText) findViewById(R.id.register_mobile);
        register_city = (EditText) findViewById(R.id.register_city);
        register_zip_code = (EditText) findViewById(R.id.register_zip_code);
        register_state = (EditText) findViewById(R.id.register_state);
        register_password = (EditText) findViewById(R.id.register_password);
        register_button = (TextView) findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        mangedhaLoader = MangedhaLoader.init(this);
    }


    void spanableaText(){
        Spannable wordtoSpan = new SpannableString(getString(R.string.alreadyhave));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                finish();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(ContextCompat.getColor(context,R.color.colorPrimary));
            }
        };
        wordtoSpan.setSpan(clickableSpan, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        alredyhave_text.setText(wordtoSpan);
        alredyhave_text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backarroww){
            finish();
        }
        if(view.getId() == R.id.register_button){
            register();
        }
    }

    void register(){

        String mobile_pattern = "[0-9]{10}";
        String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String name_text = register_name.getText().toString().trim();
        String email_text = register_email.getText().toString().trim();
        String mobile_text = register_mobile.getText().toString().trim();
        String city_text = register_city.getText().toString().trim();
        String state_text = register_state.getText().toString().trim();
        String zip_code_text = register_zip_code.getText().toString().trim();
        String password_text = register_password.getText().toString().trim();

        boolean submit = true;
        if(name_text.equals("")){
            register_name.setError("Name is required!");
            submit = false;
        }

        if(email_text.equals("")){
            register_email.setError("Email is required!");
            submit = false;
        }

        if((!email_text.equals("") && !email_text.matches(email_pattern))){
            register_email.setError("Not a valid Email!");
            submit =false;
        }

        if(mobile_text.equals("")){
            register_mobile.setError("Mobile is required!");
            submit =false;
        }

        if((!mobile_text.equals("") && !mobile_text.matches(mobile_pattern))){
            register_mobile.setError("Not a valid Mobile Number!");
            submit =false;
        }

        if(city_text.equals("")){
            register_city.setError("City is required!");
            submit =false;
        }

        if(state_text.equals("")){
            register_state.setError("State is required!");
            submit =false;
        }

        if(password_text.equals("")){
            register_password.setError("Password is required!");
            submit =false;
        }

        if(!submit){
            return;
        }

        mangedhaLoader.start();
        Map<String, String> registerUser  = RegisterUser.call(email_text, password_text, name_text, mobile_text, city_text, state_text, zip_code_text);
        UserLoginModel.register(registerUser, new UserLoginModel.Register() {
            @Override
            public void onSuccess(UserLoginModel userLoginModel) {
                mangedhaLoader.stop();
                UserHelper.login(userLoginModel);
                AlertHelper.success("Register Succesfully", RegisterActiviy.this);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Intent intent = new Intent(RegisterActiviy.this,
                                        ProductsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        },
                        300);
            }

            @Override
            public void onLoginFail(String message) {
                mangedhaLoader.stop();
                AlertHelper.error(message, RegisterActiviy.this);
            }

            @Override
            public void onFail(Throwable t) {
                mangedhaLoader.stop();
                AlertHelper.error(t.getMessage(), RegisterActiviy.this);
            }
        });
    }

}
