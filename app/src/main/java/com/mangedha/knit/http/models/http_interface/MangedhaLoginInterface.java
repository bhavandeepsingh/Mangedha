package com.mangedha.knit.http.models.http_interface;

import com.mangedha.knit.http.models.UserLoginModel;

/**
 * Created by admin on 4/25/2017.
 */

public interface MangedhaLoginInterface {

    void onLoginSuccess(UserLoginModel userLoginModel);

    void onLoginError(Throwable t);

    void onLoginFailure(UserLoginModel userLoginModel);

}
