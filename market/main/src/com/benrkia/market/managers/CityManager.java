package com.benrkia.market.managers;

import com.benrkia.market.address.City;
import com.benrkia.market.dao.CityDAO;

import java.util.Collection;

public class CityManager{

    private CityDAO cityDAO = CityDAO.getInstance();


    public City get(long id) {
        return cityDAO.get(id);
    }

    public Collection<City> getAll() {
        return cityDAO.getAll();
    }

    public int add(City city) {
        return cityDAO.add(city);
    }
}
