package com.androvaid.guitorio.to_letbd.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";
    
    private static final String BASE_URL = "http://to-let.androvaid.com/api/v1/";
    //private static final String BASE_URL = "http://127.0.0.1:8000/api/v1/";
    //private static final String BASE_URL = "http://192.168.11.144:8088/to-let/api/v1/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {

        Log.d(TAG, "RetrofitClient: inside");
        
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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