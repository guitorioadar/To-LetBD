package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("categories")
    Call<CategoriesResponse> getCategories();

    @GET("posts")
    Call<PostsResponse> getPosts();
}
