package com.benrkia.market.dao;

import com.benrkia.market.orders.Cart;
import com.benrkia.market.orders.LineCmd;
import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.orders.LineCmdType;
import com.benrkia.market.orders.Order;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class LineCmdDAO implements DAO<LineCmd> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`linecmd`";
    public static final String[] FIELDS = {"id", "quantity", "price", "linecmdtype", "product", "order", "cart"};

    private static LineCmdDAO lineCmdDAO;
    private LineCmdDAO(){}
    public static LineCmdDAO getInstance(){
        if(lineCmdDAO == null)
            lineCmdDAO = new LineCmdDAO();
        return lineCmdDAO;
    }

    @Override
    public LineCmd get(long id) {
        ProductDAO productDAO = ProductDAO.getInstance();
        OrderDAO orderDAO = OrderDAO.getInstance();
        CartDAO cartDAO = CartDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        LineCmd lineCmd = null;

        try {
            if(res.next()){
                lineCmd = new LineCmd(res.getLong(FIELDS[0]), res.getInt(FIELDS[1]), res.getDouble(FIELDS[2]), res.getShort(FIELDS[3]), productDAO.get(res.getLong(FIELDS[4])), orderDAO.get(res.getLong(FIELDS[5])), cartDAO.get(res.getLong(FIELDS[6])) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineCmd;
    }

    @Override
    public Collection<LineCmd> getAll() {
        ProductDAO productDAO = ProductDAO.getInstance();
        OrderDAO orderDAO = OrderDAO.getInstance();
        CartDAO cartDAO = CartDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<LineCmd> lineCmds = new ArrayList<>();

        try {
            while (res.next()){
                LineCmd lineCmd = new LineCmd(res.getLong(FIELDS[0]), res.getInt(FIELDS[1]), res.getDouble(FIELDS[2]), res.getShort(FIELDS[3]), productDAO.get(res.getLong(FIELDS[4])), orderDAO.get(res.getLong(FIELDS[5])), cartDAO.get(res.getLong(FIELDS[6])) );

                lineCmds.add(lineCmd);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lineCmds;
    }

    @Override
    public int add(LineCmd lineCmd) {
        if(lineCmd != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`quantity`, `price`, `linecmdtype`, `product`, `cart`)" +
                    "VALUES ('"+lineCmd.getQuantity()+"', '"+lineCmd.getPrice()+"', '"+ LineCmdType.Cart+"', '"+lineCmd.getProduct().getId()+"', '"+lineCmd.getCart().getId()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                lineCmd.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, LineCmd lineCmd) {
        if(lineCmd != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`quantity`='"+lineCmd.getQuantity()+"', " +
                    "`price`='"+lineCmd.getPrice()+"', ";
            if(lineCmd.getOrder() != null)
                sql += "`order`='"+lineCmd.getOrder().getId()+"', ";

            sql += "`linecmdtype`='"+lineCmd.getLineCmdType()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    public void update(Order order, Cart cart) {
        if(cart != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`order`='"+order.getId()+"', " +
                    "`cart`=NULL, " +
                    "`linecmdtype`='"+LineCmdType.Order+"' " +
                    "WHERE `order` IS NULL AND cart="+cart.getId();
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(LineCmd lineCmd) {
        if(lineCmd != null)
            update(lineCmd.getId(), lineCmd);
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
    public void delete(LineCmd lineCmd) {
        if(lineCmd != null)
            delete(lineCmd.getId());
    }
}
