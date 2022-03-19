package com.ezcats.ezkapal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.ezcats.ezkapal.Adapter.BeritaAdapter;
import com.ezcats.ezkapal.Model.BeritaModel;
import com.ezcats.ezkapal.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new home_fragment());

        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                replaceFragment(new home_fragment_without_shimmer());
            }
        }.start();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.bot_frag_home:
                    replaceFragment(new home_fragment_without_shimmer());
                    break;
                case R.id.bot_frag_ticket:
                    replaceFragment(new fragment_ticket());
                    break;

                case R.id.bot_frag_profile:
                    replaceFragment(new fragment_profile());
                    break;
            }
            return true;
        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutID, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}