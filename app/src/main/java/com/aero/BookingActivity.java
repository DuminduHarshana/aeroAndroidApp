package com.aero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class BookingActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        final HashMap<String, String> holder = (HashMap<String, String>) getIntent().getSerializableExtra("key");

        String fid = holder.get("flightId");
        textView =(TextView) findViewById(R.id.textView);
        textView.setText(fid);

    }
}