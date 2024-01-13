package com.example.qlct.model;

import java.io.Serializable;
import java.util.Date;

public class ThuNhap implements Serializable {
    private  int MaThuNhap;
    private double SoTien;
    private  String MaKH;
    private Date ThoiGianTN;
    private String GhiChu;

    public ThuNhap(int maThuNhap, double soTien, String maKH, Date thoiGianTN, String ghiChu) {
        MaThuNhap = maThuNhap;
        SoTien = soTien;
        MaKH = maKH;
        ThoiGianTN = thoiGianTN;
        GhiChu = ghiChu;
    }

    public int getMaThuNhap() {
        return MaThuNhap;
    }

    public void setMaThuNhap(int maThuNhap) {
        MaThuNhap = maThuNhap;
    }

    public double getSoTien() {
        return SoTien;
    }

    public void setSoTien(double soTien) {
        SoTien = soTien;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public Date getThoiGianTN() {
        return ThoiGianTN;
    }

    public void setThoiGianTN(Date thoiGianTN) {
        ThoiGianTN = thoiGianTN;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
