package com.benrkia.market.dao;

import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.orders.Order;
import com.benrkia.market.orders.OrderType;
import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Commercial;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class OrderDAO implements DAO<Order> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`order`";
    public static final String[] FIELDS = {"id", "creationdate", "ordertype", "shippingaddress", "totalprice", "commercial", "client"};

    private static OrderDAO orderDAO;
    private OrderDAO(){}
    public static OrderDAO getInstance(){
        if(orderDAO == null)
            orderDAO = new OrderDAO();
        return orderDAO;
    }

    @Override
    public Order get(long id) {
        AddressDAO addressDAO = AddressDAO.getInstance();
        PersonDAO personDAO = PersonDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Order order = null;

        try {
            if(res.next()){
                order = new Order(res.getLong(FIELDS[0]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[1])), res.getShort(FIELDS[2]), addressDAO.get(res.getLong(FIELDS[3])), res.getDouble(FIELDS[4]), (Commercial) personDAO.get(res.getLong(FIELDS[5])), (Client) personDAO.get(res.getLong(FIELDS[6])) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public Collection<Order> getAll() {
        AddressDAO addressDAO = AddressDAO.getInstance();
        PersonDAO personDAO = PersonDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Order> orders = new ArrayList<>();

        try {
            while (res.next()){
                Order order = new Order(res.getLong(FIELDS[0]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[1])), res.getShort(FIELDS[2]), addressDAO.get(res.getLong(FIELDS[3])), res.getDouble(FIELDS[4]), (Commercial) personDAO.get(res.getLong(FIELDS[5])), (Client) personDAO.get(res.getLong(FIELDS[6])) );
                orders.add(order);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public int add(Order order) {
        if(order != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`ordertype`, `shippingaddress`, `totalprice`, `commercial`, `client`)" +
                    "VALUES ('"+ OrderType.New+"', '"+order.getShippingAddress().getId()+"', '"+order.getTotalPrice()+"', '"+order.getCommercial().getId()+"', '"+order.getClient().getId()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                Order newOrder = this.get(id);
                order.setId(id);
                order.setCreationDate(newOrder.getCreationDate());
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Order order) {
        if(order != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`ordertype`='"+order.getOrderType()+"'," +
                    "`shippingaddress`='"+order.getShippingAddress().getId()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Order order) {
        if(order != null)
            update(order.getId(), order);
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
    public void delete(Order order) {
        if(order != null)
            delete(order.getId());
    }

    public Collection<LineCmd> getLineCmds(Order order){
        ProductDAO productDAO = ProductDAO.getInstance();
        CartDAO cartDAO = CartDAO.getInstance();
        if(order != null){
            String sql = "SELECT * FROM " + LineCmdDAO.TABLE_NAME + " WHERE " + LineCmdDAO.FIELDS[5] + "="+order.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<LineCmd> lineCmds = new ArrayList<>();

            try {
                while (res.next()){
                    LineCmd lineCmd = new LineCmd(res.getLong(LineCmdDAO.FIELDS[0]), res.getInt(LineCmdDAO.FIELDS[1]), res.getDouble(LineCmdDAO.FIELDS[2]), res.getShort(LineCmdDAO.FIELDS[3]), productDAO.get(res.getLong(LineCmdDAO.FIELDS[4])), order, cartDAO.get(res.getLong(LineCmdDAO.FIELDS[6])));
                    lineCmds.add(lineCmd);
                }
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return lineCmds;
        }

        return null;
    }
}
