package com.ezcats.ezkapal.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Model.PelabuhanModel;
import com.ezcats.ezkapal.R;

import java.util.List;

public class PelabuhanAdapter extends RecyclerView.Adapter<PelabuhanAdapter.PelabuhanViewHolder> {

    List<PelabuhanModel> pelabuhanModelList;
    OnPelabuhanListener mOnPelabuhanListener;

    public PelabuhanAdapter(List<PelabuhanModel> pelabuhanModelList, OnPelabuhanListener onPelabuhanListener) {
        this.pelabuhanModelList = pelabuhanModelList;
        this.mOnPelabuhanListener = onPelabuhanListener;
    }

    @NonNull
    @Override
    public PelabuhanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelabuhan_recycler, parent, false);
        return new PelabuhanViewHolder(view, mOnPelabuhanListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PelabuhanViewHolder holder, int position) {
        PelabuhanModel pelabuhanModel = pelabuhanModelList.get(position);
        holder.namaPelabuhan.setText(pelabuhanModel.getNamaPelabuhan());
        holder.statusPelabuhan.setText(pelabuhanModel.getStatusPelabuhan());
        holder.kodePelabuhan.setText(pelabuhanModel.getKodePelabuhan());
        holder.tipePelabuhan.setText(pelabuhanModel.getTipePelabuhan());
        holder.alamatPelabuhan.setText(pelabuhanModel.getAlamatPelabuhan());
        holder.lamaBeroperasi.setText(pelabuhanModel.getLamaBeroperasi());

        boolean isExpandable = pelabuhanModelList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return pelabuhanModelList.size();
    }

    public class PelabuhanViewHolder extends RecyclerView.ViewHolder {

        TextView namaPelabuhan, statusPelabuhan, kodePelabuhan, tipePelabuhan, alamatPelabuhan, lamaBeroperasi;
        ConstraintLayout firstLayout, expandableLayout;
        Button buttonPilih;


        public PelabuhanViewHolder(@NonNull View itemView, OnPelabuhanListener onPelabuhanListener) {
            super(itemView);

            namaPelabuhan = itemView.findViewById(R.id.nama_pelabuhan);
            statusPelabuhan = itemView.findViewById(R.id.statsPelabuhan);
            kodePelabuhan = itemView.findViewById(R.id.pelabuhan_code);
            tipePelabuhan = itemView.findViewById(R.id.tipe_pelabuhan);
            alamatPelabuhan = itemView.findViewById(R.id.alamat);
            lamaBeroperasi = itemView.findViewById(R.id.lama_beroperasi);
            buttonPilih = itemView.findViewById(R.id.button_pilih_pelabuhan);

            firstLayout = itemView.findViewById(R.id.pelabuhan_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            firstLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PelabuhanModel pelabuhanModel = pelabuhanModelList.get(getAdapterPosition());
                    pelabuhanModel.setExpandable(!pelabuhanModel.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            buttonPilih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PelabuhanModel mPelabuhanModel = pelabuhanModelList.get(getAdapterPosition());
                    onPelabuhanListener.OnPelabuhanClick(mPelabuhanModel.getIdPelabuhan(), mPelabuhanModel.getNamaPelabuhan());
                }
            });
        }

    }

    public interface OnPelabuhanListener {
        void OnPelabuhanClick(int id, String nama_pelabuhan);
    }

}
