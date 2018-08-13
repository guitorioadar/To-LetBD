package com.androvaid.guitorio.to_letbd.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static com.androvaid.guitorio.to_letbd.utils.Utility.mLocationPermissionGranted;
import static com.github.florent37.runtimepermission.RuntimePermission.askPermission;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(isServicesOK()){
            getAllPermissions();
        }

    }

    private void init(){
        startActivity(new Intent(this,MainActivity.class));
    }

    private void getAllPermissions() {

        //Toast.makeText(this, "getAllPermissions", Toast.LENGTH_SHORT).show();

        askPermission(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_CONTACTS)
                .onAccepted((result) -> {
                    //all permissions already granted or just granted

                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    init();

                    mLocationPermissionGranted = true;
                })
                .onDenied(result -> {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                    for (String permission : result.getDenied()) {
                        //appendText(tvUsersName, permission);
                        Toast.makeText(this, "Denied permision: " + permission, Toast.LENGTH_SHORT).show();
                    }

                    new AlertDialog.Builder(this)
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
                    Toast.makeText(this, "Forever Denied", Toast.LENGTH_SHORT).show();

                    for (String permission : result.getForeverDenied()) {
                        Toast.makeText(this, "Forever", Toast.LENGTH_SHORT).show();
                    }
                })
                .ask();

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}