package com.mangedha.knit.http.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.backround_service.NotificationService;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.RestAdapter;
import com.mangedha.knit.http.models.http_interface.NotificationInterface;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 4/29/2017.
 */

public class NotificationModel extends MangedhaModel{

    public static int NOTIFAICATION_HAS_ATTACHMENT = 1;

    @SerializedName("un_read_count")
    @Expose
    int un_read_count;

    @SerializedName("case_law_count")
    @Expose
    int case_law_count;

    @SerializedName("list")
    @Expose
    List<Notifications> notificationses;

    public int getUn_read_count() {
        return un_read_count;
    }

    public void setUn_read_count(int un_read_count) {
        this.un_read_count = un_read_count;
    }

    public List<Notifications> getNotificationses() {
        return notificationses;
    }

    public void setNotificationses(List<Notifications> notificationses) {
        this.notificationses = notificationses;
    }

    public int getCase_law_count() {
        return case_law_count;
    }

    public void setCase_law_count(int case_law_count) {
        this.case_law_count = case_law_count;
    }

    public static void getNotificationList(final NotificationInterface notificantionInterface) {
        RestAdapter.get().getNotificationList().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                notificantionInterface.onNotificationSuccess(response.body());

            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                notificantionInterface.onNotificationFailur(t);
            }
        });
    }

    public static void getUnReadCount(final NotificationService notificationService) {
        RestAdapter.get().notificationUnReadCount().enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if(response.body() != null) {
                    notificationService.updateBadge(response.body().getUn_read_count());
                    setLastCaseLawCount(response.body().getCase_law_count());
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {

            }
        });
    }

    public static int getCaselawCount(){
        return UserHelper.getSharedPreferences().getInt(LAST_CASE_LAW_COUNT, 0);
    }

    public static void setLastCaseLawCount(int count){
        UserHelper.getSharedPreferences().edit().putInt(LAST_CASE_LAW_COUNT, count).commit();
    }

    static String LAST_CASE_LAW_COUNT = "LAST_CASE_LAW_COUNT";

    public class Notifications {
        @SerializedName("id")
        @Expose
        int id;

        @SerializedName("title")
        @Expose
        String title;

        @SerializedName("description")
        @Expose
        String description;

        @SerializedName("sender_id")
        @Expose
        int sender_id;

        @SerializedName("reciever_id")
        @Expose
        int reciever_id;

        @SerializedName("is_file")
        @Expose
        int is_file;

        @SerializedName("created_at")
        @Expose
        String created_at;

        @SerializedName("updated_at")
        @Expose
        String updated_at;

        @SerializedName("notification_src")
        @Expose
        String notification_src;

        @SerializedName("isRead")
        @Expose
        int isRead;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSender_id() {
            return sender_id;
        }

        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }

        public int getReciever_id() {
            return reciever_id;
        }

        public void setReciever_id(int reciever_id) {
            this.reciever_id = reciever_id;
        }

        public int getIs_file() {
            return is_file;
        }

        public void setIs_file(int is_file) {
            this.is_file = is_file;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getNotification_src() {
            return notification_src;
        }

        public void setNotification_src(String notification_src) {
            this.notification_src = notification_src;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }


    public static void unReadNotification(Map<String, Integer> stringIntegerMap){
        RestAdapter.get().notificationUnRead(stringIntegerMap).enqueue(new Callback<NotificationModel>() {
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

}
