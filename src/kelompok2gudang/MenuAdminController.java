/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelompok2gudang;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.TokenType;
import kelompok2support.Koneksi;
import kelompok2tabel.TabelAdminAkun;
import kelompok2tabel.TabelAdminKaryawan;

/**
 * FXML Controller class
 *
 * @author Hulk
 */
public class MenuAdminController implements Initializable {
    Connection con = Koneksi.config();
    PreparedStatement pst;
    ObservableList<TabelAdminAkun> list = FXCollections.observableArrayList();
    ObservableList<TabelAdminKaryawan> listKar = FXCollections.observableArrayList();
    String pass = "12345";
    String title = "Aplikasi Gudang Inventaris by Kelompok 2";
    String id_akses;
    String pilih_tabel = null;
    String pilih_kar = null;
    String kar_id = null;
    @FXML
    private TableView<TabelAdminAkun> tbl_akun;
    @FXML
    private TableColumn<TabelAdminAkun, String> col_id;
    @FXML
    private TableColumn<TabelAdminAkun, String> col_username;
    @FXML
    private TableColumn<TabelAdminAkun, String> col_nama;
    @FXML
    private TableColumn<TabelAdminAkun,String> col_notelp;
    @FXML
    private TableColumn<TabelAdminAkun, String> col_akses;
    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXTextField txt_id;
    @FXML
    private JFXPasswordField txt_pass;
    @FXML
    private JFXTextField txt_nama;
    @FXML
    private JFXTextField txt_notelp;
    @FXML
    private JFXComboBox<String> cmb_akses;
    @FXML
    private JFXButton btn_simpan;
    @FXML
    private Label lblForm;
    @FXML
    private JFXButton btnHapus;
    @FXML
    private JFXButton btn_simpanEdit;
    @FXML
    private JFXCheckBox ckLihat;
    @FXML
    private JFXTextField txt_lihatPassword;
    @FXML
    private JFXButton btn_Kembali;
    @FXML
    private TableView<TabelAdminKaryawan> tblKaryawan;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> colNIP;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> colNama;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> colTgl;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> ColJk;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> ColAlamat;
    @FXML
    private TableColumn<TabelAdminKaryawan, String> ColTelepon;
    @FXML
    private JFXTextField txtNIP;
    @FXML
    private JFXTextField txtNama;
    @FXML
    private JFXTextField txtTelepon;
    @FXML
    private JFXDatePicker dateLahir;
    @FXML
    private JFXComboBox<String> cmbJK;
    @FXML
    private JFXTextArea txtAlamat;
    @FXML
    private JFXButton btnKembali;
    @FXML
    private JFXButton btnTambah;
    @FXML
    private JFXButton btnSimpan;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private Label lblFormKar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            //Tambah Data
            Otomatisasi();
            txt_id.setEditable(false);
            ckLihat.setVisible(true);
            txt_pass.setText(pass);
            
            OtomatisasiKaryawan();
            setTabelKaryawan();

            setTabelAkun();
            
            setComboBox();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setComboBox(){
        ObservableList<String> cblist = FXCollections.observableArrayList("ADMIN","USER","MANAGER");
        ObservableList<String> cblistjk = FXCollections.observableArrayList("PRIA","WANITA");
        cmbJK.setItems(cblistjk);
        cmb_akses.setItems(cblist);
    }

    @FXML
    private void cmbAksesAct(ActionEvent event) {
    }

