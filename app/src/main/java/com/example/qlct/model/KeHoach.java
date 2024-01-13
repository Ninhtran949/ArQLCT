package com.example.qlct.model;

import java.io.Serializable;
import java.util.Date;

public class KeHoach implements Serializable{
    private int maKeHoach;
    private String tenKeHoach;
    private Date thoiGianBatDau ;
    private Date thoiGianKetThuc ;
    private double hanMuc;
    private String ghiChu;
    private String maKH;
    private int hoanThanh;

    public KeHoach(int maKeHoach, String tenKeHoach, Date thoiGianBatDau, Date thoiGianKetThuc, double hanMuc, String ghiChu, String maKH, int HoanThanh) {
        this.maKeHoach = maKeHoach;
        this.tenKeHoach = tenKeHoach;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.hanMuc = hanMuc;
        this.ghiChu = ghiChu;
        this.maKH = maKH;
        this.hoanThanh = HoanThanh;
    }

    public int getMaKeHoach() {
        return maKeHoach;
    }

    public void setMaKeHoach(int maKeHoach) {
        this.maKeHoach = maKeHoach;
    }

    public String getTenKeHoach() {
        return tenKeHoach;
    }

    public void setTenKeHoach(String tenKeHoach) {
        this.tenKeHoach = tenKeHoach;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public double getHanMuc() {
        return hanMuc;
    }

    public void setHanMuc(double hanMuc) {
        this.hanMuc = hanMuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getHoanThanh() {
        return hoanThanh;
    }

    public void setHoanThanh(int hoanThanh) {
        this.hoanThanh = hoanThanh;
    }
}
