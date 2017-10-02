package com.mangedha.knit.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.http.models.http_interface.ChangePasswordModel;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = ChangePasswordActivity.this;

    EditText old_password, new_password, confirm_password;
    TextView password_change_button;
    ImageView backarroww;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context,R.color.clr_white));
        }
        init();
    }

    void init(){
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        password_change_button = (TextView) findViewById(R.id.password_change_button);
        password_change_button.setOnClickListener(this);

        backarroww = (ImageView) findViewById(R.id.backarroww);
        backarroww.setOnClickListener(this);

        mangedhaLoader = MangedhaLoader.init(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.password_change_button){
            updatePassword();
        }
        if(view.getId() == R.id.backarroww){
            finish();
        }
    }

    void updatePassword(){
        final String old_password_text = old_password.getText().toString();
        String new_password_text = new_password.getText().toString();
        String confirm_password_text = confirm_password.getText().toString();

        if(old_password_text.length() <= 0){
            AlertHelper.error("Old password required!", this);
            return;
        }

        if(new_password_text.length() <= 0){
            AlertHelper.error("New password required!", this);
            return;
        }

        if(confirm_password_text.length() <= 0){
            AlertHelper.error("Confirm password required!", this);
            return;
        }

        if(!confirm_password_text.equals(new_password_text)){
            AlertHelper.error("New and Confirm password not matched!", this);
            return;
        }

        mangedhaLoader.start();

        ChangePasswordModel.changePassword(old_password_text, new_password_text, new ChangePasswordModel.MangedhaChangePasswordInterface() {
            @Override
            public void onPasswordChangeSuccess(ChangePasswordModel changePasswordModel) {
                mangedhaLoader.stop();
                AlertHelper.success("Password update succesfully", ChangePasswordActivity.this);
                clearFormData();
            }

            @Override
            public void onPasswordChangeError(Throwable t) {
                mangedhaLoader.stop();
                AlertHelper.error(t.getMessage(), ChangePasswordActivity.this);
            }

            @Override
            public void onPasswordChangeFailure(ChangePasswordModel changePasswordModel) {
                mangedhaLoader.stop();
                AlertHelper.error(changePasswordModel.getError(), ChangePasswordActivity.this);
            }
        });
    }

    void clearFormData(){
        old_password.setText("");
        new_password.setText("");
        confirm_password.setText("");
    }

}
