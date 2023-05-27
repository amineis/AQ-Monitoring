package com.projects4.aqm.model;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.projects4.aqm.dto.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Model {
    FirebaseFirestore db;

    public Model(){
        db = FirebaseFirestore.getInstance();
    }

    public List<Room> getAll() {
        List<Room> rooms = new ArrayList<>();
        final boolean[] terminated = new boolean[1];

        db.collection("rooms").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            rooms.add(
                                    new Room(
                                            document.getId(),
                                            Objects.requireNonNull(document.get("title")).toString()
                                    )
                            );
                        }
                        terminated[0] = true;
                    }
                    else
                        Log.d("not ok", "Error getting documents: ", task.getException());
                });

        while (!terminated[0])
            System.out.println("...");

        return rooms;
    }

    public void insert(String title){
        Map<String, Object> r = new HashMap<>();
        r.put("title", title);
        db.collection("rooms")
                .add(r)
                .addOnSuccessListener(documentReference -> Log.w("ok", "Added Successfully"))
                .addOnFailureListener(e -> Log.w("not ok", "Error adding document", e));
    }

    public void update(Room room) {
        db.collection("rooms")
                .document(room.getId())
                .update("title", room.getTitle())
                .addOnSuccessListener(unused -> Log.w("ok", "Updated Successfully"))
                .addOnFailureListener(e -> Log.w("not ok", "Error updating document", e));
    }

    public void delete(String id) {
        db.collection("rooms").document(id).delete();
    }
}
