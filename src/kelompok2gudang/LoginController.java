/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelompok2gudang;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kelompok2support.Koneksi;

/**
 * FXML Controller class
 *
 * @author Hulk
 */
public class LoginController implements Initializable {
    Connection con = Koneksi.config();
    PreparedStatement pst;
    
    String title = "Aplikasi Gudang Inventaris by Kelompok 2";
    
    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXPasswordField txt_pass;
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField txt_passlihat;
    @FXML
    private JFXCheckBox ckLihat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnLoginAct(ActionEvent event) throws IOException {
        try{
                String username = txt_username.getText();
                String password = txt_pass.getText();
                if(username.equalsIgnoreCase("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(title);
                    alert.setHeaderText("Nama Pengguna Masih Kosong");
                    alert.setContentText("Silahkan Isi Nama Pengguna Terlebih Dahulu");
                    alert.showAndWait();
                }else if(password.equalsIgnoreCase("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(title);
                    alert.setHeaderText("Kata Sandi Masih Kosong");
                    alert.setContentText("Silahkan Isi Kata Sandi Terlebih Dahulu");
                    alert.showAndWait();
                }else{
                    String sqlLogin = "SELECT kel2_akses.kel2_nama_akses FROM kel2_akun INNER JOIN kel2_akses ON kel2_akun.kel2_id_akses = kel2_akses.kel2_id_akses WHERE kel2_akun.kel2_username='"+username+"' AND kel2_akun.kel2_password='"+password+"'";
                    pst = (PreparedStatement) con.prepareStatement(sqlLogin);
                    ResultSet rsLogin = pst.executeQuery(sqlLogin);
                    if(rsLogin.next()){
                        String akses = rsLogin.getString("kel2_nama_akses");
                        if(akses.equalsIgnoreCase("ADMIN")){
                            Parent home = FXMLLoader.load(getClass().getResource("menuAdmin.fxml"));
                            Scene homePage = new Scene(home);
                            Stage appHome = (Stage) ((Node) event.getSource()) .getScene().getWindow();
                            appHome.hide();
                            appHome.setScene(homePage);
                            appHome.show();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Gagal Login");
                        alert.setHeaderText("Password/Username tidak Ditemukan");
                        alert.showAndWait();
                        txt_username.setText("");
                        txt_pass.setText("");
                        txt_username.requestFocus();
                    }
                }
            } catch(SQLException e){
            e.printStackTrace();
            }
        }

    @FXML
    private void ckLihatExited(MouseEvent event) {
    }

    @FXML
    private void ckLihatEntered(MouseEvent event) {
    }
    }
    
