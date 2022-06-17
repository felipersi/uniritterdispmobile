package com.example.uniritter_disp_mobile.viewmodel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PosicaoViewModel extends ViewModel {
    public MutableLiveData<List<Location>> locations;

    public PosicaoViewModel() {
        locations = new MutableLiveData<>();
        locations.setValue(new ArrayList<>());
    }

    public void addLocation(Location loc) {
        List<Location>  lista = new ArrayList<>(locations.getValue());
        lista.add(loc);
        locations.setValue(lista);

    }
}
