package com.mangedha.knit.http.models.http_interface;

import com.mangedha.knit.http.models.UserLoginModel;

/**
 * Created by Akshit on 30/04/2017.
 */

public interface ProfileUpdateInterface {

    void onProfileSuccess(UserLoginModel userLoginModel);

    void onProfileError(Throwable t);

    void onProfileFailure(UserLoginModel userLoginModel);
}
