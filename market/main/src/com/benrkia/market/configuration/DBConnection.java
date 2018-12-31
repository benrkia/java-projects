package com.benrkia.market.configuration;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class DBConnection {

    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(){
        try {
            Class.forName(Configuration.JDBC_DRIVER);

            connection = DriverManager.getConnection(Configuration.DB_URL, Configuration.USER, Configuration.PASS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance(){
        if(dbConnection==null)
            dbConnection = new DBConnection();
        return dbConnection;
    }

    public int executeUpdate(String sql, boolean isInsert) throws MySQLIntegrityConstraintViolationException {
        int ans = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet res;
            if(isInsert) {
                statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                res = statement.getGeneratedKeys();
                if(res.next()){
                    ans = res.getInt(1);
                }
                res.close();
            }
            else
                ans = statement.executeUpdate(sql);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public ResultSet executeQuery(String sql){
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
            try {
                if(connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
