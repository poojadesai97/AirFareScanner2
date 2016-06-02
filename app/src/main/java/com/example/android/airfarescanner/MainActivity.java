package com.example.android.airfarescanner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;

import android.net.Uri;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.bluelinelabs.logansquare.LoganSquare;
import com.example.android.airfarescanner.Data.AirportClass;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
    private EditText departDatetxt;
    private EditText arriveDatetxt;

    private DatePickerDialog departDatePickerDialog;
    private DatePickerDialog arriveDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView fromAirport;
    private EditText toAirport;
    private Spinner adultsCount;
    private Spinner childrensCount;
    private Spinner travelClass;
    searchPojo searchObject;
    Button Clear;
    EditText text;
    private String JsonToParse;
    List<AirportClass> Airports;
    Button search;
    public static final String LOG_TAG = "AirFareScanner";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JsonToParse = readJson();

        try {
            Airports = LoganSquare.parseList(JsonToParse, AirportClass.class);
            //for(int i=0;i<Airports.size();i++){
               // Log.d(LOG_TAG,"Airports" + Airports);
            //}

        } catch (IOException e) {
            e.printStackTrace();
        }
        // AirportAdapter adapter=new AirportAdapter(getApplicationContext(),R.layout.activity_main,Airports)


Log.e(LOG_TAG,String.valueOf(Airports.size()));
List<String> airports_Names = new ArrayList<String>();
        for(AirportClass a : Airports) {
            if (a.getName() !=null && !a.getName().isEmpty())
            airports_Names.add(a.getName() + " - " +a.getAirport_code());
        }
        Log.e(LOG_TAG,String.valueOf(airports_Names));




    setContentView(R.layout.activity_main);

    Clear=(Button)

    findViewById(R.id.button1);

    text=(EditText)

    findViewById(R.id.arriveDateText);

    Clear.setOnClickListener(new

    OnClickListener() {

        @Override
        public void onClick (View arg0){
            // TODO Auto-generated method stub
            text.setText("");
            Toast t = Toast.makeText(getApplicationContext(),
                    "Date Cleared",
                    Toast.LENGTH_SHORT);
            t.show();

        }
    }

    );
    fromAirport=(AutoCompleteTextView)

    findViewById(R.id.fromAirport);

    /*fromAirport.setFilters(new InputFilter[]

    {
        new InputFilter.AllCaps()
    }

    );
*/
        ArrayAdapter<String> adapterNames = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,airports_Names);
        fromAirport.setAdapter(adapterNames);

    toAirport=(EditText)

    findViewById(R.id.toAirport);

    toAirport.setFilters(new InputFilter[]

    {
        new InputFilter.AllCaps()
    }

    );

    adultsCount=(Spinner)

    findViewById(R.id.adultsCount);

    childrensCount=(Spinner)

    findViewById(R.id.childCount);

    travelClass=(Spinner)

    findViewById(R.id.spinner);

    dateFormatter=new

    SimpleDateFormat("yyyy-MM-dd",Locale.US);

    findViewsById();

    setDateTimeField();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
    search=(Button)

    findViewById(R.id.button);

    Log.e(LOG_TAG,"Main Activity");


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
    search.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        String from = fromAirport.getText().toString();
            String airportName [] = from.split(" - ");
            from = airportName[1];
            Log.e(LOG_TAG, from);
        String to = toAirport.getText().toString();
        String arriveDate = arriveDatetxt.getText().toString();
        String departDate = departDatetxt.getText().toString();
        int adult = Integer.parseInt(adultsCount.getSelectedItem().toString());
        int children = Integer.parseInt(childrensCount.getSelectedItem().toString());
        String cabin = travelClass.getSelectedItem().toString();


        Log.e(LOG_TAG, "Search Clicked");
        Log.e(LOG_TAG, "From " + from);
        Log.e(LOG_TAG, "To " + toAirport.getText());
        Log.e(LOG_TAG, "Adult Count :" + adultsCount.getSelectedItem().toString());
        Log.e(LOG_TAG, "Child Count : " + childrensCount.getSelectedItem().toString());
        Log.e(LOG_TAG, "Depart Date : " + departDatetxt.getText());
        Log.e(LOG_TAG, "Arrive Date :" + arriveDatetxt.getText());

        if (from.isEmpty())
            Toast.makeText(getApplicationContext(), "Invalid Departure Airport", Toast.LENGTH_LONG).show();
        else if (to.isEmpty())
            Toast.makeText(getApplicationContext(), "Invalid Detination Airport ", Toast.LENGTH_LONG).show();
        else if (departDate.isEmpty())
            Toast.makeText(getApplicationContext(), "Please select Departure Date", Toast.LENGTH_LONG).show();
        else {
            searchObject = new searchPojo(from, to, departDate, arriveDate, adult, children, cabin);
            FetchAirFareTask airfareTask = new FetchAirFareTask(MainActivity.this);
            airfareTask.execute();
        }
    }
    }

    );
    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    client=new GoogleApiClient.Builder(this).

    addApi(AppIndex.API)

    .

    build();
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

                departDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                departDatetxt.setText(dateFormatter.format(newDate.getTime()));
                try {
                    arriveDatePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);

                            arriveDatetxt.setText(dateFormatter.format(newDate.getTime()));
                        }
                    }, year, monthOfYear, dayOfMonth);
                    arriveDatePickerDialog.getDatePicker().setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse(departDatetxt.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }


        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



       /* arriveDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                arriveDatetxt.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/
    }



    @Override
    public void onClick(View view) {
        if (view == departDatetxt) {
            departDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            departDatePickerDialog.show();
        } else if (view == arriveDatetxt) {
            //arriveDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            arriveDatePickerDialog.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.airfarescanner/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.airfarescanner/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class FetchAirFareTask extends AsyncTask<String, Void, ArrayList<tripPojo>> {
        public static final String LOG_TAG = "fetchairfaretask";

        private ProgressDialog progress;
        public FetchAirFareTask(MainActivity activity) {
            progress = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute() {
            progress.setMessage("Fetching Data... Please Wait");
            progress.show();

            Log.d(LOG_TAG, "onPreExecute called");
        }

        @Override
        protected ArrayList<tripPojo> doInBackground(String... params) {
            ArrayList<tripPojo> tripList;
            tripList = new ArrayList<tripPojo>();







            // adding each child node to HashMap key => value


            String APPLICATION_NAME = "MyFlightApplication";
            final String API_KEY = "";
            HttpTransport httpTransport;
            JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
            try {
                //httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                Log.e(LOG_TAG, "commented");
                httpTransport = AndroidHttp.newCompatibleTransport();
                PassengerCounts passengers = new PassengerCounts();
                passengers.setAdultCount(searchObject.getAdultCount());
                passengers.setChildCount(searchObject.getChildrenCount());

                List<SliceInput> slices = new ArrayList<SliceInput>();
                SliceInput slice = new SliceInput();
                slice.setOrigin(searchObject.getFromAirport());
                slice.setDestination(searchObject.getDestinationAirport());
                slice.setDate(searchObject.getDepartDate());
                slice.setPreferredCabin(searchObject.getTravelClass());
                slices.add(slice);

                if (!searchObject.getArriveDate().isEmpty()) {
                    slice = new SliceInput();
                    slice.setOrigin(searchObject.getDestinationAirport());
                    slice.setDestination(searchObject.getFromAirport());
                    slice.setDate(searchObject.getArriveDate());
                    slice.setPreferredCabin(searchObject.getTravelClass());
                    slices.add(slice);
                }
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

                String id;


                for (int i = 0; i < tripResults.size(); i++) {
                    tripPojo trip = new tripPojo();
                    double price = 0;
                    System.out.println("Trip Size: " + tripResults.size());
                    //Trip Option ID
                    id = tripResults.get(i).getId();
                    System.out.println("id " + id);
                    trip.setId(id);
                    //Pricing
                    List<PricingInfo> priceInfo = tripResults.get(i).getPricing();
                    for (int p = 0; p < priceInfo.size(); p++) {
                        String priceString[] = priceInfo.get(p).getSaleTotal().split("USD");
                        double personPrice = Double.parseDouble(priceString[1]);
                        if (p == 0) {
                            personPrice *= searchObject.getAdultCount();
                        } else {
                            personPrice *= searchObject.getChildrenCount();
                        }

                        price += personPrice;

                    }
                    System.out.println("Price " + price);
                    Double truncatedprice = new BigDecimal(price)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();

                    trip.setPrice(String.valueOf(truncatedprice));
                    trip.setOrigin(searchObject.getFromAirport());
                    trip.setDestination(searchObject.getDestinationAirport());
                    //Slice
                    List<SliceInfo> sliceinfo = tripResults.get(i).getSlice();
                    ArrayList<sliceInfo> slice_info = new ArrayList<sliceInfo>();
                    System.out.println("Slices Size: " + sliceinfo.size());
                    int totalDuration = 0;
                    for (int j = 0; j < sliceinfo.size(); j++) {
                        sliceInfo sInfo = new sliceInfo();

                        String duration = sliceinfo.get(j).getDuration().toString();
                        System.out.println("duration " + duration);
                        sInfo.setDuration(duration);
                        totalDuration += Integer.parseInt(duration);
                        sInfo.setTravelTime("" + Integer.parseInt(duration) / 60 + " h " + Integer.parseInt(duration) % 60 + " m");
                        List<SegmentInfo> seginfo = sliceinfo.get(j).getSegment();
                        ArrayList<segInfo> seg_info = new ArrayList<segInfo>();
                        for (int k = 0; k < seginfo.size(); k++) {
                            segInfo seInfo = new segInfo();
                            String bookingCode = seginfo.get(k).getBookingCode();
                            System.out.println("bookingCode " + bookingCode);
                            seInfo.setBookingCode(bookingCode);
                            Integer connectionDuration = seginfo.get(k).getConnectionDuration();
                            if (connectionDuration != null)
                                seInfo.setConnectionDuration(connectionDuration);
                            FlightInfo flightInfo = seginfo.get(k).getFlight();
                            String flightNum = flightInfo.getNumber();
                            System.out.println("flightNum " + flightNum);
                            seInfo.setFlightNum(flightNum);
                            String flightCarrier = flightInfo.getCarrier();
                            System.out.println("flightCarrier " + flightCarrier);
                            seInfo.setFlightCarrier(flightCarrier);

                            List<LegInfo> leg = seginfo.get(k).getLeg();
                            ArrayList<legInfo> leg_info = new ArrayList<legInfo>();
                            for (int l = 0; l < leg.size(); l++) {
                                legInfo lInfo = new legInfo();
                                String aircraft = leg.get(l).getAircraft();
                                System.out.println("aircraft " + aircraft);
                                lInfo.setAircraft(aircraft);
                                String arrivalTime = leg.get(l).getArrivalTime();
                                String dateTime[] = arrivalTime.split("T");
                                String arrivaltime[];
                                if (dateTime[1].contains("+")) {
                                    arrivaltime = dateTime[1].split("\\+");
                                } else {
                                    arrivaltime = dateTime[1].split("\\-");
                                }
                                System.out.println("arrivalTime " + arrivaltime[0]);
                                lInfo.setArrivalTime(arrivaltime[0]);
                                String departDateTime = leg.get(l).getDepartureTime();
                                dateTime = departDateTime.split("T");
                                String departtime[];
                                if (dateTime[1].contains("+")) {
                                    departtime = dateTime[1].split("\\+");
                                } else {
                                    departtime = dateTime[1].split("\\-");
                                }

                                lInfo.setDepartTime(departtime[0]);
                                System.out.println("departTime " + departtime[0]);
                                String dest = leg.get(l).getDestination();
                                lInfo.setDest(dest);
                                System.out.println("Destination " + dest);
                                String destTer = leg.get(l).getDestinationTerminal();
                                lInfo.setDestTer(destTer);
                                System.out.println("DestTer " + destTer);
                                String origin = leg.get(l).getOrigin();
                                lInfo.setOrigin(origin);
                                System.out.println("origun " + origin);
                                String originTer = leg.get(l).getOriginTerminal();
                                lInfo.setOriginTer(originTer);
                                System.out.println("OriginTer " + originTer);
                                int durationLeg = leg.get(l).getDuration();
                                lInfo.setDurationLeg(String.valueOf(durationLeg));
                                System.out.println("durationleg " + durationLeg);
                                int mil = leg.get(l).getMileage();
                                lInfo.setMil(String.valueOf(mil));
                                System.out.println("Milleage " + mil);
                                leg_info.add(lInfo);

                            }
                            seInfo.setLeg_info(leg_info);
                            seg_info.add(seInfo);

                        }
                        sInfo.setSeg_info(seg_info);
                        slice_info.add(sInfo);

                    }
                    trip.setSlice_info(slice_info);
                    trip.setTotalDuration(String.valueOf(totalDuration));
                    tripList.add(trip);


                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return tripList;

        }

        @Override
        protected void onPostExecute(ArrayList<tripPojo> list) {
            super.onPostExecute(list);
            if(progress.isShowing())
                progress.dismiss();

            Intent intent = new Intent(MainActivity.this, ResultMainActivity.class);
            intent.putExtra("List", list);
            startActivity(intent);

            Log.d(LOG_TAG, "onPostExecute called");

        }
    }
    private String readJson() {
        return readFile("airports.json");
    }

    private List<String> readJsonFromFile() {

        List<String> strings = new ArrayList<>();
        strings.add(readFile("airports.json"));

        return strings;
    }
    private String readFile(String filename) {
        StringBuilder sb = new StringBuilder();


        try {
            InputStream json = getAssets().open(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));


            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }


            in.close();
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("The JSON file was not able to load properly. These tests won't work until you completely kill this demo app and restart it.")
                    .setPositiveButton("OK", null)
                    .show();
        }


        return sb.toString();
    }


}
