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
 * Created by bhavan on 11/5/17.
 */

public class AlertMoblieRequired {

    public static void show(Context context, final AlertMobileInterface alertMobileInterface) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = ((MangedhaKnitActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.moblie_alert_dialog, null);

        TextView ok_button = (TextView) dialogView.findViewById(R.id.ok_button);
        TextView cancel_button = (TextView) dialogView.findViewById(R.id.cancel_button);
        final EditText alert_moblie = (EditText) dialogView.findViewById(R.id.alert_moblie);
        final TextInputLayout alert_moblie_layout = (TextInputLayout) dialogView.findViewById(R.id.alert_mobile_layout);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile_pattern = "[0-9]{10}";
                String mobile = alert_moblie.getText().toString().trim();

                if(mobile.equals("")){
                    alert_moblie_layout.setError("Mobile is required!");
                }else if(!mobile.equals("") && !mobile.matches(mobile_pattern)){
                    alert_moblie_layout.setError("Not a valid Mobile");
                }else {
                    alert_moblie_layout.setError(null);
                    alertDialog.hide();
                    UserHelper.setAppUserMobile(mobile);
                    alertMobileInterface.onMobileSuccess(mobile);
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

    public interface AlertMobileInterface {
        void onMobileSuccess(String mobile);
    }

}
