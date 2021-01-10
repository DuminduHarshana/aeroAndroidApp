package com.aero;

/**
 * Created by Nipunu on 21,August,2019
 */
public class Flight {


    public String flight_id;
    public String flight_number;

    public String departure_date;
    public String arrival_date;
    public String departure_time;
    public String arrival_time;
    public String flight_charge;

    public Flight(String flight_id, String flight_number, String departure_date, String arrival_date, String departure_time, String arrival_time, String flight_charge) {
        this.flight_id = flight_id;
        this.flight_number = flight_number;

        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.flight_charge = flight_charge;
}
    }
