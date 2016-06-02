package com.example.android.airfarescanner.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.airfarescanner.R;

import java.util.ArrayList;

/**
 * Created by anisha on 5/29/2016.
 */
public class AirportAdapter extends BaseAdapter {
    Context context;
    ArrayList<AirportClass> listData;

    public AirportAdapter(Context context, ArrayList<AirportClass> listData) {
        this.context = context;
        this.listData = listData;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        private TextView AirportName;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //  view = inflater.inflate(R.layout.city_item,null);
            viewHolder = new ViewHolder();
            viewHolder.AirportName = (TextView) view.findViewById(R.id.fromAirport);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        AirportClass airportnames = listData.get(position);
        String airportcodename = airportnames.getAirport_code();
        viewHolder.AirportName.setText(airportcodename);
        return view;
    }
}
