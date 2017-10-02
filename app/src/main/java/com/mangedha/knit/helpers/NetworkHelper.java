package com.mangedha.knit.helpers;

/**
 * Created by Akshit on 26/04/2017.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mangedha.knit.MangedhaApplication;

/**
 * Created by bhavan on 1/4/17.
 */

public class NetworkHelper {

    public static boolean state() {
        final ConnectivityManager conMgr = (ConnectivityManager) MangedhaApplication.getMangedhaApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) return true;
        return false;
    }

}
