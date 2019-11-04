package com.example.myapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentB extends Fragment {

    public FragmentB() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b,null);
//        TextView tv = v.findViewById(R.id.textView1);
        ListView lv = v.findViewById(R.id.listView1);

        String[] data = {"18:32 PM", "14:11 PM", "12:55 PM", "10:40 AM", "10:03 AM", "08:33 AM"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);

        return v;
    }
}