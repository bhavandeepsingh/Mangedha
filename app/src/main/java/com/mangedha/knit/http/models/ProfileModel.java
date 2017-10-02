package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 4/25/2017.
 */
public class ProfileModel extends MangedhaModel{

    @SerializedName("id")
    @Expose
    int user_id;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("mobile")
    @Expose
    String mobile;

    @SerializedName("auth_key")
    @Expose
    String auth_key;

    @SerializedName("confirmed_at")
    @Expose
    String confirmed_at;

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("updated_at")
    @Expose
    String updated_at;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("city")
    @Expose
    String city;

    @SerializedName("state")
    @Expose
    String state;

    @SerializedName("zip_code")
    @Expose
    String zip_code;

    public static ProfileModel getLoginUserProfile() {
        return new ProfileModel();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public String getConfirmed_at() {
        return confirmed_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public void setConfirmed_at(String confirmed_at) {
        this.confirmed_at = confirmed_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
