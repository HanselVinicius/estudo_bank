package br.com.alura.bytebank;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final String username = "admin";
    private final String password = "1234";
    private final String url = "jdbc:mysql://localhost:3306/BYTE_BANK";
    private final String connection ="\"jdbc:mysql://localhost:3306/BYTE_BANK?user=\"+username+\"&password=\"+password";
    public Connection recuperaConexao(){

        try{
          return createDataSource().getConnection();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }
    }

    private HikariDataSource createDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }


}
