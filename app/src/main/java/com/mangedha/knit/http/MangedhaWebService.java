package com.mangedha.knit.http;

import com.mangedha.knit.http.models.AboutUsModel;
import com.mangedha.knit.http.models.CategoriesModel;
import com.mangedha.knit.http.models.ContactModel;
import com.mangedha.knit.http.models.DownloadZipModel;
import com.mangedha.knit.http.models.FavoriteCallModel;
import com.mangedha.knit.http.models.MemmberShipModel;
import com.mangedha.knit.http.models.NotificationModel;
import com.mangedha.knit.http.models.PaymentHashModel;
import com.mangedha.knit.http.models.ProductPaymentModel;
import com.mangedha.knit.http.models.ProductsModel;
import com.mangedha.knit.http.models.SettingModel;
import com.mangedha.knit.http.models.UserLoginModel;
import com.mangedha.knit.http.models.http_interface.ChangePasswordModel;
import com.mangedha.knit.http.models.http_request.ResetMyPassword;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by bhavan on 12/26/16.
 */

public interface MangedhaWebService {

    @FormUrlEncoded
    @POST("user/login")
    Call<UserLoginModel> userLogin(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("user/request-reset-password")
    Call<ResetMyPassword> resetMyPassword(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("user/reset-password")
    Call<ChangePasswordModel> changePassword(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("user/register")
    Call<UserLoginModel> register(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("user/social-login")
    Call<UserLoginModel> socialLogin(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("user/update")
    Call<UserLoginModel> update(@FieldMap Map<String, String> maps);

    @GET("categories/list")
    Call<CategoriesModel> categoryList();

    @GET("settings/get")
    Call<SettingModel> setting();


    @FormUrlEncoded
    @POST("product/list")
    Call<ProductsModel> products(@FieldMap Map<String, String> map, @QueryMap Map<String, Integer> integerMap);

    @FormUrlEncoded
    @POST("product/favorite-add")
    Call<FavoriteCallModel> favoriteAdd(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("product/favorite-remove")
    Call<FavoriteCallModel> favoriteRemove(@FieldMap Map<String, String> map);

    @GET("notification/list")
    Call<NotificationModel> getNotificationList();

    @FormUrlEncoded
    @POST("notification/read")
    Call<NotificationModel> notificationUnRead(@FieldMap Map<String, Integer> stringStringMap);

    @GET("product/notification-count")
    Call<NotificationModel> notificationUnReadCount();

    @GET("setting/about-us")
    Call<AboutUsModel> aboutUs();

    @GET("member-ship/list")
    Call<MemmberShipModel> getMembership();

    @FormUrlEncoded
    @POST("payumoney/hash")
    Call<PaymentHashModel> getPaymentHash(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("payumoney/success")
    Call<SettingModel> paymentSuccess(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("payumoney/product-success")
    Call<ProductPaymentModel> paymentProductSuccess(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("contact/email")
    Call<ContactModel> enquirySubmit(@FieldMap Map<String, String> map);

    @GET("product/read")
    Call<NotificationModel> unRead(@QueryMap Map<String, Integer> integerMap);

    @GET("product/zip-product")
    Call<DownloadZipModel> downloadZip(@QueryMap Map<String, Integer> integerMap);


}