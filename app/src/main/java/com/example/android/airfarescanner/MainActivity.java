package com.example.android.airfarescanner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.FlightInfo;
import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.PricingInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
private EditText departDatetxt;
    private EditText arriveDatetxt;

    private DatePickerDialog departDatePickerDialog;
    private DatePickerDialog arriveDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText fromAirport;
    private EditText toAirport;
    private Spinner adultsCount;
    private Spinner childrensCount;
    private Spinner travelClass;
    searchPojo searchObject;

    Button search;
    public static final String LOG_TAG = "AirFareScanner";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromAirport = (EditText)findViewById(R.id.fromAirport);
        fromAirport.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        toAirport = (EditText) findViewById(R.id.toAirport);
        toAirport.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        adultsCount = (Spinner)findViewById(R.id.adultsCount);
        childrensCount = (Spinner)findViewById(R.id.childCount);
        travelClass = (Spinner) findViewById(R.id.spinner);
        dateFormatter=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();

        setDateTimeField();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        search = (Button) findViewById(R.id.button);
        Log.e(LOG_TAG, "Main Activity");


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Spinner adultno = (Spinner) findViewById(R.id.adultsCount);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.adultNumber, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        adultno.setAdapter(adapter1);

        Spinner childno = (Spinner) findViewById(R.id.childCount);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.childNumber, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        childno.setAdapter(adapter2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = fromAirport.getText().toString();
                String to = toAirport.getText().toString();
                String arriveDate = arriveDatetxt.getText().toString();
                String departDate = departDatetxt.getText().toString();
                int adult = Integer.parseInt(adultsCount.getSelectedItem().toString());
                int children = Integer.parseInt(childrensCount.getSelectedItem().toString());
                String cabin = travelClass.getSelectedItem().toString();


                Log.e(LOG_TAG, "Search Clicked");
                Log.e(LOG_TAG, "From " + fromAirport.getText() );
                Log.e(LOG_TAG, "To " + toAirport.getText());
                Log.e(LOG_TAG, "Adult Count :" + adultsCount.getSelectedItem().toString());
                Log.e(LOG_TAG, "Child Count : "+ childrensCount.getSelectedItem().toString());
                Log.e(LOG_TAG, "Depart Date : " + departDatetxt.getText());
                Log.e(LOG_TAG, "Arrive Date :" + arriveDatetxt.getText());

                if(from.isEmpty())
                    Toast.makeText(getApplicationContext(),"Invalid Departure Airport", Toast.LENGTH_LONG).show();
                else if(to.isEmpty())
                    Toast.makeText(getApplicationContext(),"Invalid Detination Airport ", Toast.LENGTH_LONG).show();
                else if(departDate.isEmpty())
                    Toast.makeText(getApplicationContext(),"Please select Departure Date", Toast.LENGTH_LONG).show();
                else {
                    searchObject = new searchPojo(from, to, departDate, arriveDate, adult, children, cabin);
                    FetchAirFareTask airfareTask = new FetchAirFareTask();
                    airfareTask.execute();

                }


            }
        });
    }



    private void findViewsById() {
        departDatetxt = (EditText) findViewById(R.id.departDateText);
        departDatetxt.setInputType(InputType.TYPE_NULL);
        departDatetxt.requestFocus();

        arriveDatetxt = (EditText) findViewById(R.id.arriveDateText);
        arriveDatetxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        departDatetxt.setOnClickListener(this);
        arriveDatetxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        departDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                departDatetxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        arriveDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                arriveDatetxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void onClick(View view) {
        if(view == departDatetxt) {
            departDatePickerDialog.show();
        } else if(view == arriveDatetxt) {
            arriveDatePickerDialog.show();
        }
    }
    class FetchAirFareTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {
        public static final String LOG_TAG = "fetchairfaretask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(LOG_TAG, "onPreExecute called");
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
            ArrayList<HashMap<String, String>> inboxList;
            inboxList = new ArrayList<HashMap<String, String>>();


            // adding each child node to HashMap key => value


            String APPLICATION_NAME = "MyFlightApplication";
            final String API_KEY = "";
            HttpTransport httpTransport;
            JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
            try {
                //httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                Log.e(LOG_TAG,"commented");
                httpTransport = com.google.api.client.extensions.android.http.AndroidHttp.newCompatibleTransport();
                PassengerCounts passengers = new PassengerCounts();
                passengers.setAdultCount(searchObject.getAdultCount());

                List<SliceInput> slices = new ArrayList<SliceInput>();
                SliceInput slice = new SliceInput();
                slice.setOrigin(searchObject.getFromAirport());
                slice.setDestination(searchObject.getDestinationAirport());
                slice.setDate(searchObject.getDepartDate());
                slices.add(slice);
                TripOptionsRequest request = new TripOptionsRequest();
                request.setSolutions(50);
                request.setPassengers(passengers);
                request.setSaleCountry("US");
                request.setSlice(slices);

                TripsSearchRequest parameters = new TripsSearchRequest();
                parameters.setRequest(request);
                QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
                        .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer("AIzaSyDOU4p-DF9EB6tYKo4-KGRubiYdA76W2h4")).build();

                TripsSearchResponse list = qpXExpress.trips().search(parameters).execute();
                List<TripOption> tripResults = list.getTrips().getTripOption();
                Log.e(LOG_TAG, String.valueOf(tripResults.size()));
                for (int i = 0; i < tripResults.size(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    List<PricingInfo> priceInfo = tripResults.get(i).getPricing();
                    for (int p = 0; p < priceInfo.size(); p++) {
                        String price = priceInfo.get(p).getSaleTotal();
                        map.put("price", price);
                        //System.out.println("Price "+price);
                    }
                    List<SliceInfo> sliceInfo = tripResults.get(i).getSlice();
                    for (int j = 0; j < sliceInfo.size(); j++) {
                        int duration = sliceInfo.get(j).getDuration();
                        map.put("departAirport", searchObject.getFromAirport());
                        map.put("duration", String.valueOf(duration));
                        map.put("travelTime", "" + duration / 60 + " h " + duration % 60 + " m");
                        map.put("arrivalAirport", searchObject.getDestinationAirport());
                        Log.e(LOG_TAG, String.valueOf(duration));
                        List<SegmentInfo> segInfo = sliceInfo.get(j).getSegment();
                        for (int k = 0; k < segInfo.size(); k++) {
                            FlightInfo flightInfo=segInfo.get(k).getFlight();
                            map.put("airline", flightInfo.getCarrier());
                            List<LegInfo> leg=segInfo.get(k).getLeg();
                            String departDateTime=leg.get(0).getDepartureTime();
                            String dateTime[] = departDateTime.split("T");
                            String departtime[] = dateTime[1].split("\\+");

                            map.put("departTime", departtime[0]);
                            String ArrivalTime = leg.get(leg.size()-1).getArrivalTime();
                            dateTime = ArrivalTime.split("T");
                            String arrivaltime[] = dateTime[1].split("\\+");
                            map.put("arrivalTime", arrivaltime[0]);
                            /*for(int l=0; l<leg.size(); l++){
                                String departTime=leg.get(0).getDepartureTime();
                                map.put("departTime", departTime);
                                String ArrivalTime = leg.get(leg.size()-1).getArrivalTime();
                                map.put("arrivalTime", ArrivalTime);
                            }*/
                        }

                    }

                    inboxList.add(map);
                }
            }catch (Exception e) {
                Log.e(LOG_TAG, "Exception" +e.toString());
                e.printStackTrace();
            }
            return inboxList;

        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> list) {
            super.onPostExecute(list);
            Intent intent = new Intent(MainActivity.this, ResultMainActivity.class);
            intent.putExtra("List", list);
            startActivity(intent);
            /*adapter = new SimpleAdapter(
                    getActivity(), list,
                    R.layout.airlines_list, new String[]{"airline", "departAirport", "departTime", "travelTime", "arrivalAirport", "arrivalTime", "price"},
                    new int[]{R.id.airlineText, R.id.departAirport, R.id.departTime, R.id.travelTime, R.id.arrivalAirport, R.id.arrivalTime, R.id.price});

            listView.setAdapter(adapter);
*/
            Log.d(LOG_TAG, "onPostExecute called");

        }
    }


}
