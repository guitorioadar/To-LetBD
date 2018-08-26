package com.androvaid.guitorio.to_letbd.activity;

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

public class PropertyDetailActivity extends AppCompatActivity implements InterfaceDataPass {

    private static final String TAG = "PropertyDetailActivity";

    @BindView(R.id.recyclerViewPostCategories)
    RecyclerView recyclerViewPostCategories;

    //RecyclerView recyclerViewPostCategories;

    //private RecyclerView recyclerViewCategories;
    //private RecyclerView recyclerViewFeatures;

    private RecyclerView recyclerView;
    private PostsCategoryAdapter mPostsCategoryAdapter;

    List<PostsCategory> postsCategories = new ArrayList<>();
    List<PostsImage> postsImages;
    List<PostsFeature> postsFeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail_main);
        ButterKnife.bind(this);

        /*
         * ------------------- Initialize ------------------
         * */


        if (recyclerViewPostCategories == null)
            recyclerViewPostCategories = findViewById(R.id.recyclerViewPostCategories);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPostCategories.setLayoutManager(mLayoutManager);
        recyclerViewPostCategories.setItemAnimator(new DefaultItemAnimator());

        /*recyclerViewPostCategories.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));
        recyclerViewPostCategories.setAdapter(new PostsCategoryAdapter(PropertyDetailActivity.this, postsCategories));*/

        /*recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));*/

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
                    Log.d(TAG, "onResponse: Detail: " + response.body().getPostsDetail());
                    Log.d(TAG, "onResponse: id: " + response.body().getPostsDetail().getId());
                    Log.d(TAG, "onResponse: Title: " + response.body().getPostsDetail().getTitle());

                    postsCategories = response.body().getPostsDetail().getCategories();
                    postsImages = response.body().getPostsDetail().getPostsImages();
                    postsFeatures = response.body().getPostsDetail().getPostsFeatures();




                    //mPostsCategoryAdapter.notifyDataSetChanged();

                    //posts.toArray(new Posts[posts.size()])

                    setPostCategories();
                    //setPostFeatures();

                    //PostsCategory[] arrPostsCategories = postsCategories.toArray(new PostsCategory[postsCategories.size()]);

                    /*if (recyclerViewPostCategories == null) {
                        recyclerViewPostCategories = findViewById(R.id.recyclerViewPostCategories);
                    }

                    try {
                        //recyclerViewCategories.setAdapter(new PostsCategoryAdapter(PropertyDetailActivity.this, postsCategories.toArray(new PostsCategory[postsCategories.size()])));

                        *//*recyclerViewPostCategories.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));
                        recyclerViewPostCategories.setAdapter(new PostsCategoryAdapter(PropertyDetailActivity.this, postsCategories));*//*


                        Log.d(TAG, "setPostCategories: ");

                    } catch (Exception e) {
                        Log.d(TAG, "setPostCategories: Error: " + e.getMessage());
                    }*/


                } catch (Exception e) {
                    Log.d(TAG, "onResponse: CallBack error: " + e.getMessage()+" \n\nCause: "+e.getCause()+" \n\nLocalizedMessage: "+e.getLocalizedMessage()+" \n\nStackTrace: "+ Arrays.toString(e.getStackTrace()));
                }


            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: Call: " + call + " Throwable: " + t.getMessage());

            }
        });

    }

    private void setPostFeatures() {

        //recyclerViewFeatures.setAdapter(new PostsFeaturesAdapter(PropertyDetailActivity.this,postsFeatures.toArray(new PostsFeature[postsFeatures.size()])));

    }

    private void setPostCategories() {

        Log.d(TAG, "setPostCategories: Into: " + postsCategories.toArray(new PostsCategory[postsCategories.size()]));

        try {
            mPostsCategoryAdapter = new PostsCategoryAdapter(PropertyDetailActivity.this,postsCategories);
            recyclerViewPostCategories.setAdapter(mPostsCategoryAdapter);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        /*PostsCategory[] arrPostsCategories = postsCategories.toArray(new PostsCategory[postsCategories.size()]);

        if (recyclerViewPostCategories == null) {
            recyclerViewPostCategories = findViewById(R.id.recyclerViewPostCategories);
        }

        try {
            //recyclerViewCategories.setAdapter(new PostsCategoryAdapter(PropertyDetailActivity.this, postsCategories.toArray(new PostsCategory[postsCategories.size()])));


            recyclerViewPostCategories.setLayoutManager(new LinearLayoutManager(PropertyDetailActivity.this));
            recyclerViewPostCategories.setAdapter(new PostsCategoryAdapter(this, arrPostsCategories));


            Log.d(TAG, "setPostCategories: ");

        } catch (Exception e) {
            Log.d(TAG, "setPostCategories: Error: " + e.getMessage());
        }*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
