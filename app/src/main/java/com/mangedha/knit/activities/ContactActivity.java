package com.mangedha.knit.activities;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.helpers.AlertHelper;
import com.mangedha.knit.helpers.MangedhaLoader;
import com.mangedha.knit.http.models.ContactModel;
import com.mangedha.knit.layouts.TextInputLayout;

import java.util.Map;

public class ContactActivity extends MangedhaKnitActivity implements View.OnClickListener {

    TextView toolbartitle;
    EditText contact_name, contact_email, contact_subject, contact_message;
    TextInputLayout contact_name_layout, contact_email_layout, contact_subject_layout, contact_message_layout;
    TextView contact_send;
    MangedhaLoader mangedhaLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        init();
    }

   void init(){
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("Contact us");
        contact_name = (EditText) findViewById(R.id.contact_name);
        contact_email = (EditText) findViewById(R.id.contact_email);
        contact_subject = (EditText) findViewById(R.id.contact_subject);
        contact_message = (EditText) findViewById(R.id.contact_message);

        contact_send = (TextView) findViewById(R.id.contact_send);

       contact_name_layout = (TextInputLayout) findViewById(R.id.contact_name_layout);
       contact_email_layout = (TextInputLayout) findViewById(R.id.contact_email_layout);
       contact_subject_layout = (TextInputLayout) findViewById(R.id.contact_subject_layout);
       contact_message_layout = (TextInputLayout) findViewById(R.id.contact_message_layout);

        contact_send.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.contact_send){
            formSubmit();
        }

    }

    void formSubmit(){
        String error_message = "";

        boolean submit = true;
        if(contact_name.getText().toString().trim().equals("")){
            contact_name_layout.setError("Name is required.");
            submit = false;
        }else {
            contact_name_layout.setError(null);
        }

        if(contact_message.getText().toString().trim().equals("")){
            contact_message_layout.setError("Message is required.");
            submit = false;
        }else{
            contact_message_layout.setError(null);
        }

        if(contact_subject.getText().toString().trim().equals("")){
            contact_subject_layout.setError("Subject is required.");
            submit = false;
        }else{
            contact_subject_layout.setError(null);
        }

        if(contact_email.getText().toString().trim().equals("")){
            contact_email_layout.setError("Email is required.");
            submit = false;
        }else{
            contact_email_layout.setError(null);
        }

        if(!submit){
            return;
        }

        hideKeyboard();

        mangedhaLoader.start();

        ContactModel.submit(makeCall(), new ContactModel.ContactInterface() {
            @Override
            public void onSuccess(ContactModel contactModel) {
                mangedhaLoader.stop();
                AlertHelper.success(contactModel.getMessage(), ContactActivity.this);
            }

            @Override
            public void onFail(String error) {
                mangedhaLoader.stop();
                AlertHelper.error(error, ContactActivity.this);
            }
        });

    }

    private Map<String,String> makeCall() {
        Map<String, String > stringStringMap = new ArrayMap<>();
        stringStringMap.put("ContactUs[email]", contact_email.getText().toString());
        stringStringMap.put("ContactUs[name]", contact_name.getText().toString());
        stringStringMap.put("ContactUs[subject]", contact_subject.getText().toString());
        stringStringMap.put("ContactUs[message]", contact_message.getText().toString());
        return stringStringMap;
    }
}
