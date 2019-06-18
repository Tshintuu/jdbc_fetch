package com.wildcodeschool.wizards.entities;

public class School {
    private int id;
    private String name;
    private Integer capacity;
    private String country;

    public School(int id, String name, Integer capacity, String country) {
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

    public Integer getCapacity() {
        return this.capacity;
    }

    public String getCountry() {
        return this.country;
    }
}