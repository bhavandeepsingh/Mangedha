package com.mangedha.knit.http.models;

import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mangedha.knit.http.RestAdapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavan on 11/5/17.
 */

public class DownloadZipModel extends MangedhaModel {

    @SerializedName("path")
    @Expose
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static void downloadFile(int product_id, final DownloadZipInterface downloadZipInterface){

        RestAdapter.get().downloadZip(makeCall(product_id)).enqueue(new Callback<DownloadZipModel>() {
            @Override
            public void onResponse(Call<DownloadZipModel> call, Response<DownloadZipModel> response) {
                downloadZipInterface.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DownloadZipModel> call, Throwable t) {
                downloadZipInterface.onError(t.getMessage());
            }
        });
    }

    private static Map<String,Integer> makeCall(int product_id) {
        Map<String, Integer> stringIntegerMap = new ArrayMap<>();
        stringIntegerMap.put("id", product_id);
        return stringIntegerMap;
    }

    public interface DownloadZipInterface {
        void onSuccess(DownloadZipModel downloadZipModel);
        void onError(String error);
    }
}
