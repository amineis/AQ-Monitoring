package com.projects4.aqm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomMonitor extends AppCompatActivity {

    TextView title_field, co2_field, temp_field, hum_field, light_field, perc_field, comment;
    ImageView prev;
    FirebaseDatabase db;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        // ProgressBar
        pb = findViewById(R.id.progress_circular);

        // Back Button
        prev = findViewById(R.id.back);
        prev.setOnClickListener(view -> finish());

        // Fields in the Layout
        co2_field = findViewById(R.id.co2_value);
        temp_field = findViewById(R.id.temp_value);
        hum_field = findViewById(R.id.hum_value);
        light_field = findViewById(R.id.lux_value);
        perc_field = findViewById(R.id.perc_value);
        comment = findViewById(R.id.comment_value);

        // Title
        title_field = findViewById(R.id.title_value);
        title_field.setText(getIntent().getStringExtra("title"));

        // Getting Data from Firebase Realtime Database
        db = FirebaseDatabase.getInstance();
        DatabaseReference carb = db.getReference("Co2Level"),
                hum = db.getReference("humidity"),
                temp = db.getReference("temperature"),
                lux = db.getReference("light");

        // Listeners
        carb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Co2Level
                String value = dataSnapshot.getValue(String.class);
                assert value != null;
                double val = Double.parseDouble(value);
                value = (((int) val < 1000) ? String.valueOf((int) val) : "1000+");
                co2_field.setText(value);

                // AQI %
                double progress = val / 1200;
                int progressPercentage = (int) (progress * 100);
                pb.setProgress(progressPercentage);
                String perc = progressPercentage + " %";
                perc_field.setText(perc);

                // Comment_Val
                String message = (progressPercentage < 67) ? "THE AIR QUALITY IS GOOD" :
                        (progressPercentage < 84) ? "THE AIR QUALITY IS MODERATE" : "THE AIR QUALITY IS BAD";
                comment.setText(message);

                // Measures taken
                LayerDrawable layerDrawable = (LayerDrawable) pb.getProgressDrawable();
                if (progressPercentage < 67) {
                    comment.setTextColor(Color.rgb(0, 100, 0));
                    layerDrawable.getDrawable(1)
                            .setColorFilter(Color.rgb(0, 100, 0), PorterDuff.Mode.SRC_IN);
                }
                else if (progressPercentage < 84) {
                    comment.setTextColor(Color.rgb(255, 215, 0));
                    layerDrawable.getDrawable(1)
                            .setColorFilter(Color.rgb(255, 215, 0), PorterDuff.Mode.SRC_IN);
                }
                else {
                    showBuzzingNotification(perc);
                    comment.setTextColor(Color.rgb(139, 0, 0));
                    layerDrawable.getDrawable(1)
                            .setColorFilter(Color.rgb(139, 0, 0), PorterDuff.Mode.SRC_IN);
                }
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
    }

    private void showBuzzingNotification(String perc) {
        // Create a notification channel
        String channelId = "buzzing_channel";
        CharSequence channelName = "Buzzing Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelName, importance);
        }

        // Configure additional channel settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.enableVibration(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setVibrationPattern(new long[] { 0, 500, 200, 500 });
        }

        // Register the notification channel with the system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification builder
        String message = "AQI : " + perc;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.co2)
                .setContentTitle("Attention Needed!")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false);

        // Set vibration pattern
        long[] vibrationPattern = {0, 500, 200, 500};
        builder.setVibrate(vibrationPattern);

        // Create a unique notification ID
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }

}