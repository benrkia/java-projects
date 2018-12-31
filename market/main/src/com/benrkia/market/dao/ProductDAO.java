package com.benrkia.market.dao;

import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Commercial;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.text.StringEscapeUtils;

public class ProductDAO implements DAO<Product> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`product`";
    public static final String[] FIELDS = {"id", "name", "description", "sellingprice", "buyingprice", "quantity", "creationdate", "commercial", "category", "image"};

    private static ProductDAO productDAO;
    private ProductDAO(){}
    public static ProductDAO getInstance(){
        if(productDAO == null)
            productDAO = new ProductDAO();
        return productDAO;
    }

    @Override
    public Product get(long id) {
        PersonDAO personDAO = PersonDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        ImageDAO imageDAO = ImageDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Product product = null;

        try {
            if(res.next()){
                product = new Product(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getDouble(FIELDS[3]), res.getDouble(FIELDS[4]), res.getInt(FIELDS[5]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[6])), (Commercial) personDAO.get(res.getLong(FIELDS[7])), categoryDAO.get(res.getLong(FIELDS[8])), imageDAO.get(res.getLong(FIELDS[9])));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public Collection<Product> getAll() {
        PersonDAO personDAO = PersonDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        ImageDAO imageDAO = ImageDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Product> products = new ArrayList<>();

        try {
            while (res.next()){
                Product product = new Product(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getDouble(FIELDS[3]), res.getDouble(FIELDS[4]), res.getInt(FIELDS[5]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[6])), (Commercial) personDAO.get(res.getLong(FIELDS[7])), categoryDAO.get(res.getLong(FIELDS[8])), imageDAO.get(res.getLong(FIELDS[9])));
                products.add(product);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Collection<Product> getNewArrivals() {
        PersonDAO personDAO = PersonDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        ImageDAO imageDAO = ImageDAO.getInstance();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY creationdate DESC LIMIT 4";
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Product> products = new ArrayList<>();

        try {
            while (res.next()){
                Product product = new Product(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getDouble(FIELDS[3]), res.getDouble(FIELDS[4]), res.getInt(FIELDS[5]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[6])), (Commercial) personDAO.get(res.getLong(FIELDS[7])), categoryDAO.get(res.getLong(FIELDS[8])), imageDAO.get(res.getLong(FIELDS[9])));
                products.add(product);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Collection<Product> getTopSaled() {
        PersonDAO personDAO = PersonDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        ImageDAO imageDAO = ImageDAO.getInstance();
        String sql = "SELECT " + TABLE_NAME + ".* FROM " + LineCmdDAO.TABLE_NAME + " INNER JOIN product ON " + LineCmdDAO.TABLE_NAME + ".product = " + TABLE_NAME + ".id WHERE `order` IS NOT NULL GROUP BY " + TABLE_NAME + ".id LIMIT 4";
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Product> products = new ArrayList<>();

        try {
            while (res.next()){
                Product product = new Product(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getDouble(FIELDS[3]), res.getDouble(FIELDS[4]), res.getInt(FIELDS[5]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(FIELDS[6])), (Commercial) personDAO.get(res.getLong(FIELDS[7])), categoryDAO.get(res.getLong(FIELDS[8])), imageDAO.get(res.getLong(FIELDS[9])));
                products.add(product);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public int add(Product product) {
        if(product != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`name`, `description`, `sellingprice`, `buyingprice`, `quantity`, `commercial`, `category`, `image`)" +
                    "VALUES ('"+product.getName()+"', '"+product.getDescription()+"', '"+product.getSellingPrice()+"', '"+product.getBuyingPrice()+"', '"+product.getQuantity()+"', '"+product.getCommercial().getId()+"', '"+product.getCategory().getId()+"', '"+product.getImage().getId()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                Product newProduct = this.get(id);
                product.setId(id);
                product.setCreationDate(newProduct.getCreationDate());
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Product product) {
        if(product != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`name`='"+StringEscapeUtils.escapeEcmaScript(product.getName())+"'," +
                    "`description`='"+StringEscapeUtils.escapeEcmaScript(product.getDescription())+"', " +
                    "`quantity`='"+product.getQuantity()+"', " +
                    "`sellingprice`='"+product.getSellingPrice()+"', " +
                    "`category`='"+product.getCategory().getId()+"', " +
                    "`image`='"+product.getImage().getId()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Product product) {
        if(product != null)
            update(product.getId(), product);
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
    public void delete(Product product) {
        if(product != null)
            delete(product.getId());
    }
}
