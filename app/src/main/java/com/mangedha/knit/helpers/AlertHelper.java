package com.mangedha.knit.helpers;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by bhavan on 9/7/17.
 */

public class AlertHelper {

    public static AlertDialog get(String message, Context context, String title){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        return alertDialog;
    }

    public static void error(String message, final Context context){
        get(message, context, "Error").show();
    }

    public static void success(String message, final Context context){
        get(message, context, "Success").show();
    }

}
