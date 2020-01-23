/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kelompok2tabel;

/**
 *
 * @author Hulk
 */
public class TabelAdminAkun {
    String id_user,username,password,nama,telp,akses;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }


    public String getAkses() {
        return akses;
    }

    public void setAkses(String akses) {
        this.akses = akses;
    }

    public TabelAdminAkun(String id_user, String username, String password, String nama, String telp, String akses) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.telp = telp;
        this.akses = akses;
    }
    
}
