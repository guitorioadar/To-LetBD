package com.androvaid.guitorio.to_letbd.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.api.RetrofitClient;
import com.androvaid.guitorio.to_letbd.model.PlaceInfo;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.androvaid.guitorio.to_letbd.model.posts.PostsResponse;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsDetailResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    private List<PlaceInfo> placeInfos = new ArrayList<>();
    private List<Posts> posts = new ArrayList<>();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);


        progressDialog.show();
        Call<PostsResponse> call = RetrofitClient.getInstance().getApi().getPosts();
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                progressDialog.dismiss();
                if(response.code()==200){
                    posts = response.body().getPosts();

                    Drawable circleDrawable = getResources().getDrawable(R.drawable.ic_buildings_24dp);
                    BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);

                    for (Posts post : posts) {

                        Log.d(TAG, "onMapReady: each posts: "+post);

                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.valueOf(post.getLatitude()),Double.valueOf(post.getLongitude())))
                                .title(post.getTitle())
                                .snippet(String.valueOf(post.getId()))
                                .icon(markerIcon)
                        );
                    }

                }else {
                    Toast.makeText(MapsActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.777176,90.399452),10));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        marker.showInfoWindow();


        Log.d(TAG, "onMarkerClick: Marker: "
                +" Id: "+marker.getId()
                +" Snippet: "+marker.getSnippet()
                +" Title: "+marker.getTitle()
        );

        progressDialog.show();
        Call<PostsDetailResponse> call = RetrofitClient.getInstance().getApi().getPostDetailResponse(marker.getSnippet());
        call.enqueue(new Callback<PostsDetailResponse>() {
            @Override
            public void onResponse(Call<PostsDetailResponse> call, Response<PostsDetailResponse> response) {
                progressDialog.dismiss();

                if(response.code()==200){
                    Log.d(TAG, "onResponse: Each Response: "+response.body().toString());
                }else {
                    Toast.makeText(MapsActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PostsDetailResponse> call, Throwable t) {

            }
        });

        return false;
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
