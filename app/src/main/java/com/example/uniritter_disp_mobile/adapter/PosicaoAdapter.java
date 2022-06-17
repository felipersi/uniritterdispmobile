package com.example.uniritter_disp_mobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.example.uniritter_disp_mobile.R;
import com.example.uniritter_disp_mobile.repositorios.PosicaoRepository;



public class PosicaoAdapter extends RecyclerView.Adapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_posicao, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.tvPosicao)).setText(
                PosicaoRepository.getInstance().getPosicoes().getValue().get(position).toString());
    }

    @Override
    public int getItemCount() {
        return PosicaoRepository.getInstance().getPosicoes().getValue().size();
    }
    public void refresh() {
        this.notifyDataSetChanged();
    }
}
