package com.ezcats.ezkapal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Model.MetodePembayaranModel;
import com.ezcats.ezkapal.R;

import org.w3c.dom.Text;

import java.util.List;

public class MetodePembayaranAdapter extends RecyclerView.Adapter<MetodePembayaranAdapter.MetodePembayaranViewHolder> {

    List<MetodePembayaranModel> metodePembayaranModels;
    OnMetodePembayaranListener onMetodePembayaranListener;

    public MetodePembayaranAdapter(List<MetodePembayaranModel> list, OnMetodePembayaranListener onMetodePembayaranListener) {
        this.metodePembayaranModels = list;
        this.onMetodePembayaranListener = onMetodePembayaranListener;
    }

    @NonNull
    @Override
    public MetodePembayaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_metode_pembayaran,parent,false);
        return new MetodePembayaranViewHolder(view, onMetodePembayaranListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MetodePembayaranViewHolder holder, int position) {
        MetodePembayaranModel model = metodePembayaranModels.get(position);
        holder.metode_pembayaran.setText(model.getNama_metode().replace("_", " ").toUpperCase());
        holder.metode.setText(model.getMetode().replace("_", " ").toUpperCase());
        holder.nomor_rekening.setText(model.getNomor_rekening());
    }

    @Override
    public int getItemCount() {
        return metodePembayaranModels.size();
    }

    public class MetodePembayaranViewHolder extends RecyclerView.ViewHolder {

        TextView metode, metode_pembayaran, nomor_rekening;
        CardView cardView;
        public MetodePembayaranViewHolder(@NonNull View itemView, OnMetodePembayaranListener onMetodePembayaranListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_metode_pembayaran);
            metode = itemView.findViewById(R.id.metode);
            metode_pembayaran = itemView.findViewById(R.id.metode_pembayaran);
            nomor_rekening = itemView.findViewById(R.id.nomor_rekening);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MetodePembayaranModel metodePembayaranModel = metodePembayaranModels.get(getAdapterPosition());
                    onMetodePembayaranListener.OnMetodeClicked(metodePembayaranModel.getId(), metodePembayaranModel.getNama_metode());
                }
            });
        }
    }

    public interface OnMetodePembayaranListener{
        void OnMetodeClicked(int id, String nama_metode);
    }
}
