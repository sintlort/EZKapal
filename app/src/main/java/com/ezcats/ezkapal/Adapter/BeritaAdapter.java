package com.ezcats.ezkapal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Model.BeritaModel;
import com.ezcats.ezkapal.R;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    List<BeritaModel> beritaModels;
    public BeritaAdapter(List<BeritaModel> beritaModels) {
        this.beritaModels = beritaModels;
    }

    @NonNull
    @Override
    public BeritaAdapter.BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_kapal_recycler, parent, false);
        return new BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaAdapter.BeritaViewHolder holder, int position) {
        BeritaModel beritaModel = beritaModels.get(position);
        holder.judulBerita.setText(beritaModel.getJudulBerita());
        holder.tanggalBerita.setText(beritaModel.getTanggalBerita());
    }

    @Override
    public int getItemCount() {
        return beritaModels.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder {

        TextView judulBerita, tanggalBerita;
        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);
            judulBerita = itemView.findViewById(R.id.title_berita);
            tanggalBerita = itemView.findViewById(R.id.date_berita);
        }
    }
}
