package com.ezcats.ezkapal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezcats.ezkapal.Adapter.BeritaAdapter;
import com.ezcats.ezkapal.Model.BeritaModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class home_fragment extends Fragment {
    private ShimmerFrameLayout container, container1, container2;

    List<BeritaModel> beritaModelKapal = new ArrayList<>();
    List<BeritaModel> beritaModelPelabuhan = new ArrayList<>();

    RecyclerView beritaKapal, beritaPelabuhan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        beritaKapal = v.findViewById(R.id.berita_kapal);
        beritaPelabuhan = v.findViewById(R.id.berita_pelabuhan);
        addBeritaData();
        beritaRecycler();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = (ShimmerFrameLayout) view.findViewById(R.id.shimmerFragmentHome);
        container1 = (ShimmerFrameLayout) view.findViewById(R.id.berita_pelabuhan_shimmer);
        container2 = (ShimmerFrameLayout) view.findViewById(R.id.berita_kapal_shimmer);
        container.startShimmer();
        container.stopShimmer();
        container1.startShimmer();
        container1.stopShimmer();
        container2.startShimmer();
        container2.stopShimmer();
    }

    public void beritaRecycler() {
        BeritaAdapter beritaAdapter = new BeritaAdapter(beritaModelKapal);
        BeritaAdapter beritaAdapterPelabuhan = new BeritaAdapter(beritaModelPelabuhan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        beritaKapal.setLayoutManager(layoutManager);
        beritaPelabuhan.setLayoutManager(layoutManager2);
        beritaKapal.setAdapter(beritaAdapter);
        beritaPelabuhan.setAdapter(beritaAdapterPelabuhan);
    }

    public void addBeritaData() {
        beritaModelKapal.add(new BeritaModel("Kapal hancur diterjang badai", "2021-01-01", "AAAA"));
        beritaModelKapal.add(new BeritaModel("Kapal Ro Ro kembali beroperasi", "2021-01-02", "AAAA"));
        beritaModelKapal.add(new BeritaModel("Speedboat Caspla telah beroperasi", "2021-01-03", "AAAA"));
        beritaModelKapal.add(new BeritaModel("Speedboat Maruti mogok kerja", "2021-01-03", "AAAA"));
        beritaModelPelabuhan.add(new BeritaModel("Padangbai ditutup untuk sementara dikarenakan investigasi", "2021-05-02", "AAA"));
        beritaModelPelabuhan.add(new BeritaModel("Sanur tetap dibuka pada hari raya galungan dan kuningan", "2021-05-03", "AAA"));
        beritaModelPelabuhan.add(new BeritaModel("Padangbai beroperasi kembali", "2021-05-12", "AAA"));
        beritaModelPelabuhan.add(new BeritaModel("Air tinggi, Pelabuhan Samba berhenti beroperasi", "2021-05-15", "AAA"));
    }
}