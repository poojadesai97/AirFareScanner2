package com.example.android.airfarescanner.Data;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anisha on 5/29/2016.
 */

@JsonObject(serializeNullCollectionElements = true, serializeNullObjects = true)
public class AirportClass {

    @SerializedName("icao")
    @JsonField(name = "icao")
    String Airport_code;
    @SerializedName("iata")
    @JsonField(name = "iata")
    private String iata;

    @SerializedName("name")
    @JsonField(name = "name")
    private String Name;
    @SerializedName("city")
    @JsonField(name = "city")
    private String city;
    @SerializedName("country")
    @JsonField(name = "country")
    private String country;
    @SerializedName("elevation")
    @JsonField(name = "elevation")
    private int elevation;

    @SerializedName("lat")
    @JsonField(name = "lat")
    private double Longitude;
    @SerializedName("lon")
    @JsonField(name = "lon")
    private double Latitude;
    @SerializedName("tz")
    @JsonField(name = "tz")
    private String tz;

    public AirportClass() {

    }

    public AirportClass(String airport_code, String iata, String name, String city, String country, int elevation, double longitude, double latitude, String tz) {
        Airport_code = airport_code;
        this.iata = iata;
        Name = name;
        this.city = city;
        this.country = country;
        this.elevation = elevation;
        Longitude = longitude;
        Latitude = latitude;
        this.tz = tz;
    }

    public String getAirport_code() {
        return Airport_code;
    }

    public void setAirport_code(String airport_code) {
        Airport_code = airport_code;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
}
