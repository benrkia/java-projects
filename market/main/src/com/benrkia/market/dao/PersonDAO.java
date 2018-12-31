package com.benrkia.market.dao;

import com.benrkia.market.orders.Order;
import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.orders.OrderType;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Commercial;
import com.benrkia.market.users.Person;
import com.benrkia.market.users.PersonType;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAO implements DAO<Person> {

    private DBConnection dbConnection = DBConnection.getInstance();
    private AddressDAO addressDAO = AddressDAO.getInstance();
    private CartDAO cartDAO = CartDAO.getInstance();
    private CategoryDAO categoryDAO = CategoryDAO.getInstance();
    private ImageDAO imageDAO = ImageDAO.getInstance();

    public static final String TABLE_NAME = "`person`";
    public static final String[] FIELDS = {"id", "firstname", "lastname", "birthdate", "email", "password", "address", "cart", "usertype"};

    private static PersonDAO personDAO;
    private PersonDAO(){}
    public static PersonDAO getInstance(){
        if(personDAO == null)
            personDAO = new PersonDAO();
        return personDAO;
    }

    @Override
    public Person get(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Person person = null;

        try {
            if(res.next()){
                /*
                * 1 == person is a commercial
                * 2 == person is a client
                * */
                if(res.getShort(FIELDS[8]) == PersonType.Commercial)
                    person = new Commercial(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])) );
                else
                    person = new Client(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])), cartDAO.get(res.getLong(FIELDS[7])) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public Person get(String email, String password) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE `email`='"+email+"' AND password='"+password+"' LIMIT 1";
        ResultSet res = dbConnection.executeQuery(sql);
        Person person = null;

        try {
            if(res.next()){
                /*
                * 1 == person is a commercial
                * 2 == person is a client
                * */
                if(res.getShort(FIELDS[8]) == PersonType.Commercial)
                    person = new Commercial(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])) );
                else
                    person = new Client(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])), cartDAO.get(res.getLong(FIELDS[7])) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public Commercial getRandomCommercial() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + ".`usertype`=" + PersonType.Commercial + " ORDER BY id DESC LIMIT 1";
        ResultSet res = dbConnection.executeQuery(sql);
        Commercial commercial = null;

        try {
            if(res.next()){
                commercial = new Commercial(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commercial;
    }

    public Commercial getAvailableCommercial(){

        String sql = "SELECT " + TABLE_NAME + ".id FROM " + TABLE_NAME + " INNER JOIN " + OrderDAO.TABLE_NAME + " ON " + OrderDAO.TABLE_NAME + ".`commercial` = " + TABLE_NAME + ".id WHERE " + OrderDAO.TABLE_NAME + ".ordertype=" + OrderType.New + " AND " + TABLE_NAME + ".`usertype`=" + PersonType.Commercial + " GROUP BY " + TABLE_NAME + ".id ORDER BY COUNT(" + TABLE_NAME + ".id) ASC LIMIT 1";
        ResultSet res = dbConnection.executeQuery(sql);
        Commercial commercial = null;

        try {
            if(res.next()){
                commercial = (Commercial) this.get(res.getLong(FIELDS[0]));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commercial;
    }

    @Override
    public Collection<Person> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE usertype=" + PersonType.Client;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Person> clients = new ArrayList<>();

        try {
            while (res.next()){
                Person client = new Client(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])), cartDAO.get(res.getLong(FIELDS[7])) );
                clients.add(client);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public Collection<Person> getAllCommercials() {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE usertype=" + PersonType.Commercial;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Person> commercials = new ArrayList<>();

        try {
            while (res.next()){
                Person commercial = new Commercial(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), Date.valueOf(res.getString(FIELDS[3])), res.getString(FIELDS[4]), res.getString(FIELDS[5]), addressDAO.get(res.getLong(FIELDS[6])) );
                commercials.add(commercial);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commercials;
    }

    @Override
    public int add(Person person) {
        if(person != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`firstname`, `lastname`, `birthdate`, `email`, `usertype`, `address`, `password`)" +
                    "VALUES ('"+person.getFirstName()+"', '"+person.getLastName()+"', '"+person.getBirthdate().toString()+"', '"+person.getEmail()+"', '"+PersonType.Client+"', '"+person.getAddress().getId()+"', '"+person.getPassword()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                person.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Person person) {
        if(person != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`firstname`='"+person.getFirstName()+"'," +
                    "`lastname`='"+person.getLastName()+"'," +
                    "`email`='"+person.getEmail()+"'," +
                    "`usertype`='"+PersonType.Client+"',";
            if(person.getAddress() != null)
                    sql += "`address`='"+person.getAddress().getId()+"',";
            if(((Client)person).getCart() != null)
                    sql += " `cart`='"+((Client)person).getCart().getId()+"',";

            sql += "`password`='"+person.getPassword()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Person person) {
        if(person != null)
            update(person.getId(), person);
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
    public void delete(Person person) {
        if(person != null)
            delete(person.getId());
    }

    public Collection<Product> getProducts(Commercial commercial){
        if(commercial != null){
            String sql = "SELECT * FROM " + ProductDAO.TABLE_NAME + " WHERE " + ProductDAO.FIELDS[7] + "=" + commercial.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<Product> products = new ArrayList<>();

            try {
                while (res.next()){
                    Product product = new Product(res.getLong(ProductDAO.FIELDS[0]), res.getString(ProductDAO.FIELDS[1]), res.getString(ProductDAO.FIELDS[2]), res.getDouble(ProductDAO.FIELDS[3]), res.getDouble(ProductDAO.FIELDS[4]), res.getInt(ProductDAO.FIELDS[5]), Date.valueOf(res.getString(ProductDAO.FIELDS[6])), commercial, categoryDAO.get(res.getLong(ProductDAO.FIELDS[8])), imageDAO.get(res.getLong(ProductDAO.FIELDS[9])));
                    products.add(product);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return products;
        }

        return null;
    }

    public Collection<Order> getCommercialOrders(Commercial commercial){
        if(commercial != null){
            String sql = "SELECT * FROM " + OrderDAO.TABLE_NAME + " WHERE " + OrderDAO.FIELDS[5] + "=" + commercial.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<Order> orders = new ArrayList<>();

            try {
                while (res.next()){
                    Order order = new Order(res.getLong(OrderDAO.FIELDS[0]), Date.valueOf(res.getString(OrderDAO.FIELDS[1])), res.getShort(OrderDAO.FIELDS[2]), addressDAO.get(res.getLong(OrderDAO.FIELDS[3])), res.getDouble(OrderDAO.FIELDS[4]), commercial, (Client) this.get(res.getLong(OrderDAO.FIELDS[6])) );
                    orders.add(order);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return orders;
        }

        return null;
    }

    public Collection<Order> getClientOrders(Client client){
        if(client != null){
            String sql = "SELECT * FROM " + OrderDAO.TABLE_NAME + " WHERE " + OrderDAO.FIELDS[6] + "=" + client.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<Order> orders = new ArrayList<>();

            try {
                while (res.next()){
                    Order order = new Order(res.getLong(OrderDAO.FIELDS[0]), Date.valueOf(res.getString(OrderDAO.FIELDS[1])), res.getShort(OrderDAO.FIELDS[2]), addressDAO.get(res.getLong(OrderDAO.FIELDS[3])), res.getDouble(OrderDAO.FIELDS[4]), (Commercial) this.get(res.getLong(OrderDAO.FIELDS[5])), client );
                    orders.add(order);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return orders;
        }

        return null;
    }

}
