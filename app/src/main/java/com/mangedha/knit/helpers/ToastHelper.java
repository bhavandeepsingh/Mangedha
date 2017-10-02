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

    public static void showRefreshComplete(Context context) {
        showToast("Refreshed Successfully", context);
    }

    public static void showAlreadyUptodate(Context context) {
        showToast("Already Updated", context);
    }

    public static void showNoNetwork(Context context) {
        showToast("No Network please try again!", context);
    }

    public static void showTakingBitLong(Context context) {
        showToast("Taking a bit long!", context);
    }

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

    public static void pleaseFillLicenece(Context context) {
        showToast("Please fill licence no", context);
    }

    public static void pleaseFillPassword(Context context) {
        showToast("Please fill password", context);
    }

    public static void loginCreditionalFailur(Context context) {
        showToast("Username or password that enter you wrong!", context);
    }

    public static void showLogoutFailuer(Context applicationContext) {
        showToast("Unable to logout please try again!", applicationContext);
    }

    public static void passwordResetSuccessfully(Context context) {
        showToast("Password reset successfully", context);
    }

    public static void showPleaseFillOldPassword(Context context) {
        showToast("Please fill old password!", context);
    }

    public static void showPleaseFillNewPassword(Context context) {
        showToast("Please fill new password!", context);
    }

    public static void showPleaseFillConfirmPassword(Context context) {
        showToast("Please fill confirm password!", context);
    }

    public static void showNewPasswordAreNotSame(Context context) {
        showToast("New password and confirm are not same!", context);
    }

    public static void showPasswordChnageSuccess(Context context) {
        showToast("Password change successfully", context);
    }

    public static void showPasswordChnageFailur(Context context) {
        showToast("Password not change please try again!", context);
    }

    public static void showEmailNotFill(Context context) {
        showToast("Email cannot be blank!", context);
    }

    public static void showLanlineNotFill(Context context) {
        showToast("Landline cannot be blank!", context);
    }

    public static void showMoblieNotFill(Context context) {
        showToast("Mobile cannot be blank!", context);
    }

    public static void showResidentialNotFill(Context context) {
        showToast("Residential cannot be blank!", context);
    }

    public static void showCourtAddressNotFill(Context context) {
        showToast("Court Address cannot be blank!", context);
    }

    public static void showBloodgroupNotFill(Context context) {
        showToast("Blood group cannot be blank!", context);
    }

    public static void profileUpdateFail(Context context) {
        showToast("Profile not update please try again!", context);
    }

    public static void profileUpdateSuccess(Context context) {
        showToast("Profile update successfully!", context);
    }

    public static void showProfileName(Context context) {
        showToast("Profile name cannot be blank!", context);
    }
}
