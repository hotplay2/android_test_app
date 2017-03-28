package com.example.rafal.shoppinglist;

/**
 * Created by rafal on 24.03.2017.
 */

public class Item {
    private String name;
    private int id;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
