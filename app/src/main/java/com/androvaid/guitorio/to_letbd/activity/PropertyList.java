package com.androvaid.guitorio.to_letbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.adapter.PostRecyclerViewAdapter;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.model.categories.Categories;
import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertyList extends AppCompatActivity {

    private static final String TAG = "PropertyList";


    private List<Posts> posts = new ArrayList<>();
    private Posts[] arrayPosts;

    private List<Categories> categories = new ArrayList<>();
    private String[] categoryNames;

    private MaterialSpinner spnCategory, spnSortBy;

    private RecyclerView toLetItemRecyclerView;

    private String _sortBY,_category,_categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        // Initialization
        // Spinner
        spnSortBy = (MaterialSpinner) findViewById(R.id.spnSortBy);
        spnCategory = (MaterialSpinner) findViewById(R.id.spnCategory);

        // RecyclerView
        toLetItemRecyclerView = findViewById(R.id.toLetItemRecyclerView);
        toLetItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        * ------------------------------ Spinner Sort ---------------------------
        * */

        String[] sortItems = {"Latest", "Oldest", "Low", "High"};
        ArrayAdapter<String> adapterSort = new ArrayAdapter<String>(PropertyList.this, android.R.layout.simple_spinner_item, sortItems);
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnSortBy.setAdapter(adapterSort);
        spnSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _sortBY = parent.getItemAtPosition(position).toString();

                Log.d(TAG, "onItemSelected: spnSort: "+_sortBY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
         * ------------------------------ Spinner Sort Ends ---------------------------
         * */

        /*
         * --------------------------- Category Spinner ---------------------------
         * */

        Call<CategoriesResponse> call = RetrofitClient.getInstance().getApi().getCategories();

        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {

                assert response.body() != null;
                categories = response.body().getCategories();

                categoryNames = new String[categories.size()];

                for (int i = 0; i < categories.size(); i++) {
                    categoryNames[i] = String.valueOf(categories.get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PropertyList.this, android.R.layout.simple_spinner_item, categoryNames);
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
                });


            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

        /*
         * --------------------------- Category Spinner Ends ---------------------------
         * */

        /*
        * --------------------- All Posts -------------------------
        * */

        Log.d(TAG, "onCreate: initial sortBY: "+_sortBY);
        Log.d(TAG, "onCreate: initial category: "+_category);
        Log.d(TAG, "onCreate: initial categoryID: "+_categoryId);

        allPropertyList(_sortBY,_category);

        /*
         * --------------------- All Posts -------------------------
         * */

    }

    public void btnSearchClickEvent(View view) {

        allPropertyList(_sortBY,_category);

    }

    private void allPropertyList(String sort,String cat){
        Log.d(TAG, "allPropertyList: "+sort);
        Log.d(TAG, "allPropertyList: "+cat);

        Call<PostsResponse> call = RetrofitClient.getInstance().getApi().getPosts();

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                Log.d(TAG, "onResponse: PostResponce: Meta: "+response.body().getMeta());
                Log.d(TAG, "onResponse: PostResponce: Posts: "+response.body().getPosts());

                try {
                    posts = response.body().getPosts();

                    toLetItemRecyclerView.setAdapter(new PostRecyclerViewAdapter(PropertyList.this,posts.toArray(new Posts[posts.size()])));

                }catch (Exception e){
                    Log.d(TAG, "onResponse: Error: Exceptions: "+e.getMessage() );
                }

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });

    }


}
