package com.example.uniritter_disp_mobile.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.uniritter_disp_mobile.services.GPSService;
import com.example.uniritter_disp_mobile.R;

public class LandActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1 = new Intent(this, GPSService.class);
        startForegroundService(intent1);
        Log.w("LandActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
    }
}