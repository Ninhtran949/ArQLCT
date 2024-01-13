package com.example.qlct.model;

public class ViTien {
    private int maVi;
    private String loaiVi;
    private double soTien;
    private String maKH;

    public ViTien(int maVi, String loaiVi, double soTien, String maKH) {
        this.maVi = maVi;
        this.loaiVi = loaiVi;
        this.soTien = soTien;
        this.maKH = maKH;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public String getLoaiVi() {
        return loaiVi;
    }

    public void setLoaiVi(String loaiVi) {
        this.loaiVi = loaiVi;
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
}
