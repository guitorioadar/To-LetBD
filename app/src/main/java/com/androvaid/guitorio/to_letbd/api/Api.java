package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;
import com.androvaid.guitorio.to_letbd.model.signup.SignUpResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    // Post Detail
    @GET("posts/{id}")
    Call<PostsDetailResponse> getPostDetailResponse(
            @Path("id") String id
    );

    // Sign In
    @FormUrlEncoded
    @POST("sign-in")
    Call<SignInResponse> getSignInResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    // Sign Up
    @Multipart
    @POST("sign-up")
    Call<SignUpResponse> getSignUpResponse(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part image
    );

}
