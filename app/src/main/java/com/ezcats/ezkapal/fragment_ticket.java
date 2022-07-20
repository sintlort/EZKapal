package com.ezcats.ezkapal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezcats.ezkapal.Adapter.ViewPageAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

public class fragment_ticket extends Fragment {

    private static final String TAG = "TICKET_FRAGMENT";

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager_id);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_ticket);

        viewPageAdapters = new ViewPageAdapter(getChildFragmentManager());
        viewPageAdapters.addFragment(new ticket_kapal(), "Kapal");
        viewPageAdapters.addFragment(new ticket_speedboat(), "Speedboat");
        viewPager.setAdapter(viewPageAdapters);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}