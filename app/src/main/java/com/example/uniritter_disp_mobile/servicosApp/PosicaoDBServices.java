package com.example.uniritter_disp_mobile.servicosApp;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface PosicaoDBServices {

        public void salvar(Location loc);
        public List<Localizacao> getAllLocalizacao();
        public List<Localizacao> getAllLocalizacaoData(long inicio, long fim);
        public List<Localizacao> getAllLocalizacaoNaoEnviadas();

        public class Localizacao {
            private long id;
            private GeoPoint localizacao;
            private Date data;
            private boolean enviado;

            public Localizacao(GeoPoint localizacao) {
                this.localizacao = localizacao;
                this.data = Calendar.getInstance().getTime();
                this.id = this.data.getTime();
                this.enviado = false;
            }
            public Localizacao(double lat, double lng, long time, boolean enviado) {
                this.localizacao = new GeoPoint(lat, lng);
                this.data = new Date(time);
                this.id = time;
                this.enviado = enviado;
            }
            public long getId() {
                return id;
            }

            public GeoPoint getLocalizacao() {
                return localizacao;
            }

            public Date getData() {
                return data;
            }

            public boolean isEnviado() {
                return enviado;
            }

            public void setEnviado(boolean enviado) {
                this.enviado = enviado;
            }
        }
    }