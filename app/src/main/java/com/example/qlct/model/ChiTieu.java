package com.example.qlct.model;

import java.io.Serializable;
import java.util.Date;

public class ChiTieu implements Serializable {
    private int maChiTieu;
    private double soTien;
    private String maKH;
    private String loaiCT;
    private Date tgCT;
    private String ghiChu;

    public ChiTieu(int maChiTieu, double soTien, String maKH, String loaiCT, Date tgCT, String ghiChu) {
        this.maChiTieu = maChiTieu;
        this.soTien = soTien;
        this.maKH = maKH;
        this.loaiCT = loaiCT;
        this.tgCT = tgCT;
        this.ghiChu = ghiChu;
    }

    public int getMaChiTieu() {
        return maChiTieu;
    }

    public void setMaChiTieu(int maChiTieu) {
        this.maChiTieu = maChiTieu;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getLoaiCT() {
        return loaiCT;
    }

    public void setLoaiCT(String loaiCT) {
        this.loaiCT = loaiCT;
    }

    public Date getTgCT() {
        return tgCT;
    }

    public void setTgCT(Date tgCT) {
        this.tgCT = tgCT;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
