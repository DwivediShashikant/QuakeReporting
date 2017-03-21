package com.example.android.quakereport;

/**
 * Created by Shashikant on 3/21/2017.
 */

public class quake {
    private double magnitude;
    private Long time;
    private String place;

    //constructor for setting up the values for the earthquake
    quake(double magnitude, Long time, String place){
        this.magnitude = magnitude;
        this.time = time;
        this.place = place;
    }
    // function for retrieving magnitude
    public double getMagnitude(){ return magnitude; }
    //function for retrieving time
    public Long getTime(){ return time; }
    // function for retrieving place
    public String getPlace(){ return place; }

}
