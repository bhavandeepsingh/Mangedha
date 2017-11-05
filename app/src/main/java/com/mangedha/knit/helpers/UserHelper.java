package com.mangedha.knit.helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mangedha.knit.MangedhaApplication;
import com.mangedha.knit.http.models.ProfileModel;
import com.mangedha.knit.http.models.UserLoginModel;

/**
 * Created by admin on 4/25/2017.
 */

public class UserHelper {

    static String APP_USER_LOGIN_ID = "APP_USER_LOGIN_ID";
    static String APP_USER_NAME = "APP_USER_NAME";
    static String APP_USER_EMAIL = "APP_USER_EMAIL";
    static String APP_USER_MEMBER_SHIP_NO = "APP_USER_TIME_MEMBER_SHIP_NO";
    static String APP_USER_MOBILE = "APP_USER_MOBILE";
    static String APP_FIRST_RUN = "APP_FIRST_RUN";
    static String APP_USER_AUTH_KEY= "APP_USER_AUTH_KEY";
    static String APP_USER_CITY = "APP_USER_CITY";
    static String APP_USER_STATE = "APP_USER_STATE";
    static String APP_USER_ZIP_CODE = "APP_USER_ZIP_CODE";
    static String APP_USER_LOGIN_STATUS = "APP_USER_LOGIN_STATUS";



    public static boolean login(UserLoginModel userLoginModel){
        if(userLoginModel == null) return false;
        return setSharedPreferences(userLoginModel.getProfileModel());
    }

    public static boolean logout(){
        return updateAppUser(APP_USER_LOGIN_STATUS, "0");
    }

    public static boolean getState(){
        if(getAppUserLoginStatus().equals("1")) return true;
        return false;
    }

    public static long getId(){
        return Long.valueOf(getSharedPreferences().getString(APP_USER_LOGIN_ID, "0"));
    }

    public static String getName(){
        return getSharedPreferences().getString(APP_USER_NAME, "");
    }

    public static String getEmail(){
        return getSharedPreferences().getString(APP_USER_EMAIL, "");
    }

    public static String getAppUserLoginId() {
        return getSharedPreferences().getString(APP_USER_LOGIN_ID, "");
    }

    public static int getLoginId() {
        return Integer.parseInt(getSharedPreferences().getString(APP_USER_LOGIN_ID, ""));
    }

    public static void setAppUserLoginId(String appUserLoginId) {
        APP_USER_LOGIN_ID = appUserLoginId;
    }

    public static String getAppUserName() {
        return getSharedPreferences().getString(APP_USER_NAME, "");
    }

    public static void setAppUserName(String appUserName) {
        APP_USER_NAME = appUserName;
    }

    public static String getAppUserEmail() {
        return getSharedPreferences().getString(APP_USER_EMAIL, "");
    }

    public static void setAppUserEmail(String appUserEmail) {
        APP_USER_EMAIL = appUserEmail;
    }

    public static String getAppUserMemberShipNo() {
        return getSharedPreferences().getString(APP_USER_MEMBER_SHIP_NO, "");
    }

    public static void setAppUserMemberShipNo(String appUserMemberShipNo) {
        APP_USER_MEMBER_SHIP_NO = appUserMemberShipNo;
    }

    public static String getAppUserMobile() {
        return getSharedPreferences().getString(APP_USER_MOBILE, "");
    }

    public static void setAppUserMobile(String appUserMobile) {
        boolean update = updateAppUser(APP_USER_MOBILE, appUserMobile);
        APP_USER_MOBILE = appUserMobile;
    }


    public static String getAppUserLoginStatus() {
        return getSharedPreferences().getString(APP_USER_LOGIN_STATUS, "0");
    }

    public static void setAppUserLoginStatus(String appUserLoginStatus) {
        APP_USER_LOGIN_STATUS = appUserLoginStatus;
    }

    static boolean setSharedPreferences(ProfileModel profileModel){
        if(!updateAppUser(APP_USER_LOGIN_ID, String.valueOf(profileModel.getUser_id()))) return false;
        updateAppUser(APP_USER_LOGIN_STATUS, "1");
        if(!updateAppUser(APP_USER_NAME, profileModel.getName())) return false;
        if(!updateAppUser(APP_USER_EMAIL, profileModel.getEmail())) return false;
        if(!updateAppUser(APP_USER_MOBILE, profileModel.getMobile())) return false;
        if(!updateAppUser(APP_USER_NAME, profileModel.getName())) return false;
        if(!updateAppUser(APP_USER_AUTH_KEY, profileModel.getAuth_key())) return false;
        if(!updateAppUser(APP_USER_CITY, profileModel.getCity())) return false;
        if(!updateAppUser(APP_USER_STATE, profileModel.getState())) return false;
        if(!updateAppUser(APP_USER_ZIP_CODE, profileModel.getZip_code())) return false;
        return true;
    }


    static boolean updateAppUser(String name, String value){
        SharedPreferences.Editor editor = getEditor();
        editor.putString(name, value);
        return editor.commit();
    }

    static SharedPreferences.Editor getEditor(){
        return getSharedPreferences().edit();
    }

    public static SharedPreferences getSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(MangedhaApplication.getMangedhaApplicationContext());
    }

    public static boolean getFirstRun() {
        return getSharedPreferences().getBoolean(APP_FIRST_RUN, true);
    }

    public static void setFirstRun(){
        getSharedPreferences().edit().putBoolean(APP_FIRST_RUN, false).commit();
    }


    public static boolean getUserLoginStatus(){
        return (getAppUserLoginStatus().equals("1"))?true: false;
    }


    public static String getAppUserAuthKey() {
        return getSharedPreferences().getString(APP_USER_AUTH_KEY, "");
    }

    public static void setAppUserAuthKey(String appUserAuthKey) {
        getSharedPreferences().edit().putString(APP_USER_AUTH_KEY, appUserAuthKey).commit();
    }

    public static String getAppFirstRun() {
        return getSharedPreferences().getString(APP_FIRST_RUN, "");
    }

    public static String getAppUserCity() {
        return getSharedPreferences().getString(APP_USER_CITY, "");
    }

    public static String getAppUserState() {
        return getSharedPreferences().getString(APP_USER_STATE, "");
    }

    public static String getAppUserZipCode() {
        return getSharedPreferences().getString(APP_USER_ZIP_CODE, "");
    }
    
}
