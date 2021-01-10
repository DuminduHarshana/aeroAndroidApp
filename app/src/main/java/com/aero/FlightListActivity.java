package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FlightListActivity extends AppCompatActivity {
    private String TAG = "Dumindu";
    Dialog myDialog;
    private ProgressDialog pDialog;
    private ListView lv;
    ArrayList<HashMap<String, String>> FlightList;

    // URL to get contacts JSON
    private static String url = "http://192.168.56.1:8080/api/v1/flights";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        FlightList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new GetFlights().execute();

    }

    private class GetFlights extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(FlightListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {

                try {

                    JSONArray jsonArr = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonArr.length(); i++) {

                        JSONObject c = jsonArr.getJSONObject(i);

                        String flightId = c.getString("flightId");
                        String flightNumber = c.getString("flightNumber");
                        JSONObject departureAirport = c.getJSONObject("departureAirport");
                        String airportId = departureAirport.getString("airportId");
                        String airportCode = departureAirport.getString("airportCode");
                        String airportName = departureAirport.getString("airportName");
                        String city = departureAirport.getString("city");
                        String state = departureAirport.getString("state");
                        String country = departureAirport.getString("country");
                        JSONObject destinationAirport = c.getJSONObject("destinationAirport");
                        String dairportId = destinationAirport.getString("airportId");
                        String dairportCode = destinationAirport.getString("airportCode");
                        String dairportName = destinationAirport.getString("airportName");
                        String dcity = destinationAirport.getString("city");
                        String dstate = destinationAirport.getString("state");
                        String dcountry = destinationAirport.getString("country");
                        String departureDate = c.getString("departureDate");
                        String arrivalDate = c.getString("arrivalDate");
                        String departureTime = c.getString("departureTime");
                        String arrivalTime = c.getString("arrivalTime");
                        String flightCharge = c.getString("flightCharge");
                        JSONArray passengers = c.getJSONArray("passengers");
                        // tmp hash map for single contact
                        HashMap<String, String> flight = new HashMap<>();

                        // adding each child node to HashMap key => value
                        flight.put("flightId", flightId);
                        flight.put("flightNumber", flightNumber);
                        flight.put("airportCode", airportCode);
                        flight.put("dairportCode", dairportCode);
                        // adding contact to contact list
                        FlightList.add(flight);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                FlightListActivity.this, FlightList,
                R.layout.list_item, new String[]{"flightId", "flightNumber",
                "airportCode", "dairportCode"}, new int[]{R.id.flightid,
                R.id.flightnumber, R.id.airportcode, R.id.dairportcode});

            lv.setAdapter(adapter);


            //event for click

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String TempListView= FlightList.get(position).toString();

                    Intent intent = new Intent(FlightListActivity.this,ListDetail.class);
                    intent.putExtra("Listviewclickvalue",TempListView);
                    startActivity(intent);
                }
            });

            }
        }


    }

