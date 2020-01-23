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
public class TabelAdminKaryawan {
    String id_karyawan,nip,nama,tgl_lahir,jk,alamat,telp;

    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public TabelAdminKaryawan(String id_karyawan, String nip, String nama, String tgl_lahir, String jk, String alamat, String telp) {
        this.id_karyawan = id_karyawan;
        this.nip = nip;
        this.nama = nama;
        this.tgl_lahir = tgl_lahir;
        this.jk = jk;
        this.alamat = alamat;
        this.telp = telp;
    }
}
