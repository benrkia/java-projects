package com.benrkia.market.dao;

import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.configuration.DBConnection;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

public class CartDAO  implements DAO<Cart> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`cart`";
    public static final String[] FIELDS = {"id", "creationdate"};

    private static CartDAO cartDAO;
    private CartDAO(){}
    public static CartDAO getInstance(){
        if(cartDAO == null)
            cartDAO = new CartDAO();
        return cartDAO;
    }

    @Override
    public Cart get(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Cart cart = null;

        try {
            if(res.next()){
                cart = new Cart(res.getLong(FIELDS[0]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[1])), 0.0);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cart;
    }

    @Override
    public Collection<Cart> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Cart> carts = new ArrayList<>();

        try {
            while (res.next()){
                Cart cart = new Cart(res.getLong(FIELDS[0]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[1])), 0.0);
                carts.add(cart);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return carts;
    }

    @Override
    public int add(Cart cart) {
        if(cart != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "()" +
                    "VALUES ()";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                Cart newCart = this.get(id);
                cart.setId(id);
                cart.setCreationDate(newCart.getCreationDate());
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Cart cart) {

    }

    @Override
    public void update(Cart cart) {
        if(cart != null)
            update(cart.getId(), cart);
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
    public void delete(Cart cart) {
        if(cart != null)
            delete(cart.getId());
    }

    public Collection<LineCmd> getLineCmds(Cart cart){
        OrderDAO orderDAO = OrderDAO.getInstance();
        ProductDAO productDAO = ProductDAO.getInstance();
        if(cart != null){
            String sql = "SELECT * FROM " + LineCmdDAO.TABLE_NAME + " WHERE `order` IS NULL AND " + LineCmdDAO.FIELDS[6] + "="+cart.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<LineCmd> lineCmds = new ArrayList<>();

            try {
                while (res.next()){
                    LineCmd lineCmd = new LineCmd(res.getLong(LineCmdDAO.FIELDS[0]), res.getInt(LineCmdDAO.FIELDS[1]), res.getDouble(LineCmdDAO.FIELDS[2]), res.getShort(LineCmdDAO.FIELDS[3]), productDAO.get(res.getLong(LineCmdDAO.FIELDS[4])), orderDAO.get(res.getLong(LineCmdDAO.FIELDS[5])), cart);
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
