package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;
import com.androvaid.guitorio.to_letbd.model.signup.SignUpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.androvaid.guitorio.to_letbd.model.postcreate.PostCreateResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    // post create er api koi  ?

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

    // vai... korbani tme bolo
    //category er option koi  ? array pathabo to.... hmmmm.. ok.... thn? arry ta kikvabe d tui kor.... vai shono
    // data gece to... ebar ame ek na ek system a pathay debone... evabe age gece.... ekhon jabe ki na ta to ar jane na...
    // karon er to ekta nirdisto formate ache... so ki hobe bola jacche na... check kore kore dekhte hobe
    //ok bujhte parchi ki?
    //10 min kaj kore then tore janacchi vai ? shob thik nai?

    @Multipart
    @POST("sign-up")
    Call<PostCreateResponse> getPostCreateResponse(

            // Referance: https://stackoverflow.com/questions/39866676/retrofit-uploading-multiple-images-to-a-single-key

            @HeaderMap Map<String, String> headers,
            @Part("title") RequestBody title,
            @Part("location") RequestBody location,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part featured_photo,
            @Part("condition") RequestBody condition,
            @Part("rent_amount") RequestBody rent_amount,
            @Part("is_negotiable") RequestBody is_negotiable,
            @Part("available_from") RequestBody available_from,
            @Part("categories") List<Integer> categories,
            //@Part MultipartBody.Part[] images,
            //@Part List<MultipartBody.Part> images,
            //@Part MultipartBody.Part[] file,
            @Part("features") List<Integer> features

    );

}
