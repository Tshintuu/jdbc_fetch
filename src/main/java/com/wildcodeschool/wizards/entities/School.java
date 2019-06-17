package com.wildcodeschool.wizards.entities;

public class School {
    private int id;
    private String name;
    private int capacity;
    private String country;

    public School(int id, String name, int capacity, String country) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.country = country;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String getCountry() {
        return this.country;
    }
}