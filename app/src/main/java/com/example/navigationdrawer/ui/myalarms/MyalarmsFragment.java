package com.example.navigationdrawer.ui.myalarms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationdrawer.R;

public class MyalarmsFragment extends Fragment {

    private MyalarmsViewModel myalarmsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myalarmsViewModel =
                ViewModelProviders.of(this).get(MyalarmsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myalarms, container, false);
//        //final TextView textView = root.findViewById(R.id.myalarms);
//        myalarmsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
       return root;
    }
}

