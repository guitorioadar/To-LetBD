package com.androvaid.guitorio.to_letbd.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.ViewPagerLocalAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.model.categories.Categories;
import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.Features;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.widget.MultiSelectSpinner;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCreateActivity extends AppCompatActivity {

    private static final String TAG = "PostCreateActivity";

    private static final int REQUEST_CODE_SELECT_IMG = 1;

    private List<String> yourSelectImgPaths = new ArrayList<>();

    /*@BindView(R.id.view_pager_post_create)
    ViewPager view_pager_post_create;*/

    private ViewPager viewPager;

    // Spn

    private MultiSelectSpinner multiSelectSpinnerCategories,multiSelectSpinnerFeatures;

    ProgressDialog progressDialog;

    // Features
    private List<Features> features = new ArrayList<>();
    private List<String> featureIds = new ArrayList<>();
    private List<String> featureTitles = new ArrayList<>();

    // Categories
    private List<Categories> categories = new ArrayList<>();
    private List<String> categoryIds = new ArrayList<>();
    private List<String> categoryNames = new ArrayList<>();



    //@BindView(R.id.btnSelectImage) Button button;

    public void favAddImage(View view) {

        //Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        initView();

        progressDialog.show();

        // Categories
        getCategories();
        // Features
        getFeatures();

    }

    private void getCategories() {

        Call<CategoriesResponse> call = RetrofitClient.getInstance().getApi().getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

                categories = response.body().getCategories();

                for (Categories category : categories) {
                    categoryIds.add(category.getId().toString());
                    categoryNames.add(category.getName());
                }

                multiSelectSpinnerCategories.setItems(categoryNames,categoryIds,"Select-Categories","Select Multiple Categories");
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });

    }
    private void getFeatures() {

        Call<FeaturesResponse> call = RetrofitClient.getInstance().getApi().getFeatures();
        call.enqueue(new Callback<FeaturesResponse>() {
            @Override
            public void onResponse(Call<FeaturesResponse> call, Response<FeaturesResponse> response) {

                progressDialog.dismiss();

                features = response.body().getFeatures();

                for (Features feature : features) {
                    featureIds.add(feature.getId().toString());
                    featureTitles.add(feature.getTitle());
                }

                multiSelectSpinnerFeatures.setItems(featureTitles, featureIds, "Select Features","Select Multiple Features");

            }

            @Override
            public void onFailure(Call<FeaturesResponse> call, Throwable t) {

            }
        });

    }



    private void initView() {

        // Progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        //viewPager
        viewPager = findViewById(R.id.view_pager_post_create);
        // spn
        multiSelectSpinnerCategories = findViewById(R.id.multiSelectSpinnerCategories);
        multiSelectSpinnerFeatures = findViewById(R.id.multiSelectSpinnerFeatures);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            yourSelectImgPaths = ImageSelector.getImagePaths(data);
            Log.d("imgSelector", "paths: " + yourSelectImgPaths);

            //textView.setText(yourSelectImgPaths.toString());

            //Glide.with(TestActivity.this).load(yourSelectImgPaths.get(0)).into(imageViewTest);

            // Images

            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, yourSelectImgPaths);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "ID: "+yourSelectImgPaths.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnUpload(View view) {

        Log.d(TAG, "btnUpload: Categori Ids: "+multiSelectSpinnerCategories.getSelectedIdsAsString());

        String[] array = multiSelectSpinnerCategories.getSelectedIdsAsArray().toArray(new String[multiSelectSpinnerCategories.getSelectedIdsAsArray().size()]);
        Log.d(TAG, "btnUpload: Categori Ids Array: "+ Arrays.toString(array));

        Log.d(TAG, "btnUpload: Categori Ids List:"+multiSelectSpinnerCategories.getSelectedIdsAsArray());

        Log.d(TAG, "btnUpload: Features Ids: "+multiSelectSpinnerFeatures.getSelectedIdsAsString());

    }
}
