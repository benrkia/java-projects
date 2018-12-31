package com.benrkia.market.dao;

import com.benrkia.market.address.City;
import com.benrkia.market.configuration.DBConnection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CityDAO implements DAO<City> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`city`";
    public static final String[] FIELDS = {"id", "name", "country"};

    private static CityDAO cityDAO;
    private CityDAO(){}
    public static CityDAO getInstance(){
        if(cityDAO == null)
            cityDAO = new CityDAO();
        return cityDAO;
    }

    @Override
    public City get(long id) {
        CountryDAO countryDAO = CountryDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        City city = null;

        try {
            if(res.next()){
                city = new City(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), countryDAO.get(res.getLong(FIELDS[2])));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    @Override
    public Collection<City> getAll() {
        CountryDAO countryDAO = CountryDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<City> cities = new ArrayList<>();

        try {
            while (res.next()){
                City city = new City(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), countryDAO.get(res.getLong(FIELDS[2])));
                cities.add(city);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public int add(City city) {
        if(city != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`name`, `country`) " +
                    "VALUES ('" + city.getName() + "', '" + city.getCountry().getId() + "')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                city.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, City city) {
        if(city != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`name`='"+city.getName()+"'," +
                    "`country`='"+city.getCountry().getId()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(City city) {
        if(city != null)
            update(city.getId(), city);
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
    public void delete(City city) {
        if(city != null)
            delete(city.getId());
    }

}
