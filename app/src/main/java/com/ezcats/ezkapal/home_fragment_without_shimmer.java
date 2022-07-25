package com.ezcats.ezkapal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ezcats.ezkapal.Activity.CaptureActivity;
import com.ezcats.ezkapal.Activity.NotificationActivity;
import com.ezcats.ezkapal.Activity.WebViewActivity;
import com.ezcats.ezkapal.Adapter.BeritaAdapter;
import com.ezcats.ezkapal.Model.BeritaModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_fragment_without_shimmer extends Fragment {

    List<BeritaModel> beritaModelKapal = new ArrayList<>();
    List<BeritaModel> beritaModelPelabuhan = new ArrayList<>();

    RecyclerView beritaKapal, beritaPelabuhan;

    CircleImageView circleImageView;

    TextView nameText, emailText;
    String foto,name,email,number;

    ImageView notificationButton;

    private static final String TAG = "HOME_WITHOUT_SHIMMER_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment_without_shimmer, container, false);

        beritaKapal = v.findViewById(R.id.berita_kapal);
        beritaPelabuhan = v.findViewById(R.id.berita_pelabuhan);
        nameText = v.findViewById(R.id.nameFragmentHome);
        emailText = v.findViewById(R.id.emailFragmentHome);
        circleImageView = v.findViewById(R.id.profileFragmentHome);
        notificationButton = v.findViewById(R.id.notification_button);
        SharedPreferences sharedPreferences;
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        name = sharedPreferences.getString(getString(R.string.name_shared_preference),"");
        email = sharedPreferences.getString(getString(R.string.email_shared_preference),"");
        foto = sharedPreferences.getString(getString(R.string.picture_shared_preference),"");
        loadPicasso();
        nameText.setText(name);
        emailText.setText(email);
        addBeritaData();
        beritaRecycler();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void beritaRecycler() {
        BeritaAdapter beritaAdapter = new BeritaAdapter(beritaModelKapal, new BeritaAdapter.OnBeritaClick() {
            @Override
            public void OnBeritaSelect() {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });
        BeritaAdapter beritaAdapterPelabuhan = new BeritaAdapter(beritaModelPelabuhan, new BeritaAdapter.OnBeritaClick() {
            @Override
            public void OnBeritaSelect() {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        beritaKapal.setLayoutManager(layoutManager);
        beritaPelabuhan.setLayoutManager(layoutManager2);
        beritaKapal.setAdapter(beritaAdapter);
        beritaPelabuhan.setAdapter(beritaAdapterPelabuhan);
    }

    private void loadPicasso() {
        String url = "https://android.e-tiket.ngaeapp.com/storage/images/profile/"+foto;
        Log.d(TAG, "loadPicasso: "+url);
        Picasso.get().load(url).placeholder(R.drawable.home_fragment_profile).into(circleImageView);
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