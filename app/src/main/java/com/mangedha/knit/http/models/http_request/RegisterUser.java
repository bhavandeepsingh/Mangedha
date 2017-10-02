package com.mangedha.knit.http.models.http_request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bhavan on 9/10/17.
 */

public class RegisterUser {

    public static Map<String, String> call(String email, String password, String name, String mobile, String city, String state, String zip_code){
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("User[email]", email);
        stringStringHashMap.put("User[password]", password);
        stringStringHashMap.put("Profile[name]", name);
        stringStringHashMap.put("Profile[city]", city);
        stringStringHashMap.put("Profile[state]", state);
        stringStringHashMap.put("Profile[zip_code]", zip_code);
        stringStringHashMap.put("Profile[mobile]", mobile);
        return stringStringHashMap;
    }

    public static Map<String, String> call(String email, String name, String mobile, String city, String state, String zip_code){
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("User[email]", email);
        stringStringHashMap.put("Profile[name]", name);
        stringStringHashMap.put("Profile[city]", city);
        stringStringHashMap.put("Profile[state]", state);
        stringStringHashMap.put("Profile[zip_code]", zip_code);
        stringStringHashMap.put("Profile[mobile]", mobile);
        return stringStringHashMap;
    }

}
