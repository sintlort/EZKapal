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

import com.ezcats.ezkapal.Activity.LandingActivity;
import com.ezcats.ezkapal.Activity.RiwayatTransaksiActivity;
import com.ezcats.ezkapal.Activity.TransaksiTerkiniActivity;
import com.ezcats.ezkapal.Fragment.LogoutFragment;
import com.ezcats.ezkapal.Fragment.PenumpangFragment;

public class fragment_profile extends Fragment implements LogoutFragment.LogoutListener {
    CardView transaksiTerkini, transaksiRiwayat, hubungi, logout;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        transaksiTerkini = v.findViewById(R.id.transaksi_aktif);
        transaksiRiwayat = v.findViewById(R.id.riwayat_transaksi);
        hubungi = v.findViewById(R.id.hubungi_kami);
        logout = v.findViewById(R.id.logout);

        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);

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

        return v;
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