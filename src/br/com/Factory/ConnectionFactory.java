
package br.com.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    
     public Connection getConnection() {
    
    try {
     //remoto
        //return DriverManager.getConnection("jdbc:mysql://216.172.172.58/calori12_bd_caloricar_mysql", "calori12_erpjava","erpjavacaloricar");
         //return DriverManager.getConnection("jdbc:mysql://localhost/bd_caloricar_mysql", "acesso_java","gH1J0DBj52s3ZdEu");
         return DriverManager.getConnection("jdbc:mysql://localhost/bd_caloricar_mysql", "root","");
    }
    catch (SQLException excecao) {
            throw new RuntimeException (excecao);
            }
    }
    
}
