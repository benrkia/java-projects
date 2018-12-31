package com.benrkia.market.managers;

import com.benrkia.market.address.City;
import com.benrkia.market.address.Country;
import com.benrkia.market.dao.CountryDAO;

import java.util.Collection;

public class CountryManager{

    private CountryDAO countryDAO = CountryDAO.getInstance();


    public Country get(long id) {
        return countryDAO.get(id);
    }

    public Collection<Country> getAll() {
        return countryDAO.getAll();
    }

    public int add(Country country) {
        return countryDAO.add(country);
    }

    public Collection<City> getCities(Country country){
        return countryDAO.getCities(country);
    }
}
