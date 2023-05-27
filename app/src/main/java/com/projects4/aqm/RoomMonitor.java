package com.projects4.aqm;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomMonitor extends AppCompatActivity {

    // Fields
    TextView title_field, co2_field, temp_field, hum_field, light_field, fan_field, occ_field;
    Button btn;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_monitor);

        // Fields in the Layout
        title_field = findViewById(R.id.title);
        co2_field = findViewById(R.id.Co2Level);
        temp_field = findViewById(R.id.temperature);
        hum_field = findViewById(R.id.humidity);
        light_field = findViewById(R.id.light);
        fan_field = findViewById(R.id.isFanEnabled);
        occ_field = findViewById(R.id.occupancy);

        // Button
        btn = findViewById(R.id.detect_occ);

        db = FirebaseDatabase.getInstance();
        DatabaseReference carb = db.getReference("Co2Level");
        DatabaseReference hum = db.getReference("humidity");
        DatabaseReference temp = db.getReference("temperature");
        DatabaseReference lux = db.getReference("light");
        DatabaseReference fan = db.getReference("isFanEnabled");

        carb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                co2_field.setText(value);
                Log.d("DataChange", "Value is: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Cancelled", "Failed to read value.", error.toException());
            }
        });
        hum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                hum_field.setText(value);
                Log.d("DataChange", "Value is: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Cancelled", "Failed to read value.", error.toException());
            }
        });
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                temp_field.setText(value);
                Log.d("DataChange", "Value is: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Cancelled", "Failed to read value.", error.toException());
            }
        });
        lux.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                light_field.setText(value);
                Log.d("DataChange", "Value is: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Cancelled", "Failed to read value.", error.toException());
            }
        });
        fan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                fan_field.setText(value);
                Log.d("DataChange", "Value is: " + value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Cancelled", "Failed to read value.", error.toException());
            }
        });
    }
}