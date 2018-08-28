package com.androvaid.guitorio.to_letbd.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.PostsCategoryAdapter;
import com.androvaid.guitorio.to_letbd.adapter.PostsFeaturesAdapter;
import com.androvaid.guitorio.to_letbd.adapter.ViewPagerAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsCategory;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsFeature;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity{
    private static final String TAG = "TestActivity";

    private String productId = "";

    private RecyclerView recyclerView, recyclerViewFeaturesTest;

    private PostsCategoryAdapter mAdapter;
    private List<PostsCategory> postsCategories = new ArrayList<>();

    private PostsFeaturesAdapter mFeaturesAdapter;
    private List<PostsFeature> postsFeatures = new ArrayList<>();

    private List<PostsImage> postsImages = new ArrayList<>();
    HashMap<String, String> url_maps = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d(TAG, "onCreate: Sequence : productId: " + productId);

        // Categories
        recyclerView = (RecyclerView) findViewById(R.id.recyTestCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Features
        recyclerViewFeaturesTest = findViewById(R.id.recyclerViewFeaturesTest);
        recyclerViewFeaturesTest.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFeaturesTest.setItemAnimator(new DefaultItemAnimator());


        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("producatId");
            //Toast.makeText(this, "id: "+productId, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        if (!productId.isEmpty())
            getAllData(productId);

//        setCategoryRec(postsCategories);


    }

    private void getAllData(String id) {

        Call<PostsDetailResponse> call = RetrofitClient.getInstance().getApi().getPostDetailResponse(id);

        call.enqueue(new Callback<PostsDetailResponse>() {
            @Override
            public void onResponse(Call<PostsDetailResponse> call, Response<PostsDetailResponse> response) {

                postsCategories = response.body().getPostsDetail().getCategories();
                setCategoryRec(postsCategories);

                postsFeatures = response.body().getPostsDetail().getPostsFeatures();
                setFeaturesRec(postsFeatures);

                // Images
                postsImages = response.body().getPostsDetail().getPostsImages();

                for (PostsImage postsImage : postsImages) {
                    Log.d(TAG, "onResponse: Image Url Parse: " + parseImageUrl(postsImage.getImage()));
                    url_maps.put(postsImage.getId().toString(), parseImageUrl(postsImage.getImage()));
                }

                ViewPager viewPager = findViewById(R.id.view_pager_test);
                ViewPagerAdapter adapter = new ViewPagerAdapter(TestActivity.this, postsImages);
                viewPager.setAdapter(adapter);

                // Images


            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

    }

    private void setFeaturesRec(List<PostsFeature> postsFeatures) {
        mFeaturesAdapter = new PostsFeaturesAdapter(TestActivity.this, postsFeatures);
        recyclerViewFeaturesTest.setAdapter(mFeaturesAdapter);
    }

    private void setCategoryRec(List<PostsCategory> postsCategories) {
        mAdapter = new PostsCategoryAdapter(TestActivity.this, postsCategories);
        recyclerView.setAdapter(mAdapter);
    }

    private String parseImageUrl(String image) {
        return "http://to-let.androvaid.com/" + image.substring(7).replace('\\', '/');
    }

}
