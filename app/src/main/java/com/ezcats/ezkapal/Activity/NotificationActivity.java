package com.ezcats.ezkapal.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ezcats.ezkapal.Adapter.ViewPageAdapter;
import com.ezcats.ezkapal.Fragment.ArchivedNotification;
import com.ezcats.ezkapal.Fragment.CurrentNotificationFragment;
import com.ezcats.ezkapal.R;
import com.ezcats.ezkapal.ticket_kapal;
import com.ezcats.ezkapal.ticket_speedboat;
import com.google.android.material.tabs.TabLayout;

public class NotificationActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        viewPager = (ViewPager) findViewById(R.id.view_pager_notification);
        tabLayout = (TabLayout) findViewById(R.id.tab_notification);

        viewPageAdapters = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapters.addFragment(new CurrentNotificationFragment(), "Notifikasi");
        viewPageAdapters.addFragment(new ArchivedNotification(), "Arsip Notifikasi");
        viewPager.setAdapter(viewPageAdapters);

        tabLayout.setupWithViewPager(viewPager);
    }
}