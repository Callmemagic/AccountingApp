package com.joe.accountingapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joe.accountingapp.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        settingsViewModel.getFuncName().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //記得加
                recyclerView.setHasFixedSize(true); //是否有固定大小

                SettingAdapter settingAdapter = new SettingAdapter(getContext(), strings);
                recyclerView.setAdapter(settingAdapter);
            }
        });
        return root;
    }
}