package com.projects4.aqm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// import com.ekn.gruzer.gaugelibrary.ArcGauge;
// import com.ekn.gruzer.gaugelibrary.Range;

public class RoomDetails extends AppCompatActivity {
    /* res
    Range range1, range2, range3, range4, range5, range6;
    ArcGauge gauge;
    TextView title_field, co2_field, temp_field, hum_field;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
    /*
        gauge = findViewById(R.id.air_quality_gauge);

        title_field = findViewById(R.id.title);
        co2_field = findViewById(R.id.co2);
        temp_field = findViewById(R.id.temp);
        hum_field = findViewById(R.id.hum);

        //set min max and current value
        gauge.setMinValue(0);
        gauge.setMaxValue(400);
        gauge.setValue(56);

        range1 = new Range();
        range2 = new Range();
        range3 = new Range();
        range4 = new Range();
        range5 = new Range();
        range6 = new Range();

        range1.setFrom(0); range1.setTo(50); range1.setColor(Color.GREEN);
        range2.setFrom(51); range2.setTo(100); range2.setColor(Color.YELLOW);
        range3.setFrom(101); range3.setTo(150); range3.setColor(-39424);
        range4.setFrom(151); range4.setTo(200); range4.setColor(Color.RED);
        range5.setFrom(201); range5.setTo(300); range5.setColor(Color.parseColor("#800080"));
        range6.setFrom(301); range6.setTo(500); range6.setColor(-8388608);
        */
    }

    /*
    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title"),
                Co2 = intent.getStringExtra("CO2"),
                temp = intent.getStringExtra("Temperature"),
                hum = intent.getStringExtra("Humidity");

        title_field.setText(title);
        co2_field.setText("CO2 Level : " + Co2);
        temp_field.setText("Temperature : " + temp);
        hum_field.setText("Humidity : " + hum);
    }
    */
}