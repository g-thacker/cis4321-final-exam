package edu.txstate.get26.finalexam;

import org.json.JSONObject;
import org.json.JSONException;

public class Event {
    private int id;
    private String name;
    private int numberAttending;
    private String room;
    private int capacity;

    public Event() {
    }

    public Event(int id, String name, int numberAttending, String room, int capacity) {
        this.id = id;
        this.name = name;
        this.numberAttending = numberAttending;
        this.room = room;
        this.capacity = capacity;
    }

    public Event(JSONObject object) {
        try {
            this.id = object.getInt("Id");
            this.name = object.getString("Name");
            this.numberAttending = object.getInt("NumOfAttendees");
            this.room = object.getString("Room");
            this.capacity = object.getInt("Capacity");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberAttending() {
        return numberAttending;
    }

    public void setNumberAttending(int numberAttending) {
        this.numberAttending = numberAttending;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.id + " " + this.name;
    }
}
