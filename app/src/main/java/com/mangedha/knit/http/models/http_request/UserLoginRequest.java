package com.mangedha.knit.http.models.http_request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 4/25/2017.
 */

public class UserLoginRequest {

    public static Map<String, String> makeLoginRequest(String email, String password){
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("LoginForm[email]", email);
        stringStringHashMap.put("LoginForm[password]", password);
        return stringStringHashMap;
    }

}
