package com.androvaid.guitorio.to_letbd.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.PostsCategoryAdapter;
import com.androvaid.guitorio.to_letbd.adapter.PostsFeaturesAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.interfaces.InterfaceDataPass;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsCategory;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetail;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsFeature;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends AppCompatActivity {

    private static final String TAG = "PropertyDetailActivity";

    // Categories
    @BindView(R.id.recyclerViewPostCategories)
    RecyclerView recyclerViewPostCategories;
    private PostsCategoryAdapter mPostsCategoryAdapter;

    // Features
    @BindView(R.id.recyclerViewFeatures)
    RecyclerView recyclerViewFeatures;
    private PostsFeaturesAdapter mPostsFeaturesAdapter;

    List<PostsCategory> postsCategories = new ArrayList<>();
    List<PostsImage> postsImages = new ArrayList<>();
    List<PostsFeature> postsFeatures = new ArrayList<>();

    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail_main);
        ButterKnife.bind(this);

        /*
         * ------------------- Initialize ------------------
         * */

        // Categories
        recyclerViewPostCategories.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPostCategories.setItemAnimator(new DefaultItemAnimator());

        /*
         * ------------------- Initialize ------------------
         * */

        /*
        * ---------------- Intent -------------------
        * */

        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("producatId");
            Toast.makeText(this, "id: "+productId, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        /*
         * ---------------- Intent -------------------
         * */

        /*
         * ========== ToolBar ===============
         * */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPropertyDetail_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        /*
         * ================ ToolBar Ends ==================
         * */

        /*
         * ==============  Action Bar Button =================
         * */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
         * ==============  Action Bar Button Ends =================
         * */

        /*
        * ---------------------------- Server Data ----------------------------------
        * */

        Call<PostsDetailResponse> call = RetrofitClient.getInstance().getApi().getPostDetailResponse(productId);

        call.enqueue(new Callback<PostsDetailResponse>() {
            @Override
            public void onResponse(Call<PostsDetailResponse> call, Response<PostsDetailResponse> response) {

                postsCategories = response.body().getPostsDetail().getCategories();
                postsFeatures = response.body().getPostsDetail().getPostsFeatures();

                for (PostsFeature postsFeature : postsFeatures) { Log.d(TAG, "onResponse: "+postsFeature.getTitle()); }

                // Categories
                setPostCategoriesRec(postsCategories);

                // Features
                setPostFeaturesRec(postsFeatures);

            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

        /*
         * ---------------------------- Server Data ----------------------------------
         * */

    }

    private void setPostFeaturesRec(List<PostsFeature> postsFeatures) {
        mPostsFeaturesAdapter = new PostsFeaturesAdapter(this,postsFeatures);
        recyclerViewFeatures.setAdapter(mPostsFeaturesAdapter);
    }

    private void setPostCategoriesRec(List<PostsCategory> postsCategories) {
        mPostsCategoryAdapter = new PostsCategoryAdapter(this,postsCategories);
        recyclerViewPostCategories.setAdapter(mPostsCategoryAdapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
