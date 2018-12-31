package com.benrkia.market.dao;

import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.products.Category;
import com.benrkia.market.products.Product;
import com.benrkia.market.users.Commercial;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class CategoryDAO implements DAO<Category> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`category`";
    public static final String[] FIELDS = {"id", "name", "description"};

    private static CategoryDAO categoryDAO;
    private CategoryDAO(){}
    public static CategoryDAO getInstance(){
        if(categoryDAO == null)
            categoryDAO = new CategoryDAO();
        return categoryDAO;
    }

    @Override
    public Category get(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Category category = null;

        try {
            if(res.next()){
                category = new Category(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public Collection<Category> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Category> categories = new ArrayList<>();

        try {
            while (res.next()){
                Category category = new Category(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]) );
                categories.add(category);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public int add(Category category) {
        if(category != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`name`, `description`)" +
                    "VALUES ('"+category.getName()+"', '"+category.getDescription()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                category.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Category category) {
        if(category != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`name`='"+category.getName()+"'," +
                    "`description`='"+category.getDescription()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Category category) {
        if(category != null)
            update(category.getId(), category);
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
    public void delete(Category category) {
        if(category != null)
            delete(category.getId());
    }

    public Collection<Product> getProducts(Category category){
        PersonDAO personDAO = PersonDAO.getInstance();
        ImageDAO imageDAO = ImageDAO.getInstance();
        if(category != null){
            String sql = "SELECT * FROM " + ProductDAO.TABLE_NAME + " WHERE " + ProductDAO.FIELDS[8] + "="+category.getId();
            ResultSet res = dbConnection.executeQuery(sql);
            Collection<Product> products = new ArrayList<>();

            try {
                while (res.next()){
                    Product product = new Product(res.getLong(ProductDAO.FIELDS[0]), res.getString(ProductDAO.FIELDS[1]), res.getString(ProductDAO.FIELDS[2]), res.getDouble(ProductDAO.FIELDS[3]), res.getDouble(ProductDAO.FIELDS[4]), res.getInt(ProductDAO.FIELDS[5]), (new SimpleDateFormat("yyyy-MM-dd")).parse(res.getString(ProductDAO.FIELDS[6])), (Commercial) personDAO.get(res.getLong(ProductDAO.FIELDS[7])), category, imageDAO.get(res.getLong(ProductDAO.FIELDS[9])));
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

        return null;
    }
}
