package com.mangedha.knit.helpers;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mangedha.knit.R;
import com.mangedha.knit.activities.MangedhaKnitActivity;

/**
 * Created by bhavan on 9/7/17.
 */

public class AlertHelper {

    public static Toast get(String message, Context context, String title){
        /*AlertDialog alertDialog = new AlertDialog.Builder(context).create();
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

        return alertDialog;*/
        LayoutInflater inflater = ((MangedhaKnitActivity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.mytoast, (ViewGroup) ((MangedhaKnitActivity) context).findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        final Toast toast = new Toast(context);
//        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1100);
        return  toast;

    }

    public static void error(String message, final Context context){
        get(message, context, "Error").show();
    }

    public static void success(String message, final Context context){
        get(message, context, "Success").show();
    }

}
