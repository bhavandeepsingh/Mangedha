package com.mangedha.knit.helpers;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Akshit on 26/04/2017.
 */

public class MangedhaLoader extends ProgressDialog {

    static MangedhaLoader mangedhaLoader;

    public MangedhaLoader(Context context) {
        super(context);
    }

    public static MangedhaLoader init(Context context) {
        mangedhaLoader  = new MangedhaLoader (context);
        mangedhaLoader.setProgressStyle(mangedhaLoader.STYLE_SPINNER);
        mangedhaLoader.setCancelable(false);
        mangedhaLoader.setMessage("Loading...");
        return mangedhaLoader;
    }

    public static void init(Context context, String msg) {
        mangedhaLoader = new MangedhaLoader (context);
        mangedhaLoader.setMessage(msg);
    }

    public MangedhaLoader start() {
        mangedhaLoader.show();
        return this;
    }

    public void stop() {
        mangedhaLoader.hide();
    }

}