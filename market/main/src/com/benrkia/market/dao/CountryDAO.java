package com.benrkia.market.dao;

import com.benrkia.market.address.City;
import com.benrkia.market.address.Country;
import com.benrkia.market.configuration.DBConnection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CountryDAO implements DAO<Country> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`country`";
    public static final String[] FIELDS = {"id", "name"};

    private static CountryDAO countryDAO;
    private CountryDAO(){}
    public static CountryDAO getInstance(){
        if(countryDAO == null)
            countryDAO = new CountryDAO();
        return countryDAO;
    }

    @Override
    public Country get(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Country country = null;

        try {
            if(res.next()){
                country = new Country(res.getLong(FIELDS[0]), res.getString(FIELDS[1]));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

    @Override
    public Collection<Country> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Country> countries = new ArrayList<>();

        try {
            while (res.next()){
                Country country = new Country(res.getLong(FIELDS[0]), res.getString(FIELDS[1]));
                countries.add(country);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countries;
    }

    @Override
    public int add(Country country) {
        if(country != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`name`) " +
                    "VALUES ('" + country.getName() + "')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                country.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Country country) {
        if(country != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`name`='"+country.getName()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Country country) {
        if(country != null)
            update(country.getId(), country);
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id="+id;
        try {
            dbConnection.executeUpdate(sql, false);
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Country country) {
        if(country != null)
            delete(country.getId());
    }

    public Collection<City> getCities(Country country){
        if(country != null){
            String sql = "SELECT * FROM " + CityDAO.TABLE_NAME + " WHERE " + CityDAO.FIELDS[2] + "="+country.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<City> cities = new ArrayList<>();

            try {
                while (res.next()){
                    City city = new City(res.getLong(CityDAO.FIELDS[0]), res.getString(CityDAO.FIELDS[1]), country);
                    cities.add(city);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return cities;
        }

        return null;
    }

}
