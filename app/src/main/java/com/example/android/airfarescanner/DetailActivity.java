package com.example.android.airfarescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailActivityFragment())
                    .commit();
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailActivityFragment extends Fragment {
        private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
        public static final String FORECAST_SHARE_HASHTAG = "#SunshineApp";
        private String forecastStr;

        public DetailActivityFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();
            Bundle extras = intent.getExtras();
            tripPojo trip = new tripPojo();
            if(extras != null) {
                trip = (tripPojo)extras.getSerializable("detailTrip");
                //forecastStr = t.getOrigin() + " "+ t.getDestination()+ " "+t.getPrice();
            }
            int count= 0;
            LinearLayout reLayout = (LinearLayout) rootView.findViewById(R.id.fragmentDetail);
            ArrayList<sliceInfo> slice = trip.getSlice_info();
            for(int i = 0; i <slice.size();i++) {
                ArrayList<segInfo> seg_info = slice.get(i).getSeg_info();
                for(int j = 0; j < seg_info.size();j++) {
                    ArrayList<legInfo> leg = seg_info.get(j).getLeg_info();
                    for(int m = 0;m <leg.size();m++) {
                        LinearLayout lilayout = new LinearLayout(getActivity());
                        lilayout.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                        lilayout.setLayoutParams(params);

                        TextView flightCarrier = new TextView(getActivity());
                        flightCarrier.setText(seg_info.get(j).getFlightCarrier());
                        reLayout.addView(flightCarrier);

                        TextView departTime = new TextView(getActivity());
                        /*departTime.setHeight(49);
                        departTime.setWidth(52);*/
                        //departTime.set
                        departTime.setPadding(2, 2, 100, 2);
                        departTime.setTextAppearance(getActivity(), android.R.style.TextAppearance_Small);;
                        departTime.setText(leg.get(m).getDepartTime());
                        lilayout.addView(departTime);

                        TextView departAirport = new TextView(getActivity());
                        /*departAirport.setHeight(45);
                        departAirport.setWidth(48);*/
                        departAirport.setPadding(2, 2, 100, 2);

                        departAirport.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);;
                        departAirport.setText(leg.get(m).getOrigin());
                        lilayout.addView(departAirport);

                        TextView travelTime = new TextView(getActivity());
                        travelTime.setPadding(2, 2, 100, 2);
                        travelTime.setTextAppearance(getActivity(), android.R.style.TextAppearance_Small);;

                        int duration = Integer.parseInt(leg.get(m).getDurationLeg());
                        String travlTimeDuration = String.valueOf(duration/60) + " h "+ String.valueOf(duration%60) +" m";
                        travelTime.setText(travlTimeDuration);
                        lilayout.addView(travelTime);


                        TextView arrivalAirport = new TextView(getActivity());
                        /*arrivalAirport.setHeight(67);
                        arrivalAirport.setWidth(43);*/
                        arrivalAirport.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);;
                        arrivalAirport.setPadding(2, 2, 100, 2);
                        arrivalAirport.setText(leg.get(m).getDest());
                        lilayout.addView(arrivalAirport);

                        TextView arrivalTime = new TextView(getActivity());
                        arrivalTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT ));
                        arrivalTime.setText(leg.get(m).getArrivalTime());
                        lilayout.addView(arrivalTime);

                        reLayout.addView(lilayout);
                        count++;
                    }
                    int connection = seg_info.get(j).getConnectionDuration();
                    if (connection > 0) {
                        TextView connectionDuration = new TextView(getActivity());
                        String connection_Duration = (connection/60) + " h " +(connection%60) + " m ";
                        connectionDuration.setText(connection_Duration);
                        reLayout.addView(connectionDuration);
                    }

                }

            }
            Log.e("detail activity", String.valueOf(count));


            /*TextView forecastDetail = new TextView(getActivity());
            forecastDetail.setText(trip.getPrice());
            LinearLayout lilayout = new LinearLayout(getActivity());
            lilayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 67);
            lilayout.setLayoutParams(params);
            lilayout.addView(forecastDetail);
            reLayout.addView(lilayout);*/
            return rootView;
        }

        /*@Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);

            // Retrieve the share menu item
            MenuItem menuItem = menu.findItem(R.id.action_share);

            // Get the provider and hold onto it to set/change the share intent.
            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            if (mShareActionProvider != null ) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }

        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, forecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;

        }*/
    }
}