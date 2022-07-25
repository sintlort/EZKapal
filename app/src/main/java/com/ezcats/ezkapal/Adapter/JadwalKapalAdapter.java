package com.ezcats.ezkapal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Model.JadwalKapalModel;
import com.ezcats.ezkapal.R;

import org.w3c.dom.Text;

import java.util.List;

public class JadwalKapalAdapter extends RecyclerView.Adapter<JadwalKapalAdapter.JadwalKapalViewHolder> {

    List<JadwalKapalModel> jadwalKapalModels;
    public JadwalKapalAdapter(List<JadwalKapalModel> jadwalKapalModels) {
        this.jadwalKapalModels = jadwalKapalModels;
    }

    @NonNull
    @Override
    public JadwalKapalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jadwal_kapal,parent,false);
        return new JadwalKapalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalKapalViewHolder holder, int position) {
        JadwalKapalModel j = jadwalKapalModels.get(position);
        holder.tanggal.setText(j.getTanggal());
        holder.nama_asal.setText(j.getAsal_pelabuhan());
        holder.nama_tujuan.setText(j.getTujuan_pelabuhan());
        holder.status_asal.setText(j.getStatus_asal());
        holder.status_tujuan.setText(j.getStatus_tujuan());
        holder.kode_asal.setText(j.getKode_asal());
        holder.kode_tujuan.setText(j.getKode_tujuan());
        holder.waktu_berangkat.setText(j.getWaktu_berangkat());
        holder.waktu_sampai.setText(j.getWaktu_sampai());
        holder.dermaga_asal.setText(j.getDermaga_asal());
        holder.dermaga_tujuan.setText(j.getDermaga_asal());
    }

    @Override
    public int getItemCount() {
        return jadwalKapalModels.size();
    }

    public static class JadwalKapalViewHolder extends RecyclerView.ViewHolder {

        TextView tanggal, nama_asal, nama_tujuan, status_asal, status_tujuan, kode_asal, kode_tujuan, waktu_berangkat, waktu_sampai, dermaga_asal, dermaga_tujuan;

        public JadwalKapalViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.ticket_date_jadwal_kapal);
            nama_asal = itemView.findViewById(R.id.first_pelabuhan_jadwal_kapal);
            nama_tujuan = itemView.findViewById(R.id.second_pelabuhan_jadwal_kapal);
            status_asal = itemView.findViewById(R.id.first_pelabuhan_status_jadwal_kapal);
            status_tujuan = itemView.findViewById(R.id.second_pelabuhan_status_jadwal_kapal);
            kode_asal = itemView.findViewById(R.id.first_pelabuhan_code_jadwal_kapal);
            kode_tujuan = itemView.findViewById(R.id.second_pelabuhan_code_jadwal_kapal);
            waktu_berangkat = itemView.findViewById(R.id.first_start_clock_jadwal_kapal);
            waktu_sampai = itemView.findViewById(R.id.second_start_clock_jadwal_kapal);
            dermaga_asal = itemView.findViewById(R.id.first_dermaga_jadwal_kapal);
            dermaga_tujuan = itemView.findViewById(R.id.second_dermaga_jadwal_kapal);
        }
    }
}
