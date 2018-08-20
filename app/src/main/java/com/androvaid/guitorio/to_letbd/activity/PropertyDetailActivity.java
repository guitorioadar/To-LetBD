package com.androvaid.guitorio.to_letbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyDetailActivity extends AppCompatActivity implements InterfaceDataPass {

    private static final String TAG = "PropertyDetailActivity";

    private RecyclerView recyclerViewCategories,recyclerViewFeatures;

    List<PostsCategory> postsCategories;
    List<PostsImage> postsImages;
    List<PostsFeature> postsFeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail_main);

        /*
        * ------------------- Initialize ------------------
        * */



        /*recyclerViewFeatures = findViewById(R.id.recyclerViewFeatures);
        recyclerViewFeatures.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));*/

        /*
         * ------------------- Initialize ------------------
         * */

        /*
         * ========== ToolBar Initializaation ===============
         * */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPropertyDetail_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setTitle("BDT "+"7000");
            //getSupportActionBar().setTitle(Html.fromHtml("<small>Property Price</small>"));
        }

        /*
         * ================ Ends ==================
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

    }

    @Override
    public void passPostId(Integer id) {
        Log.d(TAG, "passPostId: ID: " + id);

        Call<PostsDetailResponse> call = RetrofitClient.getInstance().getApi().getPostDetailResponse(id.toString());

        call.enqueue(new Callback<PostsDetailResponse>() {
            @Override
            public void onResponse(Call<PostsDetailResponse> call, Response<PostsDetailResponse> response) {

                try {
                    Log.d(TAG, "onResponse: " + response.body().getPostsDetail());
                    Log.d(TAG, "onResponse: " + response.body().getPostsDetail().getId());
                    Log.d(TAG, "onResponse: " + response.body().getPostsDetail().getTitle());

                    postsCategories = response.body().getPostsDetail().getCategories();
                    postsImages = response.body().getPostsDetail().getPostsImages();
                    postsFeatures = response.body().getPostsDetail().getPostsFeatures();

                    //posts.toArray(new Posts[posts.size()])

                    setPostCategories();
                    setPostFeatures();





                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                }


            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

    }

    private void setPostFeatures() {

        //recyclerViewFeatures.setAdapter(new PostsFeaturesAdapter(PropertyDetailActivity.this,postsFeatures.toArray(new PostsFeature[postsFeatures.size()])));

    }

    private void setPostCategories() {

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));

        recyclerViewCategories.setAdapter(new PostsCategoryAdapter(PropertyDetailActivity.this,postsCategories.toArray(new PostsCategory[postsCategories.size()])));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
