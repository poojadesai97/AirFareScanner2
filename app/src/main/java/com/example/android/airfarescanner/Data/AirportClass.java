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
    @JsonField(name="icao")
    String icao;
    @SerializedName("iata")
    @JsonField(name="iata")
    private String Airport_code;

    @SerializedName("name")
    @JsonField(name="name")
    private String Name;
    @SerializedName("continent")
    @JsonField(name="continent")
    private String continent;
    @SerializedName("iso")
    @JsonField(name="iso")
    private String iso;
    @SerializedName("status")
    @JsonField(name="status")
    private int status;

    @SerializedName("lat")
    @JsonField(name="lat")
    private String Longitude;
    @SerializedName("lon")
    @JsonField(name="lon")
    private String Latitude;
    @SerializedName("size")
    @JsonField(name="size")
    private String size;

    public AirportClass() {

    }

    public AirportClass(String icao, String airport_code, String name, String continent, String iso, int status, String longitude, String latitude, String size) {
        this.icao = icao;
        Airport_code = airport_code;
        Name = name;
        this.continent = continent;
        this.iso = iso;
        this.status = status;
        Longitude = longitude;
        Latitude = latitude;
        this.size = size;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getAirport_code() {
        return Airport_code;
    }

    public void setAirport_code(String airport_code) {
        Airport_code = airport_code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
