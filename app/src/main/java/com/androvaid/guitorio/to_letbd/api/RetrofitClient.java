package com.androvaid.guitorio.to_letbd.api;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";
    
    private static final String BASE_URL = "http://to-let.androvaid.com/api/v1/";
    //private static final String BASE_URL = "https://khulna-food.herokuapp.com/api/v1/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(100, TimeUnit.SECONDS);
        httpClient.readTimeout(100,TimeUnit.SECONDS);
        httpClient.writeTimeout(100,TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        Log.d(TAG, "getInstance: inside");
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        Log.d(TAG, "getApi: inside");
        return retrofit.create(Api.class);
    }
}