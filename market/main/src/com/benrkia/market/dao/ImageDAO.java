package com.benrkia.market.dao;

import com.benrkia.market.configuration.DBConnection;
import com.benrkia.market.products.Image;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ImageDAO implements DAO<Image> {

    private DBConnection dbConnection = DBConnection.getInstance();

    public static final String TABLE_NAME = "`image`";
    public static final String[] FIELDS = {"id", "name", "title", "extension"};

    private static ImageDAO imageDAO;
    private ImageDAO(){}
    public static ImageDAO getInstance(){
        if(imageDAO == null)
            imageDAO = new ImageDAO();
        return imageDAO;
    }

    @Override
    public Image get(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id="+id;
        ResultSet res = dbConnection.executeQuery(sql);
        Image image = null;

        try {
            if(res.next()){
                image = new Image(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getString(FIELDS[3]) );
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    public Collection<Image> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet res = dbConnection.executeQuery(sql);
        Collection<Image> images = new ArrayList<>();

        try {
            while (res.next()){
                Image image = new Image(res.getLong(FIELDS[0]), res.getString(FIELDS[1]), res.getString(FIELDS[2]), res.getString(FIELDS[3]) );
                images.add(image);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return images;
    }

    @Override
    public int add(Image image) {
        if(image != null) {
            String sql = "INSERT INTO " + TABLE_NAME +
                    "(`name`, `title`, `extension`)" +
                    "VALUES ('"+image.getName()+"', '"+image.getTitle()+"', '"+image.getExtension()+"')";
            try {
                int id = dbConnection.executeUpdate(sql, true);
                image.setId(id);
                return id;
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    @Override
    public void update(long id, Image image) {
        if(image != null){
            String sql = "UPDATE "+ TABLE_NAME +" SET " +
                    "`name`='"+image.getName()+"'," +
                    "`title`='"+image.getTitle()+"', " +
                    "`extension`='"+image.getExtension()+"' " +
                    "WHERE id="+id;
            try {
                dbConnection.executeUpdate(sql, false);
            }catch (MySQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Image image) {
        if(image != null)
            update(image.getId(), image);
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
    public void delete(Image image) {
        if(image != null)
            delete(image.getId());
    }
}
