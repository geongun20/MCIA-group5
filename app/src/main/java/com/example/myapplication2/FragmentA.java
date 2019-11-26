package com.example.myapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;


public class FragmentA extends Fragment {

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a,null);
//        TextView tv = v.findViewById(R.id.textView1);
        ListView lv = v.findViewById(R.id.listView1);
        Input test = new Input();
        test.input("sample_data.txt", getContext());

        List<String> temp = test.data2[test.getMonth()][test.getDay()];
        System.out.println(temp.size());
        String[] aaa = temp.toArray(new String[0]);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, aaa);
        lv.setAdapter(adapter);
        return v;
    }
}