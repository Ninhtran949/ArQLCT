package com.example.qlct.model;

public class LoaiChiTieu {
    private int maLoaiCT;
    private  String tenLoaiCT;

    public LoaiChiTieu(int maLoaiCT, String tenLoaiCT) {
        this.maLoaiCT = maLoaiCT;
        this.tenLoaiCT = tenLoaiCT;
    }

    public int getMaLoaiCT() {
        return maLoaiCT;
    }

    public void setMaLoaiCT(int maLoaiCT) {
        this.maLoaiCT = maLoaiCT;
    }

    public String getTenLoaiCT() {
        return tenLoaiCT;
    }

    public void setTenLoaiCT(String tenLoaiCT) {
        this.tenLoaiCT = tenLoaiCT;
    }
}
