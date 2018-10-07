package com.androvaid.guitorio.to_letbd.activity;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androvaid.guitorio.to_letbd.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class TestActivity extends AppCompatActivity {

    private final Context mContext = this;
    private static final String TAG = "TestActivity";
    ProgressDialog progressDialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

    }

}



/*
 * ================================================== Monon Food App =============================================
 * */

    /*public void CheckAuthentication(View view) {

        Log.d(TAG, "onResponse: client: " + client + " uid: " + uid + " token: " + access_token);

        progressDialog.show();

        Map<String, String> headers = new HashMap<>();
        headers.put("client", client);
        headers.put("uid", uid);
        headers.put("access-token", access_token);

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://khulna-food.herokuapp.com/api/v1/users/check_user")
                .get()
                .addHeader("client", client)
                .addHeader("uid", uid)
                .addHeader("access-token", access_token)
                .build();

        okhttp3.Call response = okHttpClient.newCall(request);
        response.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: Response: " + response.body().string());
            }
        });
    }*/



/*
 * ---------------- RequestBody working food ----------------------
 * */

                /*MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("email", "abc@gmail.com");
                builder.addFormDataPart("first_name", "abc");
                final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");
                builder.addFormDataPart("avatar", file.getName(), RequestBody.create(MEDIA_TYPE, file));

                RequestBody requestBody = builder.build();

                Call<RegAuthResponse> call = RetrofitClient.getInstance().getApi().getRegAuthRequestBodyResponse(requestBody);
                call.enqueue(new Callback<RegAuthResponse>() {
                    @Override
                    public void onResponse(Call<RegAuthResponse> call, Response<RegAuthResponse> response) {
                        progressDialog.dismiss();
                        Log.i(TAG, "Response: " + response.code());

                        if (response.code() == 200) {

                            try {
                                Log.d(TAG, "onResponse: json: " + response.body().getStatus());

                                Log.d(TAG, "onResponse: client: "+response.headers().get("Client")+" uid: "+response.headers().get("Uid")+" token: "+response.headers().get("Access-Token"));

                                client = response.headers().get("Client");
                                uid = response.headers().get("Uid");
                                access_token = response.headers().get("Access-Token");


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Log.d(TAG, "onResponse: Headers: uid: "+response.headers().get("Uid")+" Client: "+response.headers().get("Client")+" Expiry: "+response.headers().get("Expiry"));
                        }else {
                            Toast.makeText(TestActivity.this, "Data Problem", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegAuthResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });*/


/*
 * ---------------- RequestBody working  food ----------------------
 * */


/*
 * ------------- Ion Method working food -------------------
 * */

                /*
                MediaType mediaType = MediaType.parse("multipart/form-data");
                RequestBody body = RequestBody.create(mediaType, file);

                        Ion.with(TestActivity.this)
                        .load("POST",IMAGE_UPLOAD_URL)
                        .uploadProgressHandler(new ProgressCallback() {
                            @Override
                            public void onProgress(long uploaded, long total) {
                            }
                        })
                        .setMultipartParameter("email", "asd@gmail.com")
                        .setMultipartParameter("first_name", "asdfasdf")
                        .setMultipartFile("avatar", "image/jpeg", file)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                progressDialog.dismiss();
                                if (result != null) {
                                    //Upload Success
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onCompleted: REsult: "+result);

                                } else {
                                    //Upload Failed
                                    Toast.makeText(mContext, "Not Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/

/*
 * ------------- Ion Method working food-------------------
 * */