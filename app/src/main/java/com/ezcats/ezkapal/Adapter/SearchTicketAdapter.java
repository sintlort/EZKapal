package com.ezcats.ezkapal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Activity.DetailPesanan;
import com.ezcats.ezkapal.Model.TicketModel;
import com.ezcats.ezkapal.R;

import java.util.List;

public class SearchTicketAdapter extends RecyclerView.Adapter<SearchTicketAdapter.SearchTicketViewHolder> {

    List<TicketModel> mTicketModelList;
    int jumlah_penumpang;
    OnPilihTiketListener mOnPilihTiketListener;
    public SearchTicketAdapter(List<TicketModel> ticketModelList,OnPilihTiketListener onPilihTiketListener, int jumlah_penumpang) {
        this.mTicketModelList = ticketModelList;
        this.mOnPilihTiketListener = onPilihTiketListener;
        this.jumlah_penumpang = jumlah_penumpang;
    }

    @NonNull
    @Override
    public SearchTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_ticket_recycler_view, parent, false);
        return new SearchTicketViewHolder(view, mOnPilihTiketListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTicketViewHolder holder, int position) {
        TicketModel ticketModel = mTicketModelList.get(position);
        holder.ticketDays.setText(ticketModel.getHari());
        holder.ticketDate.setText(ticketModel.getTanggal());
        holder.portNameStart.setText(ticketModel.getNama_asal());
        holder.portNameEnd.setText(ticketModel.getNama_tujuan());
        holder.statusPortStart.setText(ticketModel.getStatus_asal());
        holder.statusPortEnd.setText(ticketModel.getStatus_tujuan());
        holder.codePortStart.setText(ticketModel.getKode_pelabuhan_asal());
        holder.codePortEnd.setText(ticketModel.getKode_pelabuhan_tujuan());
        holder.clockPortStart.setText(ticketModel.getWaktu_berangkat_asal());
        holder.clockPortEnd.setText(ticketModel.getWaktu_berangkat_tujuan());
        holder.dockPortStart.setText(ticketModel.getDermaga_asal());
        holder.dockPortEnd.setText(ticketModel.getDermaga_tujuan());
        holder.priceTicket.setText(ticketModel.getHarga());
    }

    @Override
    public int getItemCount() {
        return mTicketModelList.size();
    }


    public class SearchTicketViewHolder extends RecyclerView.ViewHolder {

        TextView ticketDays, ticketDate, portNameStart, portNameEnd, statusPortStart, statusPortEnd, codePortStart, codePortEnd, clockPortStart, clockPortEnd, dockPortStart, dockPortEnd, priceTicket;
        Button mBtnPilih;

        public SearchTicketViewHolder(@NonNull View v, OnPilihTiketListener onPilihTiketListener) {
            super(v);
            ticketDays = v.findViewById(R.id.ticket_days);
            ticketDate = v.findViewById(R.id.ticket_date);
            portNameStart = v.findViewById(R.id.first_pelabuhan);
            portNameEnd = v.findViewById(R.id.second_pelabuhan);
            statusPortStart = v.findViewById(R.id.first_pelabuhan_status);
            statusPortEnd = v.findViewById(R.id.second_pelabuhan_status);
            codePortStart = v.findViewById(R.id.first_pelabuhan_code);
            codePortEnd = v.findViewById(R.id.second_pelabuhan_code);
            clockPortStart = v.findViewById(R.id.first_start_clock);
            clockPortEnd = v.findViewById(R.id.second_start_clock);
            dockPortStart = v.findViewById(R.id.first_dermaga);
            dockPortEnd = v.findViewById(R.id.second_dermaga);
            priceTicket = v.findViewById(R.id.harga);

            mBtnPilih = v.findViewById(R.id.btn_pilih_tiket);
            mBtnPilih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TicketModel ticketModel = mTicketModelList.get(getAdapterPosition());
                    onPilihTiketListener.onClickPilihTiket(DetailPesanan.class, ticketModel);
                }
            });
        }
    }

    public interface OnPilihTiketListener{
        void onClickPilihTiket(Class classTarget, TicketModel ticketModel);
    }
}
