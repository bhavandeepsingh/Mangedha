package com.mangedha.knit.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MangedhaKnitActivity;

/**
 * Created by bhavan on 9/7/17.
 */

public class AlertHelper {

    public static AlertDialog get(String message, Context context, String title){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = ((MangedhaKnitActivity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);

        TextView alert_title = (TextView) dialogView.findViewById(R.id.alert_title);
        alert_title.setText(title);

        TextView alert_message = (TextView) dialogView.findViewById(R.id.alert_message);
        alert_message.setText(message);

        if(!title.equals("Success")){
            RelativeLayout alert_diveder = (RelativeLayout) dialogView.findViewById(R.id.alert_diveder);
            alert_diveder.setBackgroundColor(ContextCompat.getColor(context, R.color.error));
        }

        alertDialog.setView(dialogView);

        return alertDialog;
    }

    public static void error(String message, final Context context){
        get(message, context, "Error").show();
    }

    public static void success(String message, final Context context){
        get(message, context, "Success").show();
    }

}
