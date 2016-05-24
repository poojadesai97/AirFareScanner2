package com.example.android.airfarescanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pooja on 15-05-2016.
 */
public class TwoFragment extends Fragment {

    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*if(getArguments()!=null){
            ArrayList<HashMap<String, String>> quickestList= (ArrayList<HashMap<String, String>>)getArguments().getSerializable("quickestList");
            Log.e("Tab2", String.valueOf(quickestList.size()));
            ListAdapter adapter =  new SimpleAdapter(
                    getActivity(), quickestList,
                    R.layout.airlines_list, new String[]{"airline", "departAirport", "departTime", "travelTime", "arrivalAirport", "arrivalTime", "price"},
                    new int[]{R.id.airlineText, R.id.departAirport, R.id.departTime, R.id.travelTime, R.id.arrivalAirport, R.id.arrivalTime, R.id.price});
            ListAdapter.setListAdapter(adapter);
            }*/

        return inflater.inflate(R.layout.result_fragment_main, container, false);
    }
}
