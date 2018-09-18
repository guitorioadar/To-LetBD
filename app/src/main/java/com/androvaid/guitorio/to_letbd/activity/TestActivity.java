package com.androvaid.guitorio.to_letbd.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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
import com.androvaid.guitorio.to_letbd.adapter.ViewPagerLocalAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.meta.Meta;
import com.androvaid.guitorio.to_letbd.model.features.Features;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;
import com.androvaid.guitorio.to_letbd.widget.GetDummyValues;
import com.androvaid.guitorio.to_letbd.widget.MultiSelectSpinner;
import com.bumptech.glide.Glide;
import com.scrat.app.selectorlibrary.ImageSelector;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    ProgressDialog progressDialog;

    String token = null;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        Button buttonPress = findViewById(R.id.buttonPress);

        buttonPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<SignInResponse> call = RetrofitClient.getInstance().getApi().getSignInResponse("mdshahin0002@gmail.com", "123456");

                call.enqueue(new Callback<SignInResponse>() {
                    @Override
                    public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                        try {

                            if (response.body().getMeta().toString() != null) {

                                Integer meta = response.body().getMeta().getStatus();
                                String message = response.body().getResponse().getMessage();

                                if (meta.equals(200)) {
                                    token = response.body().getResponse().getToken();
                                    startActivity(new Intent(TestActivity.this,PropertyList.class));
                                }


                                Log.d(TAG, "onResponse: Meta: " + meta + " message: " + message+" token: "+token);

                            } else {
                                Toast.makeText(TestActivity.this, "Getting no Data", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            Toast.makeText(TestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Exception: " + e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<SignInResponse> call, Throwable t) {

                    }
                });

            }
        });


    }

}