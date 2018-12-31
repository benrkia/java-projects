package com.benrkia.market.dao;

import com.benrkia.market.address.Address;
import com.benrkia.market.configuration.DBConnection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AddressDAO implements DAO<Address> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`address`";
    public static final String[] FIELDS = {"id", "street", "city", "country", "postalcode"};

    private static AddressDAO addressDAO;
    private AddressDAO(){}
    public static AddressDAO getInstance(){
        if(addressDAO == null)
            addressDAO = new AddressDAO();
        return addressDAO;
    }

    @Override
    public Address get(long id) {
        CountryDAO countryDAO = CountryDAO.getInstance();
        CityDAO cityDAO = CityDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Address address = null;

        try {
            if(res.next()){
                address = new Address(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), cityDAO.get(res.getLong(FIELDS[2])), countryDAO.get(res.getLong(FIELDS[2])), res.getInt(FIELDS[4]));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public Collection<Address> getAll() {
        CountryDAO countryDAO = CountryDAO.getInstance();
        CityDAO cityDAO = CityDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Address> addresses = new ArrayList<>();

        try {
            while (res.next()){
                Address address = new Address(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), cityDAO.get(res.getLong(FIELDS[2])), countryDAO.get(res.getLong(FIELDS[2])), res.getInt(FIELDS[4]));
                addresses.add(address);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    @Override
    public int add(Address address) {
        if(address != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`street`, `city`, `country`, `postalcode`) " +
                    "VALUES ('" + address.getStreet() + "', '" + address.getCity().getId() + "', '" + address.getCountry().getId() + "', '" + address.getPostalCode() + "')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                address.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public void update(long id, Address address) {
        if(address != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`street`='"+address.getStreet()+"'," +
                    "`city`='"+address.getCity().getId()+"'," +
                    "`country`='"+address.getCountry().getId()+"'," +
                    "`postalcode`='"+address.getPostalCode()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Address address) {
        if(address != null)
            update(address.getId(), address);
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
    public void delete(Address address) {
        if(address != null)
            delete(address.getId());
    }

}
