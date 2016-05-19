package com.example.android.airfarescanner;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {
private EditText departDatetxt;
    private EditText arriveDatetxt;

    private DatePickerDialog departDatePickerDialog;
    private DatePickerDialog arriveDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    Button search;
    public static final String LOG_TAG = "AirFareScanner";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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
                R.array.number, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        adultno.setAdapter(adapter1);

        Spinner childno = (Spinner) findViewById(R.id.childCount);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.number, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        childno.setAdapter(adapter2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOG_TAG, "Search Clicked");
                Intent intent = new Intent(MainActivity.this, ResultMainActivity.class);
                startActivity(intent);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == departDatetxt) {
            departDatePickerDialog.show();
        } else if(view == arriveDatetxt) {
            arriveDatePickerDialog.show();
        }
    }


}
