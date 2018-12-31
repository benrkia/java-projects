package com.benrkia.market.address;

import java.util.ArrayList;
import java.util.Collection;

public class Country {

    private long id;
    private String name;
    Collection<City> cities;

    public Country(long id, String name) {
        this.id = id;
        this.name = name;
        this.cities = new ArrayList<>();
    }

    public Country(String name) {
        this.name = name;
        this.cities = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public void removeCity(City city){
        cities.remove(city);
    }

    public Collection<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
