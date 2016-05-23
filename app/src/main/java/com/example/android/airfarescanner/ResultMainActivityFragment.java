package com.example.android.airfarescanner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class ResultMainActivityFragment extends Fragment {
    //ArrayAdapter adapter;
    public ResultMainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview);
        ArrayList<String> arrayl = new ArrayList<String>();
        searchPojo searchObject = (searchPojo) getActivity().getIntent().getSerializableExtra("searchPojo");
        HashMap<String, String> map = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> inboxList;
        inboxList = new ArrayList<HashMap<String, String>>();

        // adding each child node to HashMap key => value
        map.put("departAirport", searchObject.getFromAirport());
        map.put("departTime", searchObject.getDepartDate());
        map.put("travelTime", "24 hrs" );
        map.put("arrivalAirport", searchObject.getDestinationAirport());
        map.put("arrivalTime", searchObject.getDepartDate());
        inboxList.add(map);
        map = new HashMap<String, String>();

        map.put("departAirport", "maa");
        map.put("departTime", searchObject.getDepartDate());
        map.put("travelTime", "24 hrs" );
        map.put("arrivalAirport", searchObject.getDestinationAirport());
        map.put("arrivalTime", "therila");
        inboxList.add(map);
        arrayl.add(searchObject.getFromAirport());

        arrayl.add("something1");
        //adapter = new ArrayAdapter<String>(getActivity(), R.layout.airlines_list, R.id.item_text, arrayl);
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), inboxList,
                R.layout.airlines_list, new String[] { "departAirport", "departTime", "travelTime", "arrivalAirport", "arrivalTime" },
                new int[] { R.id.departAirport, R.id.departTime, R.id.travelTime, R.id.arrivalAirport, R.id.arrivalTime });
        listView.setAdapter(adapter);

        return rootView;
    }
}