    @FXML
    private void btnSimpanAct(ActionEvent event) {
        try {
            String username = txt_username.getText();
            String password = txt_pass.getText();
            String nama = txt_nama.getText();
            String telp = txt_notelp.getText();
            String akses = cmb_akses.getSelectionModel().getSelectedItem().toString();
            if(username.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Username Belum Di Isi");
                alert.setContentText("Silahkan isi Username Terlebih Dahulu");
                alert.showAndWait();
                txt_username.requestFocus();
            }else if(password.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Password Belum Di Isi");
                alert.setContentText("Silahkan isi Password Terlebih Dahulu");
                alert.showAndWait();
                txt_pass.requestFocus();
            }else if(nama.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nama Belum Di Isi");
                alert.setContentText("Silahkan isi Nama Terlebih Dahulu");
                alert.showAndWait();
                txt_nama.requestFocus();
            }else if(telp.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nomor Telepon Belum Di Isi");
                alert.setContentText("Silahkan isi Nomor Telepon Terlebih Dahulu");
                alert.showAndWait();
                txt_notelp.requestFocus();
            }
            String sql_akses = "SELECT kel2_id_akses FROM kel2_akses WHERE kel2_nama_akses = '"+akses+"'";
            pst = con.prepareStatement(sql_akses);
            ResultSet rs_akses = pst.executeQuery();
            if(rs_akses.next()){
                id_akses = rs_akses.getString("kel2_id_akses");
            }
            
            String sql_tambah = "INSERT INTO kel2_akun VALUES ('"+txt_id.getText()+"','"+username+"','"+password+"','"+nama+"','"+telp+"','"+id_akses+"')";
            pst = con.prepareStatement(sql_tambah);
            pst.executeUpdate();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText("Akun Baru Berhasil Ditambah");
            alert.showAndWait();
            
            txt_username.setText("");
            txt_nama.setText("");
            txt_notelp.setText("");
            cmb_akses.setItems(null);
            setComboBox();
            Otomatisasi();
            txt_id.setEditable(false);
            txt_lihatPassword.setEditable(true);
            ckLihat.setVisible(true);
            txt_pass.setText(pass);
            btn_simpan.setVisible(true);
            btn_simpanEdit.setVisible(false);
            
            list.clear();
            setTabelAkun();
            
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Hak Akses Belum Di Pilih");
            alert.setContentText("Silahkan Pilih Hak Akses Terlebih Dahulu");
            alert.showAndWait();
        }
    }
    
