package com.example.uniritter_disp_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.location.Location;
import android.location.Address;



import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtLongitude;
    private TextView txtLatitude;
    private TextView txtEstado;
    private TextView txtCidade;

    private Location location;
    private LocationManager locationManager;

    private Address endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtEstado = (TextView) findViewById(R.id.txtEstado);



        double latitude = -23.562198;
        double longitude = -46.655672;

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        }
        if(location != null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        txtLongitude.setText("Longitude: "+ longitude);
        txtLatitude.setText("Latitude: "+ latitude);

        try {
            endereco = buscaEndereco(latitude, longitude);

            txtCidade.setText("Cidade: " + endereco.getLocality());
            txtEstado.setText("Estado: " + endereco.getAdminArea());

        } catch (IOException e){
            Log.i("GPS", e.getMessage());
        }
    }

    public Address buscaEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude,1);

        if (addresses.size() > 0) {
            address = addresses.get(0);
        }
        return address;

    }


}
