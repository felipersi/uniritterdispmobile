package com.example.uniritter_disp_mobile.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.example.uniritter_disp_mobile.R;

public class GPSActivity extends AppCompatActivity {
    public static final String TAG = "GPSActivity";
    //private FusedLocationProviderClient fusedLocationClient;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_activity);

        // é necessário solicitar permissão de acesso e
        // informar no manifest o uso das permissões

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                localizar();

                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Somente localização aproximada autorizada
                            } else {
                                // Nenhuma localização autorizada
                            }
                        }
                );
// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


    private void localizar() {

        // Localização precisa autorizada
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d(TAG, "onLocationChanged: " + location);

            }
        });

 /*        FusedLocationProviderClient fusedLocationClient;
         fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
         fusedLocationClient.getLastLocation()
                 .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                     @Override
                     public void onSuccess(Location location) {
                         Log.d(TAG, "onSuccess: "+location);
                     }
                 });

         fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,
                 new CancellationToken() {
                     @Override
                     public boolean isCancellationRequested() {
                         return false;
                     }

                     @NonNull
                     @Override
                     public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                         return null;
                     }
                 })
                 .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                     @Override
                     public void onSuccess(Location location) {
                         Log.d(TAG, "onSuccess current location: "+location);
                     }
                 });
                 */

    }
}
