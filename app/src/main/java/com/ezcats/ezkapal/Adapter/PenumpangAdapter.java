package com.ezcats.ezkapal.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Fragment.PelabuhanFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.Model.PenumpangModel;
import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.ticket_kapal;

import java.util.List;

public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.PenumpangViewHolder> {

    List<PenumpangModel> mPenumpangModel;
    OnPenumpangView onPenumpangView;

    public PenumpangAdapter(List<PenumpangModel> penumpangModels, OnPenumpangView onPenumpangModels) {
        this.mPenumpangModel = penumpangModels;
        this.onPenumpangView = onPenumpangModels;
    }


    @NonNull
    @Override
    public PenumpangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_penumpang, parent, false);
        return new PenumpangViewHolder(view, onPenumpangView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenumpangViewHolder holder, int position) {
        PenumpangModel model = mPenumpangModel.get(position);
        holder.number.setText(String.valueOf(position + 1));
        holder.namePenumpang.setText(model.getNamaPenumpang());
        holder.ktpPenumpang.setText(model.getKtpPenumpang());
        if(model.getCard_color().matches("#ffffe0")){
            holder.cardView.setCardBackgroundColor(Color.RED);
            holder.cardView.setAlpha(0.5f);
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.cardView.setAlpha(1f);
        }
    }

    @Override
    public int getItemCount() {
        return mPenumpangModel.size();
    }

    public class PenumpangViewHolder extends RecyclerView.ViewHolder {
        TextView number, namePenumpang, ktpPenumpang;
        CardView cardView;
        ConstraintLayout constraintLayout;
        public PenumpangViewHolder(@NonNull View itemView, OnPenumpangView onPenumpangView) {
            super(itemView);
            number = itemView.findViewById(R.id.nomor_penumpang);
            namePenumpang = itemView.findViewById(R.id.nama_penumpang);
            ktpPenumpang = itemView.findViewById(R.id.nomor_ktp);
            cardView = itemView.findViewById(R.id.card_id);
            constraintLayout = itemView.findViewById(R.id.constraint_penumpang);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PenumpangModel model = mPenumpangModel.get(getAdapterPosition());
                    onPenumpangView.onPenumpangClick(getAdapterPosition(), model.getNamaPenumpang(), model.getKtpPenumpang());
                }
            });
        }
    }

    public interface OnPenumpangView {
        void onPenumpangClick(int position, String name, String ktp_number);
    }
}
