package com.projects4.aqm.dto;

public class Room {
    // Properties
    private String id;
    private String title;

    // Getters & Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Constructor
    public Room(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
