package com.androvaid.guitorio.to_letbd.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.activity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import static com.github.florent37.runtimepermission.RuntimePermission.askPermission;


public class Utility implements OnMapReadyCallback {

    // Logd
    private static final String TAG = "Utility";
    public static boolean mLocationPermissionGranted = false;

    // Context
    private Context context;

    //
    private Activity activity;

    // Map
    private GoogleMap mMapUtil;
    private FusedLocationProviderClient mFusedLocationClient;

    // Location
    private static final float DEFAULT_ZOOM = 15f;

    public Utility() {

    }

    public Utility(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    /*public Utility(Activity activity){
        this.activity = activity;
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapUtil = googleMap;
    }

    public void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the the devices current location ");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    Toast.makeText(context, location.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Location: total"+ location.toString());
//                    Log.d("Location: extra", location.getExtras().toString());
                    Log.d(TAG,"Location: latitude: "+ String.valueOf(location.getLatitude()));
                    Log.d(TAG,"Location: longitude: "+ String.valueOf(location.getLongitude()));

                    UtilityInterfaces utilityInterfaces = (UtilityInterfaces) context;
                    if (utilityInterfaces != null) {
                        utilityInterfaces.moveCameraInterface(new LatLng(location.getLatitude(), location.getLongitude()),DEFAULT_ZOOM , "Current Location");
                    }

                    //moveCamera(new LatLng(location.getLatitude(), location.getLongitude()),DEFAULT_ZOOM , "Current Location");
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    //mMapUtil.setMyLocationEnabled(true);
                    //mMapUtil.getUiSettings().setMyLocationButtonEnabled(false);
                } else {
                    Toast.makeText(context, "Turn on Your Location button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*public void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to : lat: " + latLng.latitude + ", lng : " + latLng.longitude);
        mMapUtil.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("Current Location")) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMapUtil.addMarker(markerOptions);
            hideSoftkeyboard();
        }


    }*/




    public void getAllPermissions() {

        //Toast.makeText(this, "getAllPermissions", Toast.LENGTH_SHORT).show();

        askPermission((FragmentActivity) context)
                .request(Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_CONTACTS)
                .onAccepted((result) -> {
                    //all permissions already granted or just granted

                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();

                    mLocationPermissionGranted = true;
                })
                .onDenied(result -> {
                    Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show();
                    for (String permission : result.getDenied()) {
                        //appendText(tvUsersName, permission);
                        Toast.makeText(context, "Denied permision: " + permission, Toast.LENGTH_SHORT).show();
                    }

                    new AlertDialog.Builder(context)
                            .setMessage("Please accept our permissions")
                            .setPositiveButton("yes", (dialog, which) -> {
                                result.askAgain();
                            }) // ask again
                            .setNegativeButton("no", (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .show();

                })
                .onForeverDenied(result -> {
                    Toast.makeText(context, "Forever Denied", Toast.LENGTH_SHORT).show();

                    for (String permission : result.getForeverDenied()) {
                        Toast.makeText(context, "Forever", Toast.LENGTH_SHORT).show();
                    }
                })
                .ask();

    }

    public void hideSoftkeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                Log.d(TAG, "hideSoftkeyboard: " + e.getMessage());
            }
        }
    }


}
