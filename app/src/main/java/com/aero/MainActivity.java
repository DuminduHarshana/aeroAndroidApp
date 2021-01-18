package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username_edit;
    EditText password_edit;
    String username;
    String password;
    String TAG = "Request";
    Button btnFlightList;
    Button btn_login;
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_edit = findViewById(R.id.username);
        password_edit = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btnFlightList = (Button) findViewById(R.id.button1);

        baseUrl = "http://192.168.56.1:8080/api/v1/login";

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = username_edit.getText().toString();
                password = password_edit.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {

                    Toast.makeText(v.getContext(), "Please Fill Required ", Toast.LENGTH_SHORT).show();

                } else {

                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        baseUrl, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {

                                    if (response.getBoolean("authenticated")) {

                                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                        intent.putExtra("user", response.getString("user"));
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getBaseContext(), "Username or password incorrect!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }


                            }
                        }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());

                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }

//                        Use this when url has parameters
//                        @Override
//                        protected Map<String, String> getParams() {
//
//                            Map<String, String> params = new HashMap<String, String>();
//
//                            params.put("username", username);
//                            params.put("password", password);
//
//                            return params;
//                        }

                        @Override
                        public byte[] getBody() {

                            JSONObject js = new JSONObject();
                            try {
                                js.put("username", username);
                                js.put("password", password);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            return js.toString().getBytes();
                        }
                    };

                    AppController.getInstance().addToRequestQueue(jsonObjReq);
                }

            }

        });

        btnFlightList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, FlightListActivity.class);
                startActivity(i);
            }
        });


    }

}
