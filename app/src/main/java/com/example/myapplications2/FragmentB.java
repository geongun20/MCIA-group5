package com.example.myapplications2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;


public class FragmentB extends Fragment {

    public FragmentB() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b,null);

        ListView lv = v.findViewById(R.id.listView1);

        Input input = new Input();
        input.readFile();
        List<String> list = input.getData()[input.getMonth()][input.getDay()];
        String[] arr = list.toArray(new String[0]);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adapter);
        return v;
    }
}