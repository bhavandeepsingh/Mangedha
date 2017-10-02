package com.mangedha.knit.backround_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import com.mangedha.knit.helpers.BadgeUtils;
import com.mangedha.knit.helpers.UserHelper;
import com.mangedha.knit.http.models.NotificationModel;

/**
 * Created by bhavan on 5/7/17.
 */

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep((1000*60*10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(UserHelper.getUserLoginStatus()) NotificationModel.getUnReadCount(NotificationService.this);
                }
            }
        }).start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void updateBadge(int i) {
        if(i > getLastCount()) alertVibrate();
        if(i > 0) BadgeUtils.setBadge(getApplicationContext(), i);
        else BadgeUtils.clearBadge(getApplicationContext());
        setLastCount(i);
    }

    private void alertVibrate() {
        long pattern[] = { 0, 100, 200, 300, 400 };
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    vibrator.cancel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getLastCount(){
        return UserHelper.getSharedPreferences().getInt(LAST_NOTIFICATION_COUNT, 0);
    }

    public void setLastCount(int count){
        UserHelper.getSharedPreferences().edit().putInt(LAST_NOTIFICATION_COUNT, count).commit();
    }

    public String LAST_NOTIFICATION_COUNT = "LAST_NOTIFICATION_COUNT";

    public static void updateCount(int cnt){
        NotificationService notificationService = new NotificationService();
        notificationService.setLastCount(notificationService.getLastCount() - cnt);
    }

}
