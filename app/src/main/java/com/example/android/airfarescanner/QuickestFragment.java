package com.example.android.airfarescanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by pooja on 15-05-2016.
 */
public class QuickestFragment extends Fragment {

    String LOG_TAG="QuickestFragment";
    public QuickestFragment() {
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
                    R.layout.cheapest_list_item, new String[]{"airline", "departAirport", "departTime", "travelTime", "arrivalAirport", "arrivalTime", "price"},
                    new int[]{R.id.airlineText, R.id.departAirport, R.id.departTime, R.id.travelTime, R.id.arrivalAirport, R.id.arrivalTime, R.id.price});
            ListAdapter.setListAdapter(adapter);
            }*/
        View rootView = inflater.inflate(R.layout.result_fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview);


        ArrayList<tripPojo> quickest = new ArrayList<tripPojo>();
        Log.e("Get arguments ", ""+getArguments().size());
        if(getArguments() != null) {
            Bundle bundle = this.getArguments();
            quickest = (ArrayList<tripPojo>) bundle.getSerializable("quickest");
            Collections.sort(quickest, new Comparator<tripPojo>(){
                public int compare(tripPojo s1, tripPojo s2) {
                    return s1.getTotalDuration().compareToIgnoreCase(s2.getTotalDuration());
                }
            });

        }




        CustomAdapter adapter = new CustomAdapter(getContext(),R.layout.airlines_list, quickest);

       /* ArrayAdapter adapter;
        ArrayList<String> ql = new ArrayList<String>();
        ql.add("one");
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.airlines_list, R.id.item_text, ql);*/
        listView.setAdapter(adapter);
        return rootView;
    }


}
