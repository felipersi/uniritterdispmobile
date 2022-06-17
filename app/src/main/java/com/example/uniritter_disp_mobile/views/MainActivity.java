package com.example.uniritter_disp_mobile.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import com.example.uniritter_disp_mobile.R;
import com.example.uniritter_disp_mobile.adapter.PosicaoAdapter;
import com.example.uniritter_disp_mobile.broadcastreceiver.GPSBroadcastReceiver;
import com.example.uniritter_disp_mobile.repositorios.PosicaoRepository;
import com.example.uniritter_disp_mobile.sqlite.DBHelper;
import com.example.uniritter_disp_mobile.viewmodel.SensorsViewModel;
import com.example.uniritter_disp_mobile.viewmodel.PosicaoViewModel;



public class MainActivity extends AppCompatActivity {

    private SensorsViewModel viewmodel;
    int valor = 0;
    BroadcastReceiver br;
    //adicionado requiresApi aqui
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registrarIntentFilters();


        findViewById(R.id.button2).setOnClickListener(view->{
            Intent intent = new Intent();
            intent.setAction("br.edu.uniritter.GPS_START");
            getApplicationContext().sendBroadcast(intent);
        });

        /*findViewById(R.id.btNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), GPSActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        */
        setarRecyclerViewGPS();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();


        findViewById(R.id.buttonMap).setOnClickListener((v)->{
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            startActivity(intent);
        });
    }

    private void setarRecyclerViewGPS() {
        RecyclerView recyclerView =  findViewById(R.id.rvPosicao);
        PosicaoAdapter adapter =  new PosicaoAdapter();
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        PosicaoViewModel viewmodel = new ViewModelProvider(this).get(PosicaoViewModel.class);

        PosicaoRepository.getInstance().getPosicoes().observe(this,
                new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {
                        adapter.refresh();
                    }
                }
        );
    }

    private void carregaPreferencias() {

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int qtd = 0;
        if (preferences.contains("qtd")) {
            qtd = preferences.getInt("qtd",0)+1;
        }
        editor.putInt("qtd", qtd);
        editor.commit();
    }
    //adicionado requiresApi
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verificaPermissoes() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            Boolean backgroundLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted && backgroundLocationGranted) {
                                Log.d("MainActivity", "onCreate: autorizado GPS");
                                Toast.makeText(this, "Localização autorizado", Toast.LENGTH_SHORT).show();


                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Toast.makeText(this, "Somente localização aproximada", Toast.LENGTH_SHORT).show();
                                // Somente localização aproximada autorizada
                            } else {
                                // Nenhuma localização autorizada
                                Toast.makeText(this, "Localização não autorizada", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

    }

    //adicionado requiresApi
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registrarIntentFilters() {
        br = new GPSBroadcastReceiver();
        IntentFilter intf = new IntentFilter("com.example.uniritter_disp_mobile.GPS_START");
        IntentFilter intf1 = new IntentFilter("android.intent.action.BOOT_COMPETED");

        registerReceiver(br, intf);
        registerReceiver(br, intf1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // uma boa prática é desregistrar o brodcastreceiver no final da aplicação
        //unregisterReceiver(br);
    }
}
