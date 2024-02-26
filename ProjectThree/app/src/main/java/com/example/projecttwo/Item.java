package com.example.projecttwo;

/**
 * Represents an item in the inventory, including its database ID, name, and count.
 * Author: Scott Martel
 * Date: 02/19/2024
 */
public class Item {
    private int id; // Unique identifier for the item in the database
    private String name;
    private int count;

    // Constructor without id, useful when creating a new item that hasn't been saved to the database yet
    public Item(String name, int count) {
        this.name = name;
        this.count = count;
    }

    // Constructor with id, useful when fetching items from the database
    public Item(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
