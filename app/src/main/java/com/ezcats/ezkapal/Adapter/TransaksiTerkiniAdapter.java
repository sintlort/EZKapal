package com.ezcats.ezkapal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Model.MetodePembayaranModel;
import com.ezcats.ezkapal.Model.TransaksiModel;
import com.ezcats.ezkapal.R;

import java.util.List;

public class TransaksiTerkiniAdapter extends RecyclerView.Adapter<TransaksiTerkiniAdapter.TransaksiTerkiniViewHolder> {

    List<TransaksiModel> transaksiModels;
    OnTerkiniClick onTerkiniClick;

    public TransaksiTerkiniAdapter(List<TransaksiModel> transaksiModelList, OnTerkiniClick onTerkiniClick) {
        this.transaksiModels = transaksiModelList;
        this.onTerkiniClick = onTerkiniClick;
    }

    @NonNull
    @Override
    public TransaksiTerkiniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaksi, parent, false);
        return new TransaksiTerkiniViewHolder(v, onTerkiniClick);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiTerkiniViewHolder holder, int position) {
        TransaksiModel transaksiModel = transaksiModels.get(position);
        holder.firstPelabuhan.setText(transaksiModel.getNama_asal());
        holder.secondPelabuhan.setText(transaksiModel.getNama_tujuan());
        holder.firstCode.setText(transaksiModel.getKode_pelabuhan_asal());
        holder.secondCode.setText(transaksiModel.getKode_pelabuhan_tujuan());
        holder.firstDermaga.setText(transaksiModel.getDermaga_asal());
        holder.secondDermaga.setText(transaksiModel.getDermaga_tujuan());
        holder.firstClock.setText(transaksiModel.getWaktu_berangkat_asal());
        holder.secondClock.setText(transaksiModel.getWaktu_berangkat_tujuan());
    }

    @Override
    public int getItemCount() {
        return transaksiModels.size();
    }

    public class TransaksiTerkiniViewHolder extends RecyclerView.ViewHolder {
        TextView firstPelabuhan, secondPelabuhan, firstCode, secondCode, firstDermaga, secondDermaga, firstClock, secondClock, date;

        CardView cardView;
        public TransaksiTerkiniViewHolder(@NonNull View itemView, OnTerkiniClick onTerkiniClick) {
            super(itemView);
            date = itemView.findViewById(R.id.ticket_date_t);
            firstPelabuhan = itemView.findViewById(R.id.first_pelabuhan_t);
            secondPelabuhan = itemView.findViewById(R.id.second_pelabuhan_t);
            firstCode = itemView.findViewById(R.id.first_pelabuhan_code_t);
            secondCode = itemView.findViewById(R.id.second_pelabuhan_code_t);
            firstDermaga = itemView.findViewById(R.id.first_dermaga_t);
            secondDermaga = itemView.findViewById(R.id.second_dermaga_t);
            firstClock = itemView.findViewById(R.id.first_start_clock_t);
            secondClock = itemView.findViewById(R.id.second_start_clock_t);
            cardView = itemView.findViewById(R.id.card_transaksi);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTerkiniClick.onClick(transaksiModels.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnTerkiniClick{
        void onClick(TransaksiModel transaksiModel);
    }
}
