package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("categories")
    Call<CategoriesResponse> getCategories();

    @GET("posts")
    Call<PostsResponse> getPosts();

    /*@GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);*/

    @GET("posts/{id}")
    Call<PostsDetailResponse> getPostDetailResponse(@Path("id") String id);

}
