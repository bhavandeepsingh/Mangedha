package com.mangedha.knit.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mangedha.knit.helpers.UserHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bhavan on 12/26/16.
 */

public class RestAdapter {

    private static MangedhaWebService REST_CLIENT = null;

    //private static String ROOT = "http://192.168.0.102/mangedha/api/web/v1/";
    private static String ROOT = "http://192.168.1.5/mangedha/api/web/v1/";
    //private static String ROOT = "http://uniquecoders.in/mangedha/api/web/v1/";

    static {
        setupRestClient();
    }

    private RestAdapter() {
    }

    public static MangedhaWebService get() {
        return REST_CLIENT;
    }

    public static void setupRestClient() {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        addHeaderToken(httpClient);
        httpClient.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        if (REST_CLIENT == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            REST_CLIENT = retrofit.create(MangedhaWebService.class);
        }
    }

    public static void addHeaderToken(OkHttpClient.Builder httpClient) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("mangedha-header-token", String.valueOf(UserHelper.getAppUserAuthKey()))
                        .method(original.method(), original.body());

                return chain.proceed(requestBuilder.build());
            }
        });
    }

}