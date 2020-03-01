
package br.com.Nuvem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactoryNuvem {
    
    
      public Connection getConnection() {
    
          
    try {
        return DriverManager.getConnection("jdbc:mysql://216.172.172.58/calori12_bd_caloricar_mysql",
                                           "calori12_erpjava","erpjavacaloricar");
        
    }
    catch (SQLException excecao) {
            throw new RuntimeException (excecao);
            }
    }
    
    
}
