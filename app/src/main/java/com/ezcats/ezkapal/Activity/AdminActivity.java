package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.databinding.ActivityAdminBinding;
import com.ezcats.ezkapal.fragment_profile;
import com.ezcats.ezkapal.fragment_ticket;
import com.ezcats.ezkapal.home_fragment_without_shimmer;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding activityAdminBinding;
    String token, name, email, number;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityAdminBinding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(activityAdminBinding.getRoot());

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preference), Context.MODE_PRIVATE);
        String type_account = sharedPreferences.getString(getString(R.string.type_account), "");
        token = sharedPreferences.getString(getString(R.string.token), "");
        name = sharedPreferences.getString(getString(R.string.name_shared_preference), "");
        email = sharedPreferences.getString(getString(R.string.email_shared_preference), "");
        number = sharedPreferences.getString(getString(R.string.email_shared_preference), "");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayoutID, new home_fragment_without_shimmer());
        ft.addToBackStack(null);
        ft.commit();

        //com.ezcats.ezkapal.databinding.ActivityMainBinding.inflate(getLayoutInflater());
    }
}