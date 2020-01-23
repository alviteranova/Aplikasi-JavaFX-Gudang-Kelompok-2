/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelompok2support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Hulk
 */
public class Koneksi {
    public static Connection config(){
        String driver = "com.mysql.jdbc.Driver";
        String host = "jdbc:mysql://localhost/db_kelompok2gudang";
        String user = "root";
        String pass = "";
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection (host, user, pass);
            
            
            return conn;
        }catch (ClassNotFoundException | SQLException e){
            Alert peringatan = new Alert(Alert.AlertType.ERROR);
            peringatan.setTitle("Peringatan SQL Connection");
            peringatan.setHeaderText(null);
            peringatan.setContentText("+e");
            peringatan.showAndWait();
        }
        return null;
    }
    
}
