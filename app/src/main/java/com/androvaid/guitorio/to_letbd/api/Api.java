package com.androvaid.guitorio.to_letbd.api;

import com.androvaid.guitorio.to_letbd.model.categories.CategoriesResponse;
import com.androvaid.guitorio.to_letbd.model.features.FeaturesResponse;
import com.androvaid.guitorio.to_letbd.model.postcreate.success.PostCreateResponse;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.androvaid.guitorio.to_letbd.model.regauth.RegAuthResponse;
import com.androvaid.guitorio.to_letbd.model.signin.SignInResponse;
import com.androvaid.guitorio.to_letbd.model.signup.SignUpResponse;

import java.util.Map;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
            //@Part MultipartBody.Part image
            @Part("image") RequestBody image
            //@Part("image\"; filename=\"pp.jpg\" ") RequestBody image
    );



    // food Multipart
    @Multipart
    @POST("auth")
    Call<RegAuthResponse> getRegAuthResponse (
            @Part("email") RequestBody email,
            @Part("first_name") RequestBody name,
            @Part("") MultipartBody.Part image
    );
    // food body
    @POST("auth")
    Call<RegAuthResponse> getRegAuthRequestBodyResponse (
            @Body RequestBody file
    );
    //food header
    @POST("users/check_user")
    Call<ResponseBody> getAuthHeaderResponse (
            @HeaderMap Map<String, String> headers
            /*@Header("client") String client,
            @Header("uid") String uid,
            @Header("access-token") String access_token*/
    );

    /*@POST("sign-up")
    Call<SignUpResponse> getSignUpResponse(@Body RequestBody file);*/


    /*@Headers({
            "Accept:application/json",
            "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjgzYWVmNDY5NjUxMTA2YWQzZDUxNTVmNjBhNjU4YjM0OWI2MWFjYjk0ZDRlNjhjOGQzZGQ4ZGY0ZTk0MzBhNjRjOWU4YjI0MTU4YTk5ZTMyIn0.eyJhdWQiOiIxIiwianRpIjoiODNhZWY0Njk2NTExMDZhZDNkNTE1NWY2MGE2NThiMzQ5YjYxYWNiOTRkNGU2OGM4ZDNkZDhkZjRlOTQzMGE2NGM5ZThiMjQxNThhOTllMzIiLCJpYXQiOjE1MzY1MTMwODIsIm5iZiI6MTUzNjUxMzA4MiwiZXhwIjoxNTY4MDQ5MDgyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.rOrPiMXXhmpseGp51dflx2Mfn2ytz6Fhlhuc9ZM8teIoBr5gpLb-6guHPazgsrx4TJ1AoIaGij1OFSySJpwE8S8NsvkwdpR_CBgw86CZskbmQrAFRvtNPo7NArSEDhMWA7MVwVbK9WlNLald3hXiENIvIxJq3TmlBsZLdmUpzcOcL1PHcgK31OKWLIVCfMXJe6SATeXiNHV5jwyzNcEaxGjPOVIsxeM0iz0CRmiJ7VxBHtnCfq6i86z6zwzolKHLHcdvc_6Yec5J6t1qN8n4-AZP57ImaCUDvXm8EBTKq1fL6UAm6B78MtBhTpu0dzWwdHG9uaDcRBdDUhM3UzFoC01Zbv7R6wbNkekfTghGP2Obh5xUYBwACLJh5XyI31PCT4mKbl5faWVxGPAH2QZgWIy-phR0m47rHEDUOCvmGwXIjVZ7NTZw9mAAZzQ5Bq6I_NDqy_YyVL_-9lag92ifP8TPj1JBKzdnGaBPopdunWQXvFUTLICH0ebhA6DgK4QBF0irKOGqQSZNIn7qQkvnPxyZZGBp1T4IF9gTTFtJb7PInW29yVvgoSoT_DUaIePBYqbLiiq0xZ3I4tQAsT4PeFzLr48xvWt6yMXh0FhAkOCanLGhqRz6upSNA_SOpe8obTvfOhww49dLXrXwHE9E_cV2UVPZvtHjik6dP5XU_ag"
    })*/
    @Multipart
    @POST("post-create")
    Call<PostCreateResponse> getPostCreateResponse(

            // Referance: https://stackoverflow.com/questions/39866676/retrofit-uploading-multiple-images-to-a-single-key

            @HeaderMap Map<String, String> headers,
            //@Header("Accept") String header1,
            //@Header("Authorization") String header2,
            @Part("title") RequestBody title,
            @Part("location") RequestBody location,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part featured_photo,
            @Part("condition") RequestBody condition,
            @Part("rent_amount") RequestBody rent_amount,
            @Part("is_negotiable") RequestBody is_negotiable,
            @Part("available_from") RequestBody available_from
            //@Part("categories") List<Integer> categories,
            //@Part MultipartBody.Part[] images,
            //@Part MultipartBody.Part[] file,
            //@Part List<MultipartBody.Part> images,
            //@Part("features") List<Integer> features

    );

    @POST("post-create")
    Call<PostCreateResponse> getPostCreateBodyResponse(
            @Header("Accept") String accept,
            @Header("Authorization") String authorization,
            @Body RequestBody file
    );

}
