package com.imd.comasy.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConFactory {
    private static final String PostgreSQLDriver = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/comasy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private static ConFactory instance;

    private static Connection con;

    public ConFactory() throws ClassNotFoundException {
        Class.forName(PostgreSQLDriver);
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ConFactory getInstance() throws ClassNotFoundException {
        if(instance == null) {
            instance = new ConFactory();
        }
        return instance;
    }
    public Connection conexao() throws ClassNotFoundException, SQLException {
        return con;
    }


}
