package com.mangedha.knit.helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MangedhaKnitActivity;
import com.mangedha.knit.layouts.TextInputLayout;

/**
 * Created by bhavan on 11/4/17.
 */

public class AlertEmailRequred {

    public static void show(Context context, final  AlertEmailInterface alertEmailInterface) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = ((MangedhaKnitActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.email_alert_dialog, null);

        TextView ok_button = (TextView) dialogView.findViewById(R.id.ok_button);
        TextView cancel_button = (TextView) dialogView.findViewById(R.id.cancel_button);
        final EditText alert_email = (EditText) dialogView.findViewById(R.id.alert_email);
        final TextInputLayout alert_email_layout = (TextInputLayout) dialogView.findViewById(R.id.alert_email_layout);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = alert_email.getText().toString().trim();

                if(email .equals("")){
                    alert_email_layout.setError("Email is required!");
                }else if(!email.equals("") && !email.matches(email_pattern)){
                    alert_email_layout.setError("Not a valid Email");
                }else {
                    alert_email_layout.setError(null);
                    alertDialog.hide();
                    alertEmailInterface.onEmailSuccess(email);
                }
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });


        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public interface AlertEmailInterface{
        void onEmailSuccess(String email);
    }

}
