package com.androvaid.guitorio.to_letbd.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.PostsCategoryAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.interfaces.InterfaceDataPass;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsCategory;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    private String productId="";

    private RecyclerView recyclerView;
    private PostsCategoryAdapter mAdapter;
    private List<PostsCategory> postsCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Log.d(TAG, "onCreate: Sequence : productId: "+productId);


        recyclerView = (RecyclerView) findViewById(R.id.recyTestCategories);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Intent intent = getIntent();
        if (intent != null) {
            productId = intent.getStringExtra("producatId");
            //Toast.makeText(this, "id: "+productId, Toast.LENGTH_SHORT).show();

        }else {
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

                for (PostsCategory postsCategory : postsCategories) {

                    Log.d(TAG, "onResponse: "+postsCategory.getName());

                }
                setCategoryRec(postsCategories);


            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

    }

    private void setCategoryRec(List<PostsCategory> postsCategories) {
        mAdapter = new PostsCategoryAdapter(TestActivity.this,postsCategories);
        recyclerView.setAdapter(mAdapter);
    }

}
