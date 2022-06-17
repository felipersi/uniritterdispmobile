package com.example.uniritter_disp_mobile.viewmodel;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SensorsViewModel extends ViewModel {

    private MutableLiveData<String> nome;
    private MutableLiveData<List<Sensor>> sensores;
    private Context contexto;

    public SensorsViewModel() {
        super();
        nome = new MutableLiveData();
        sensores = new MutableLiveData<List<Sensor>>();


    }

    public MutableLiveData<List<Sensor>> getSensores() {
        return sensores;
    }

    public MutableLiveData<String> getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.setValue(nome);
    }

    public void setContexto(Context context) {
        this.contexto = context;
        SensorManager sensorManager = (SensorManager) contexto.getSystemService(contexto.SENSOR_SERVICE);

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.d("proximidade", ""+sensorEvent.values[0]);//+" "+ sensorEvent.values[1]+" "+ sensorEvent.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, sensorManager.getSensorList(Sensor.TYPE_PROXIMITY).get(0), SensorManager.SENSOR_DELAY_NORMAL );
        sensorManager.getSensorList(Sensor.TYPE_PROXIMITY).get(0).getMaximumRange();
        sensores.setValue( sensorManager.getSensorList(Sensor.TYPE_ALL) );
    }

}

