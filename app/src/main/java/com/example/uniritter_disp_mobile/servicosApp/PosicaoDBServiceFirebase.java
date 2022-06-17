package com.example.uniritter_disp_mobile.servicosApp;

import android.location.Location;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class PosicaoDBServiceFirebase implements PosicaoDBServices {

    private FirebaseFirestore db;
    private CollectionReference collection;
    public PosicaoDBServiceFirebase() {
        db = FirebaseFirestore.getInstance();
        collection = db.collection("prof");
    }
    @Override
    public void salvar(Location loc) {
        GeoPoint gp = new GeoPoint(loc.getLatitude(), loc.getLongitude());
        collection.add(new Localizacao(gp));
    }

    @Override
    public List<Localizacao> getAllLocalizacao() {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoData(long inicio, long fim) {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoNaoEnviadas() {
        return null;
    }
}
