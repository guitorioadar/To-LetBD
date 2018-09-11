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
import com.androvaid.guitorio.to_letbd.model.features.Features;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
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

    String[] strings = {"Red", "Blue", "Green"};

    MultiSelectSpinner mySpin;

    private static final String DEFAULT_TEXT = "Select - Value";
    private GetDummyValues getDummyValues;
    private Button button;
    private MultiSelectSpinner myMultispinner;
    private String titleText = "Your Title Here";

    private List<Features> features = new ArrayList<>();
    private List<String> featureIds = new ArrayList<>();
    private List<String> featureTitles = new ArrayList<>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



        init();

        progressDialog.show();
        progressDialog.setCancelable(false);


        /*for (int i = 0; i < 20; i++) {
            featureIds.add(String.valueOf(i));
            featureTitles.add("Value "+i);
        }*/

        /*
         * --------------------------- Features Spinner ---------------------------
         * */

        Call<FeaturesResponse> call = RetrofitClient.getInstance().getApi().getFeatures();

        call.enqueue(new Callback<FeaturesResponse>() {
            @Override
            public void onResponse(Call<FeaturesResponse> call, Response<FeaturesResponse> response) {

                progressDialog.dismiss();

                Log.d(TAG, "onResponse: "+response);

                features = response.body().getFeatures();

                for (Features feature : features) {
                    featureIds.add(feature.getId().toString());
                    featureTitles.add(feature.getTitle());
                }

                for (String featureTitle : featureTitles) {
                    Log.d(TAG, "onResponse: Feat Title: "+featureTitle);
                }

                for (String featureId : featureIds) {
                    Log.d(TAG, "onResponse: Feat ID: "+featureId);
                }

                myMultispinner.setItems(featureTitles, featureIds, DEFAULT_TEXT,titleText);

                /*featureTitles = new String[features.size()];
                featureIds = new String[features.size()];

                for (int i = 0; i < features.size(); i++) {
                    featureTitles[i] = String.valueOf(features.get(i).getTitle());
                    Log.d(TAG, "onResponse: featureTitles: "+ featureTitles[i]);
                }

                for (int i = 0; i<features.size(); i++){
                    featureIds[i] = String.valueOf(features.get(i).getId());
                    Log.d(TAG, "onResponse: featureIds: "+ featureIds[i]);
                }*/

                /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(PropertyList.this, android.R.layout.simple_spinner_item, categoryNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnCategory.setAdapter(adapter);

                spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {
                            //Toast.makeText(PropertyList.this, parent.getItemAtPosition(position).toString() + " ID: " + categories.get(position).getId(), Toast.LENGTH_SHORT).show();

                            _category = parent.getItemAtPosition(position).toString();
                            _categoryId = categories.get(position).getId().toString();

                            Log.d(TAG, "onItemSelected: spnCategory: "+_category + " ID: " + _categoryId);

                        } catch (Exception e) {
                            Log.d(TAG, "onItemSelected: spnCategory" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

            }

            @Override
            public void onFailure(Call<FeaturesResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

        /*
         * --------------------------- Category Spinner Ends ---------------------------
         * */




    }

    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        myMultispinner = (MultiSelectSpinner) findViewById(R.id.view);
        button = (Button) findViewById(R.id.buttonPress);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.textViewIds)).setText(myMultispinner.getSelectedIdsAsString());
                ((TextView) findViewById(R.id.textViewNames)).setText(myMultispinner.getSelectedItemsAsString());
            }
        });

        getDummyValues = new GetDummyValues();
        //myMultispinner.setItems(getDummyValues.getValues(), getDummyValues.getIds(), DEFAULT_TEXT,titleText);



    }
}