package com.example.uniritter_disp_mobile.repositorios;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import com.example.uniritter_disp_mobile.servicosApp.PosicaoServices;


public class PosicaoRepository {

    private static PosicaoRepository instance;
    private MutableLiveData<List<Location>> dados;
    private Context context;

    private PosicaoRepository(Context context) {
        this.context = context;
        dados = new MutableLiveData<>();
        dados.setValue(new ArrayList<>());
    }

    public static PosicaoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosicaoRepository(context);
        }
        return instance;
    }

    // aqui pode quebrar pois se não for criado antes com um context
    public static PosicaoRepository getInstance() {
        return instance;
    }

    public MutableLiveData<List<Location>> getPosicoes() {
        return dados;
    }
    public void incluir(Location loc, float dist) {
        int d = (int) dist;
        PosicaoServices.getInstance(context).gravar(loc);
        dados.getValue().add(loc);
        dados.setValue(dados.getValue());
    }
    public Context getContext() {
        return this.context;
    }
}



