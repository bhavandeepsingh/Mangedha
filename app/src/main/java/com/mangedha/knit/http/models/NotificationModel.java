package com.mangedha.knit.http.models;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.backround_service.NotificationService;
import com.mangedha.knit.http.RestAdapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 4/29/2017.
 */

public class NotificationModel extends MangedhaModel{

    @SerializedName("count")
    @Expose
    int count;

    @SerializedName("data")
    @Expose
    NotificationStatus notificationStatus;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public static void getUnReadCount(final NotificationService notificationService) {
        RestAdapter.get().notificationUnReadCount().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if(response.body() != null) {
                    notificationService.updateBadge(response.body().getCount());
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

            }
        });
    }


    public static void unReadNotification(int product_id){
        RestAdapter.get().unRead(makeCall(product_id)).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                Log.d("ASD", response.body().toString());
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Log.d("ASD", t.getMessage());
            }
        });
    }

    private static Map<String, Integer> makeCall(int product_id) {
        Map<String, Integer> stringIntegerMap = new ArrayMap<>();
        stringIntegerMap.put("product_id", product_id);
        return stringIntegerMap;
    }

}
