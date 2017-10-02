package com.mangedha.knit.http.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 4/25/2017.
 */

public class MangedhaModel {

    @SerializedName("is_error")
    @Expose
    public boolean is_error;


    @SerializedName("is_success")
    @Expose
    public boolean is_success;

    public boolean is_success() {
        return is_success;
    }

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("pagination")
    @Expose
    Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public boolean is_error() {
        return is_error;
    }

    public void setIs_error(boolean is_error) {
        this.is_error = is_error;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public class Pagination{

        @SerializedName("total")
        @Expose
        int total;

        @SerializedName("load_more")
        @Expose
        boolean load_more;

        @SerializedName("page_no")
        @Expose
        int page_no;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isLoad_more() {
            return load_more;
        }

        public void setLoad_more(boolean load_more) {
            this.load_more = load_more;
        }

        public int getPage_no() {
            return page_no;
        }

        public void setPage_no(int page_no) {
            this.page_no = page_no;
        }
    }
}
