package com.projects4.aqm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projects4.aqm.controller.RoomDaoImplementation;

import java.sql.SQLException;

public class AddRoom extends AppCompatActivity {
    EditText title;
    TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        title = findViewById(R.id.title);
        ok = findViewById(R.id.proceed);
        ok.setOnClickListener(view -> {
            RoomDaoImplementation impl = new RoomDaoImplementation();
            try {
                impl.insert(title.getText().toString());
                Intent intent = new Intent(this, FloorsList.class);
                startActivity(intent);
            } catch (SQLException e) {
                Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}