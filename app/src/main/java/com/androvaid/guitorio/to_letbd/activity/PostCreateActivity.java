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
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kosalgeek.android.imagebase64encoder.ImageBase64;
import com.scrat.app.selectorlibrary.ImageSelector;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import droidninja.filepicker.FilePickerConst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCreateActivity extends AppCompatActivity {

    private static final String TAG = "PostCreateActivity";

    private static final int REQUEST_CODE_SELECT_IMG = 1;

    private List<String> yourSelectImgPaths = new ArrayList<>();
    private List<String> imagesBase64 = new ArrayList<>();
    private ArrayList<String> photoPaths = new ArrayList<String>();


    private ArrayList<String> mResults = new ArrayList<>();
    private static final int REQUEST_CODE = 123;


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

        //ImageSelector.show(this, REQUEST_CODE_SELECT_IMG);

        /*// start multiple photos selector
        Intent intent = new Intent(PostCreateActivity.this, ImagesSelectorActivity.class);
        // max number of images to be selected
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 15);
        // min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 10000);
        // show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, false);
        // pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        // start the selector
        startActivityForResult(intent, REQUEST_CODE);*/

        droidninja.filepicker.FilePickerBuilder.getInstance().setMaxCount(5)
               .setSelectedFiles(photoPaths)
               .setActivityTheme(R.style.LibAppTheme)
               .pickPhoto(this);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        Fresco.initialize(PostCreateActivity.this);

        Log.d(TAG, "onCreate: inside");

        initView();

        progressDialog.show();

        // Categories
        getCategories();
        // Features
        getFeatures();

    }

    private void getCategories() {

        Log.d(TAG, "getCategories: inside");

        Call<CategoriesResponse> call = RetrofitClient.getInstance().getApi().getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

                categories = response.body().getCategories();

                for (Categories category : categories) {

                    Log.d(TAG, "onResponse: Categories getName: "+category.getName());

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

        Log.d(TAG, "getFeatures: inside");

        Call<FeaturesResponse> call = RetrofitClient.getInstance().getApi().getFeatures();
        call.enqueue(new Callback<FeaturesResponse>() {
            @Override
            public void onResponse(Call<FeaturesResponse> call, Response<FeaturesResponse> response) {

                progressDialog.dismiss();

                features = response.body().getFeatures();

                for (Features feature : features) {

                    Log.d(TAG, "onResponse: Features getTitle: "+feature.getTitle());

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

        // viewPager
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

            /*
            * ----------- base64 ------------
            * */
            for (String yourSelectImgPath : yourSelectImgPaths) {
                try {
                    String encodedImage = ImageBase64
                            .with(getApplicationContext())
                            .noScale()
                            .encodeFile(yourSelectImgPath);

                    imagesBase64.add(encodedImage);

                    Log.d(TAG, "onActivityResult: Image Path: "+yourSelectImgPath+" Encoded To Base64: "+encodedImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            /*
             * ----------- base64 ------------
             * */

            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, yourSelectImgPaths);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "Path: "+yourSelectImgPaths.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            return;
        }else if(requestCode == REQUEST_CODE){

            mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;

            // show results in textview
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
            for (String result : mResults) {
                sb.append(result).append("\n");
            }

            Log.d(TAG, "onActivityResult: " + mResults);

            /*Glide.with(SignupActivity.this)
                    .load(mResults.get(0))
                    .into(profileImage);*/
            //tvResults.setText(sb.toString());


            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, mResults);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "Path: "+mResults.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }else if(FilePickerConst.REQUEST_CODE_PHOTO==233){

            //photoPaths = new ArrayList<>();

            if(photoPaths == null){
                photoPaths = new ArrayList<String>();
            }

            photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));

            ViewPagerLocalAdapter adapter = new ViewPagerLocalAdapter(PostCreateActivity.this, photoPaths);
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(PostCreateActivity.this, "FilePickerConst: Path: "+photoPaths.get(position), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnUpload(View view) {

        Log.d(TAG, "btnUpload: Categori Ids: "+multiSelectSpinnerCategories.getSelectedIdsAsString());

        String[] array = multiSelectSpinnerCategories.getSelectedIdsAsArray().toArray(new String[multiSelectSpinnerCategories.getSelectedIdsAsArray().size()]);
        Log.d(TAG, "btnUpload: Categori Ids Array: "+ Arrays.toString(array));

        Log.d(TAG, "btnUpload: Categori Ids List:"+multiSelectSpinnerCategories.getSelectedIdsAsArray());

        Log.d(TAG, "btnUpload: Features Ids: "+multiSelectSpinnerFeatures.getSelectedIdsAsString());
        Log.d(TAG, "btnUpload: Features Ids: ArrayList: "+multiSelectSpinnerFeatures.getSelectedIdsAsArray());

        Log.d(TAG,"btnUpload: Images array: "+photoPaths);

    }
}
