package com.high.court.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bhavan on 3/10/17.
 */

/**
 * Created by Akshit on 26/04/2017.
 */

public class ToastHelper {


    public static void showToast(String message, Context context) {
       /* LayoutInflater inflater = HighCourtApplication.getHighCourtActivity(context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.mytoast, (ViewGroup) HighCourtApplication.getHighCourtActivity(context).findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();**/
       Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
