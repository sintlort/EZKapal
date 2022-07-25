package com.ezcats.ezkapal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezcats.ezkapal.Activity.EditProfileActivity;
import com.ezcats.ezkapal.Activity.InfoKapal;
import com.ezcats.ezkapal.Activity.JadwalKapal;
import com.ezcats.ezkapal.Activity.LandingActivity;
import com.ezcats.ezkapal.Activity.RiwayatTransaksiActivity;
import com.ezcats.ezkapal.Activity.TransaksiTerkiniActivity;
import com.ezcats.ezkapal.Fragment.LogoutFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_profile extends Fragment implements LogoutFragment.LogoutListener {

    private static final String TAG = "PROFILE_FRAGMENT";


    CardView transaksiTerkini, transaksiRiwayat, hubungi, logout, info_kapal, jadwal_kapal;
    Button editProfile;
    CircleImageView circleImageView;
    SharedPreferences sharedPreferences;
    TextView nameProfile;
    String name,foto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        transaksiTerkini = v.findViewById(R.id.transaksi_aktif);
        transaksiRiwayat = v.findViewById(R.id.riwayat_transaksi);
        hubungi = v.findViewById(R.id.hubungi_kami);
        logout = v.findViewById(R.id.logout);
        nameProfile = v.findViewById(R.id.profile_name);
        circleImageView = v.findViewById(R.id.profile_image_fragment);
        editProfile = v.findViewById(R.id.edit_profile_fragment);
        info_kapal = v.findViewById(R.id.info_kapal);
        jadwal_kapal = v.findViewById(R.id.jadwal_kapal);
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String type_account = sharedPreferences.getString(getString(R.string.type_account), "");
        checkAccount(type_account);
        name = sharedPreferences.getString(getString(R.string.name_shared_preference),"");
        foto = sharedPreferences.getString(getString(R.string.picture_shared_preference),"");
        loadPicasso();
        nameProfile.setText(name);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutStart();
            }
        });

        transaksiTerkini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TransaksiTerkiniActivity.class);
                startActivity(intent);
            }
        });

        transaksiRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RiwayatTransaksiActivity.class);
                startActivity(intent);
            }
        });

        hubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:08977200423"));
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        info_kapal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoKapal();
            }
        });

        jadwal_kapal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoJadwal();
            }
        });

        return v;
    }

    private void gotoKapal() {
        Intent intent = new Intent(getContext(), InfoKapal.class);
        startActivity(intent);
    }

    private void gotoJadwal() {
        Intent intent = new Intent(getContext(), JadwalKapal.class);
        startActivity(intent);
    }

    private void checkAccount(String type_account) {
        if(type_account.matches("TAdmin")){
            info_kapal.setVisibility(View.VISIBLE);
            jadwal_kapal.setVisibility(View.VISIBLE);
        } else {
            info_kapal.setVisibility(View.GONE);
            jadwal_kapal.setVisibility(View.GONE);
        }
    }

    private void loadPicasso() {
        String url = "https://android.e-tiket.ngaeapp.com/storage/images/profile/"+foto;
        Picasso.get().load(url).placeholder(R.drawable.home_fragment_profile).into(circleImageView);
    }

    private void logoutStart() {
        LogoutFragment logoutFragment = new LogoutFragment();
        logoutFragment.setTargetFragment(fragment_profile.this, 2);
        logoutFragment.show(getParentFragmentManager(), "LOGOUT_FRAGMENT");
    }

    @Override
    public void logout() {
        sharedPreferences.edit().clear().apply();
        gotoIntent(LandingActivity.class);
    }

    private void gotoIntent(Class classTarget) {
        Intent intent = new Intent(getContext(), classTarget);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}