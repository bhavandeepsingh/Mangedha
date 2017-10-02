package com.mangedha.knit.http.models.http_interface;

import com.mangedha.knit.http.models.NotificationModel;

/**
 * Created by admin on 4/29/2017.
 */

public interface NotificationInterface {

    void onNotificationSuccess(NotificationModel notificationModel);

    void onNotificationFailur(Throwable t);

    void onNotificationError(NotificationModel notificationModel);

}
