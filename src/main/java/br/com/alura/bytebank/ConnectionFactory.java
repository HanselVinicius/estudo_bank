package br.com.alura.bytebank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    public Connection recuperaConexao(){

        try{
          return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/BYTE_BANK?user=admin&password=1234");

        }catch (SQLException e){
            throw new RuntimeException(e);

        }
    }


}