    public void setTabelAkun(){
        try{
            String sqlTabel = "SELECT * FROM kel2_akun INNER JOIN kel2_akses ON kel2_akun.kel2_id_akses=kel2_akses.kel2_id_akses";
            pst = (PreparedStatement) con.prepareStatement(sqlTabel);
            ResultSet rsTabel = pst.executeQuery(sqlTabel);
           while(rsTabel.next()){
               list.add(new TabelAdminAkun(rsTabel.getString("kel2_id_akun"), rsTabel.getString("kel2_username"), rsTabel.getString("kel2_password"), rsTabel.getString("kel2_nama_akun"), rsTabel.getString("kel2_telp"), rsTabel.getString("kel2_nama_akses")));
           }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        col_notelp.setCellValueFactory(new PropertyValueFactory<>("telp"));
        col_akses.setCellValueFactory(new PropertyValueFactory<>("akses"));
        tbl_akun.setItems(list);
    }

    @FXML
    private void tblAkunClicked(MouseEvent event) {
        try {
            
            if(event.getClickCount() >= 2){
                lblForm.setText("Edit Data Akun");
                btn_simpanEdit.setVisible(true);
                btn_simpan.setVisible(false);
                txt_id.setEditable(false);
                txt_pass.setEditable(false);
                ckLihat.setVisible(false);
                txt_id.setText(tbl_akun.getSelectionModel().getSelectedItem().getId_user());
                txt_username.setText(tbl_akun.getSelectionModel().getSelectedItem().getUsername());
                txt_pass.setText(tbl_akun.getSelectionModel().getSelectedItem().getPassword());
                txt_nama.setText(tbl_akun.getSelectionModel().getSelectedItem().getNama());
                txt_notelp.setText(tbl_akun.getSelectionModel().getSelectedItem().getTelp());
                cmb_akses.setValue(tbl_akun.getSelectionModel().getSelectedItem().getAkses());
            }else{
                pilih_tabel = null;
                pilih_tabel = tbl_akun.getSelectionModel().getSelectedItem().getId_user();
            }    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void hapusAction(ActionEvent event) throws SQLException {
        if(pilih_tabel == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Akun Belum Dipilih");
            alert.setContentText("Silahkan pilih akun yang akan dipilih");
            alert.showAndWait();
        }else{
            System.out.println("Ada");
            String sql_hapus = "DELETE FROM kel2_akun WHERE kel2_id_akun = '"+pilih_tabel+"'";
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText("Apakah Anda Yakin Akan Menghapus Akun ?");
            alert.setContentText("Klik OK untuk menghapus ?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                pst = con.prepareStatement(sql_hapus);
                pst.executeUpdate();
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle(title);
                alert1.setHeaderText("Akun Berhasil Dihapus");
                alert1.showAndWait();
                
                list.clear();
                setTabelAkun();
            }else{
                alert.close();
            }
            pilih_tabel = null; 
        }
    }

    @FXML
    private void btnSimpanEdit(ActionEvent event) throws SQLException {
            String id_akun = txt_id.getText();
            String username = txt_username.getText();
            String nama = txt_nama.getText();
            String telp = txt_notelp.getText();
            String akses = cmb_akses.getSelectionModel().getSelectedItem().toString();
            if(username.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Username Belum Di Isi");
                alert.setContentText("Silahkan isi Username Terlebih Dahulu");
                alert.showAndWait();
                txt_username.requestFocus();
            }else if(nama.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nama Belum Di Isi");
                alert.setContentText("Silahkan isi Nama Terlebih Dahulu");
                alert.showAndWait();
                txt_nama.requestFocus();
            }else if(telp.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nomor Telepon Belum Di Isi");
                alert.setContentText("Silahkan isi Nomor Telepon Terlebih Dahulu");
                alert.showAndWait();
                txt_notelp.requestFocus();
            }else{
                String sql_akses = "SELECT kel2_id_akses FROM kel2_akses WHERE kel2_nama_akses = '"+akses+"'";
            pst = con.prepareStatement(sql_akses);
            ResultSet rs_akses = pst.executeQuery();
            if(rs_akses.next()){
                id_akses = rs_akses.getString("kel2_id_akses");
            }
            
            String sql_update = "UPDATE kel2_akun SET kel2_username = '"+username+"', kel2_nama_akun = '"+nama+"', kel2_telp='"+telp+"', kel2_id_akses='"+id_akses+"' WHERE kel2_id_akun ='"+id_akun+"'";
            pst = con.prepareStatement(sql_update);
            pst.executeUpdate();
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Data Berhasil Disimpan");
            alert.showAndWait();
            
            list.clear();
            setTabelAkun();
            
            lblForm.setText("Tambah Data User");
            txt_username.setText("");
            txt_nama.setText("");
            txt_notelp.setText("");
            cmb_akses.setItems(null);
            setComboBox();
            Otomatisasi();
            txt_id.setEditable(false);
            txt_lihatPassword.setEditable(true);
            ckLihat.setVisible(true);
            txt_pass.setText(pass);
            btn_simpan.setVisible(true);
            btn_simpanEdit.setVisible(false);
        }    
    }

    @FXML
    private void btnKembaliAct(ActionEvent event) {
        try {
            lblForm.setText("Tambah Data User");
            txt_username.setText("");
            txt_nama.setText("");
            txt_notelp.setText("");
            cmb_akses.setItems(null);
            setComboBox();
            Otomatisasi();
            txt_id.setEditable(false);
            txt_lihatPassword.setEditable(true);
            ckLihat.setVisible(true);
            txt_pass.setText(pass);
            btn_simpan.setVisible(true);
            btn_simpanEdit.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Otomatisasi() throws SQLException{
        try{
            String string_awal,string_id = null;
            int id_user;
            String sql_otomatisasi = "SELECT kel2_id_akun FROM kel2_akun ORDER BY kel2_id_akun DESC";
            pst = (PreparedStatement) con.prepareStatement(sql_otomatisasi);
            ResultSet rs_oto = pst.executeQuery();
            if(rs_oto.next()){
                string_awal = rs_oto.getString("kel2_id_akun").substring(2);
                id_user = Integer.valueOf(string_awal);
                id_user++;
            }else{
                id_user = 1;
            }
            
            if(id_user < 10){
                string_id = ("ID00" + id_user);
            } if(id_user >= 10){
                string_id = ("ID0" + id_user);
            } if(id_user >= 100){
                string_id = ("ID" + id_user);
            }
            txt_id.setText(string_id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void OtomatisasiKaryawan() throws SQLException{
        try{
            String kar_awal;
            int id_kar;
            String sql_otomatisasiKar = "SELECT kel2_id_karyawan FROM kel2_karyawan ORDER BY kel2_id_karyawan DESC";
            pst = (PreparedStatement) con.prepareStatement(sql_otomatisasiKar);
            ResultSet rs_otoKar = pst.executeQuery();
            if(rs_otoKar.next()){
                kar_awal = rs_otoKar.getString("kel2_id_karyawan").substring(2);
                id_kar = Integer.valueOf(kar_awal);
                id_kar++;
            }else{
                id_kar = 1;
            }
            
            if(id_kar < 10){
                kar_id = ("ID000" + id_kar);
            } if(id_kar >= 10){
                kar_id = ("ID00" + id_kar);
            } if(id_kar >= 100){
                kar_id = ("ID0" + id_kar);
            }
            if(id_kar >= 1000){
                kar_id = ("ID" + id_kar);
            }
            System.out.println(kar_id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ckLihatMouseEntered(MouseEvent event){
        ckLihat.setSelected(true);
            txt_lihatPassword.setText(txt_pass.getText());
            txt_pass.setVisible(false);
            txt_lihatPassword.setVisible(true);
    }

    @FXML
    private void ckLihatMouseExited(MouseEvent event) {
        ckLihat.setSelected(false);
            txt_lihatPassword.setVisible(false);
            txt_pass.setVisible(true);
    }
    
    public void setTabelKaryawan(){
        try{
            String sqlTabelKaryawan = "SELECT * FROM kel2_karyawan";
            pst = (PreparedStatement) con.prepareStatement(sqlTabelKaryawan);
            ResultSet rsTabelKaryawan = pst.executeQuery(sqlTabelKaryawan);
           while(rsTabelKaryawan.next()){
               listKar.add(new TabelAdminKaryawan(rsTabelKaryawan.getString("kel2_id_karyawan"),rsTabelKaryawan.getString("kel2_nip"),rsTabelKaryawan.getString("kel2_nama"),rsTabelKaryawan.getString("kel2_tgl_lahir"),rsTabelKaryawan.getString("kel2_jk"),rsTabelKaryawan.getString("kel2_alamat"),rsTabelKaryawan.getString("kel2_telp")));
           }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        colNIP.setCellValueFactory(new PropertyValueFactory<>("nip"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colTgl.setCellValueFactory(new PropertyValueFactory<>("tgl_lahir"));
        ColJk.setCellValueFactory(new PropertyValueFactory<>("jk"));
        ColAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        ColTelepon.setCellValueFactory(new PropertyValueFactory<>("telp"));
        tblKaryawan.setItems(listKar);
    }

    @FXML
    private void tblKaryawanClicked(MouseEvent event) {
        if(event.getClickCount() >= 2){
            lblFormKar.setText("Edit Data Karyawan");
            btnTambah.setVisible(false);
            btnSimpan.setVisible(true);
            String editID = tblKaryawan.getSelectionModel().getSelectedItem().getId_karyawan();
            txtNIP.setText(tblKaryawan.getSelectionModel().getSelectedItem().getNip());
            txtNama.setText(tblKaryawan.getSelectionModel().getSelectedItem().getNama());
            txtAlamat.setText(tblKaryawan.getSelectionModel().getSelectedItem().getAlamat());
            txtTelepon.setText(tblKaryawan.getSelectionModel().getSelectedItem().getTelp());
            LocalDate editTanggal = LocalDate.parse(tblKaryawan.getSelectionModel().getSelectedItem().getTgl_lahir());
            dateLahir.setValue(editTanggal);
            cmbJK.setValue(tblKaryawan.getSelectionModel().getSelectedItem().getJk());
            pilih_kar = tblKaryawan.getSelectionModel().getSelectedItem().getId_karyawan();
        }else{
            pilih_kar = tblKaryawan.getSelectionModel().getSelectedItem().getId_karyawan();
        }
    }

    @FXML
    private void kembaliAction(ActionEvent event) throws SQLException {
        txtNIP.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        dateLahir.setValue(null);
        btnTambah.setVisible(true);
        btnSimpan.setVisible(false);
        cmbJK.setItems(null);
        lblFormKar.setText("Tambah Data Karyawan");
        setComboBox();
        OtomatisasiKaryawan();
    }

    @FXML
    private void tambahAction(ActionEvent event) {
        try{
        String nip = txtNIP.getText();
        String namaKar =txtNama.getText();
        String alamat = txtAlamat.getText();
        String telpKar = txtTelepon.getText();
        String jk = cmbJK.getSelectionModel().getSelectedItem().toString();
        if(nip.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("NIP Belum Di Isi");
                alert.setContentText("Silahkan isi NIP Terlebih Dahulu");
                alert.showAndWait();
                txtNIP.requestFocus();
            }else if(namaKar.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nama Belum Di Isi");
                alert.setContentText("Silahkan isi Nama Terlebih Dahulu");
                alert.showAndWait();
                txtNama.requestFocus();
            }else if(telpKar.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nomor Telepon Belum Di Isi");
                alert.setContentText("Silahkan isi Nomor Telepon Terlebih Dahulu");
                alert.showAndWait();
                txtTelepon.requestFocus();
            }else if(alamat.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Alamat Belum Di Isi");
                alert.setContentText("Silahkan isi Alamat Terlebih Dahulu");
                alert.showAndWait();
                txtAlamat.requestFocus();
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Jenis Kelamin Belum Dipilih");
            alert.setContentText("Silahkan Pilih Jenis Kelamin Terlebih Dahulu");
            alert.showAndWait();
        }
        try {
            int hari = dateLahir.getValue().getDayOfMonth();
            int bulan = dateLahir.getValue().getMonthValue();
            int tahun = dateLahir.getValue().getYear();
            if(tahun > now().getYear()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                alert.showAndWait();
            }else if(tahun == now().getYear()){
                if(bulan > now().getMonthValue()){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(title);
                    alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                    alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                    alert.showAndWait();
                }else{
                    if(hari > now().getDayOfMonth()){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle(title);
                        alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                        alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                        alert.showAndWait();
                    }else {
                        String sql_tambahKar = "INSERT INTO kel2_karyawan VALUES ('"+kar_id+"','"+txtNIP.getText()+"','"+txtNama.getText()+"','"+dateLahir.getValue().toString()+"','"+cmbJK.getSelectionModel().getSelectedItem()+"','"+txtAlamat.getText()+"','"+txtTelepon.getText()+"')";
                        pst = con.prepareStatement(sql_tambahKar);
                        pst.executeUpdate();
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle(title);
                        alert.setHeaderText("Data Karyawan Berhasil Ditambahkan");
                        alert.showAndWait();
                        listKar.clear();
                        setTabelKaryawan();
                        txtNIP.setText("");
                        txtNama.setText("");
                        txtAlamat.setText("");
                        txtTelepon.setText("");
                        dateLahir.setValue(null);
                        btnTambah.setVisible(true);
                        btnSimpan.setVisible(false);
                        cmbJK.setItems(null);
                        lblFormKar.setText("Tambah Data Karyawan");
                        setComboBox();
                        OtomatisasiKaryawan();
                    }
                }
            }else{
                String sql_tambahKar = "INSERT INTO kel2_karyawan VALUES ('"+kar_id+"','"+txtNIP.getText()+"','"+txtNama.getText()+"','"+dateLahir.getValue().toString()+"','"+cmbJK.getSelectionModel().getSelectedItem()+"','"+txtAlamat.getText()+"','"+txtTelepon.getText()+"')";
                pst = con.prepareStatement(sql_tambahKar);
                pst.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText("Data Karyawan Berhasil Ditambahkan");
                alert.showAndWait();
                listKar.clear();
                setTabelKaryawan();
                txtNIP.setText("");
                txtNama.setText("");
                txtAlamat.setText("");
                txtTelepon.setText("");
                dateLahir.setValue(null);
                btnTambah.setVisible(true);
                btnSimpan.setVisible(false);
                cmbJK.setItems(null);
                lblFormKar.setText("Tambah Data Karyawan");
                setComboBox();
                OtomatisasiKaryawan();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Tanggal Lahir Belum Di Isi");
            alert.setContentText("Silahkan Isi Tanggal Lahir Terlebih Dahulu");
            alert.showAndWait();
        }
    }

    @FXML
    private void hapusKaryawanAction(ActionEvent event) throws SQLException {
        if(pilih_kar == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Karyawan Belum Dipilih");
            alert.setContentText("Silahkan pilih akun yang akan dipilih");
            alert.showAndWait();
        }else{
            String sql_hapusKar = "DELETE FROM kel2_karyawan WHERE kel2_id_karyawan = '"+pilih_kar+"'";
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText("Apakah Anda Yakin AkanMenghapusnya ?");
            alert.setContentText("Klik OK Untuk Setuju");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                pst = con.prepareStatement(sql_hapusKar);
                pst.executeUpdate();
                listKar.clear();
                setTabelKaryawan();
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle(title);
                alert1.setHeaderText("Data Karyawan Berhasil Dihapus");
                alert1.showAndWait();
                pilih_kar = null;
            }else{
                alert.close();
                pilih_kar = null;
            }
        }
    }

    @FXML
    private void simpanAction(ActionEvent event) {
        try{
            String nip = txtNIP.getText();
            String namaKar =txtNama.getText();
            String alamat = txtAlamat.getText();
            String telpKar = txtTelepon.getText();
            String jk = cmbJK.getSelectionModel().getSelectedItem().toString();
            if(nip.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("NIP Kosong");
                alert.setContentText("Silahkan isi NIP Terlebih Dahulu");
                alert.showAndWait();
                txtNIP.requestFocus();
            }else if(namaKar.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nama Kosong");
                alert.setContentText("Silahkan isi Nama Terlebih Dahulu");
                alert.showAndWait();
                txtNama.requestFocus();
            }else if(telpKar.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Nomor Kosong");
                alert.setContentText("Silahkan isi Nomor Telepon Terlebih Dahulu");
                alert.showAndWait();
                txtTelepon.requestFocus();
            }else if(alamat.equalsIgnoreCase("")){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Alamat Kosong");
                alert.setContentText("Silahkan isi Alamat Terlebih Dahulu");
                alert.showAndWait();
                txtAlamat.requestFocus();
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Jenis Kelamin Belum Dipilih");
            alert.setContentText("Silahkan Pilih Jenis Kelamin Terlebih Dahulu");
            alert.showAndWait();
        }
        try {
            String lahir = dateLahir.getValue().toString();
            int hari = dateLahir.getValue().getDayOfMonth();
            int bulan = dateLahir.getValue().getMonthValue();
            int tahun = dateLahir.getValue().getYear();
            if(tahun > now().getYear()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                alert.showAndWait();
            }else if(tahun == now().getYear()){
                if(bulan > now().getMonthValue()){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(title);
                    alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                    alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                    alert.showAndWait();
                }else{
                    if(hari > now().getDayOfMonth()){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle(title);
                        alert.setHeaderText("Tanggal Lahir yang Di Isi Tidak Valid !");
                        alert.setContentText("Silahkan Isi Tanggal Lahir yang Sesuai");
                        alert.showAndWait();
                    }else {
                        String sql_editKar = "UPDATE kel2_karyawan SET kel2_nip = '"+txtNIP.getText()+"', kel2_nama = '"+txtNama.getText()+"', kel2_tgl_lahir='"+dateLahir.getValue()+"', kel2_jk='"+cmbJK.getSelectionModel().getSelectedItem()+"', kel2_alamat='"+txtAlamat.getText()+"', kel2_telp = '"+txtTelepon.getText()+"' WHERE kel2_id_karyawan = '"+pilih_kar+"'";
                pst = con.prepareStatement(sql_editKar);
                pst.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText("Perubahan Data Karyawan Berhasil Disimpan");
                alert.showAndWait();
                listKar.clear();
                setTabelKaryawan();
                txtNIP.setText("");
                txtNama.setText("");
                txtAlamat.setText("");
                txtTelepon.setText("");
                dateLahir.setValue(null);
                btnTambah.setVisible(true);
                btnSimpan.setVisible(false);
                cmbJK.setItems(null);
                lblFormKar.setText("Tambah Data Karyawan");
                setComboBox();
                OtomatisasiKaryawan();
                    }
                }
            }else{
                String sql_editKar = "UPDATE kel2_karyawan SET kel2_nip = '"+txtNIP.getText()+"', kel2_nama = '"+txtNama.getText()+"', kel2_tgl_lahir='"+dateLahir.getValue()+"', kel2_jk='"+cmbJK.getSelectionModel().getSelectedItem()+"', kel2_alamat='"+txtAlamat.getText()+"', kel2_telp = '"+txtTelepon.getText()+"' WHERE kel2_id_karyawan = '"+pilih_kar+"'";
                pst = con.prepareStatement(sql_editKar);
                pst.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText("Perubahan Data Karyawan Berhasil Disimpan");
                alert.showAndWait();
                listKar.clear();
                setTabelKaryawan();
                txtNIP.setText("");
                txtNama.setText("");
                txtAlamat.setText("");
                txtTelepon.setText("");
                dateLahir.setValue(null);
                btnTambah.setVisible(true);
                btnSimpan.setVisible(false);
                cmbJK.setItems(null);
                lblFormKar.setText("Tambah Data Karyawan");
                setComboBox();
                OtomatisasiKaryawan();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Tanggal Lahir Belum Di Isi");
            alert.setContentText("Silahkan Isi Tanggal Lahir Terlebih Dahulu");
            alert.showAndWait();
        }
    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Apakah Anda Yakin Akan Logout ?");
        alert.setContentText("Klik OK Untuk Setuju");
        alert.showAndWait();
        if(alert.getResult() == ButtonType.OK){
            Parent home = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene homePage = new Scene(home);
            Stage appHome = (Stage) ((Node) event.getSource()) .getScene().getWindow();
            appHome.hide();
            appHome.setScene(homePage);
            appHome.show();
        }else{
            alert.close();
        }
    }
}
