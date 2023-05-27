package com.projects4.aqm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.projects4.aqm.controller.RoomDaoImplementation;
import com.projects4.aqm.dto.Room;

import java.sql.SQLException;
import java.util.Objects;

public class UpdateRoom extends AppCompatActivity {

    TextInputEditText title;
    TextView ok;

    String id, text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        RoomDaoImplementation impl = new RoomDaoImplementation();

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        text = intent.getStringExtra("title");

        title = findViewById(R.id.title);
        ok = findViewById(R.id.proceed);
        ok.setOnClickListener(view -> {
            try {
                impl.update(new Room(id, Objects.requireNonNull(title.getText()).toString()));
                startActivity(new Intent(this, FloorsList.class));
            }
            catch (SQLException e) {
                Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        title.setText(text);
    }
}
