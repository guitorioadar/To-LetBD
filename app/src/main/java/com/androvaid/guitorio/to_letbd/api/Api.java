package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    // Categories
    @GET("categories")
    Call<CategoriesResponse> getCategories();

    // Features
    @GET("features")
    Call<FeaturesResponse> getFeatures();

    // Posts
    @GET("posts")
    Call<PostsResponse> getPosts();

    /*@GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);*/

    // Post Detail
    @GET("posts/{id}")
    Call<PostsDetailResponse> getPostDetailResponse(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("sign-in")
    Call<SignInResponse> getSignInResponse(
            @Field("email") String email,
            @Field("password") String password
    );

}
