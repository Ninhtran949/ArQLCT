package com.example.qlct.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlct.model.ThuNhap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ThuNhapSql extends SQLiteOpenHelper {


    public static final String TableName = "ThuNhap";
    //tên các cột trong bảng
    public static final String MaThuNhap = "MaThuNhap";
    public static final String SoTien = "SoTien";
    public static final String MaKH = "MaKH";
    public static final String ThoiGianTN = "ThoiGianTN";
    public static final String GhiChu = "GhiChu";
    public static final String IsDeleted = "IsDeleted";

    public ThuNhapSql( Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + TableName + " ( "
                + MaThuNhap + " Integer Primary key AUTOINCREMENT, "
                + SoTien + " REAL, "
                +MaKH+" Text, "
                +ThoiGianTN+" Text, "
                +GhiChu+" Text, "
                + IsDeleted + " Integer ) ";

        //chạy câu truy vấn SQL để tạo bảng
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//xóa bảng TableContact đã có
        db.execSQL("Drop table if exists " + TableName);
        //tạo lại
        onCreate(db);
    }
    public void addThuNhap(ThuNhap thuNhap)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MaThuNhap, thuNhap.getMaThuNhap());
        value.put(SoTien, thuNhap.getSoTien());
        value.put(MaKH, thuNhap.getMaKH());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MM yyyy");
        value.put(ThoiGianTN, dateFormat.format(thuNhap.getThoiGianTN()));
        value.put(GhiChu, thuNhap.getGhiChu());
        value.put(IsDeleted, 1);
        db.insert(TableName, null, value);
        db.close();
    }
    public void updateThuNhap( ThuNhap thuNhap )
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(SoTien, thuNhap.getSoTien());
        value.put(MaKH, thuNhap.getMaKH());
        value.put(ThoiGianTN, thuNhap.getThoiGianTN().toString());
        value.put(GhiChu, thuNhap.getGhiChu());
        db.update(TableName, value,MaThuNhap + " =? ",
                new String[]{String.valueOf(thuNhap.getMaThuNhap())});
        db.close();
    }
    public void deleteThuNhap(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(IsDeleted, 0);
        db.update(TableName, value,MaThuNhap + " =? ",
                new String[]{String.valueOf(id)});
        db.close();
    }
    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<ThuNhap> getAllThuNhap(int maVi) throws ParseException {
        ArrayList<ThuNhap> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where Isdeleted=1 and MaVi = "+maVi;
        //lấy đối tượng csdl sqlite
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MM yyyy");
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                ThuNhap thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getString(2),
                        dateFormat.parse(cursor.getString(4)),
                        cursor.getString(4)
                );
                list.add(thuNhap);
            }
        return list;
    }
    public ArrayList<ThuNhap> getMonthThuNhap(String MaKH, int Month,int year) throws ParseException {
        ArrayList<ThuNhap> list = new ArrayList<>();
        //câu truy vấn
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MM yyyy");

        String sql = "Select * from " + TableName +" where Isdeleted=1 and MaKH="+MaKH
                +" and ThoiGianTN like %"+Month+" "+year+" % order by MaThuNhap";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                ThuNhap thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getString(2),
                        dateFormat.parse(cursor.getString(3)),
                        cursor.getString(4)
                );
                list.add(thuNhap);
            }
        return list;
    }

    public double getMoneyInMonthTN(String maKH, int Month,int year) throws ParseException {
        ArrayList<ThuNhap> list = new ArrayList<>();
        double tongTien = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MM yyyy");
        //câu truy vấn
        String sql = "Select * from " + TableName +" where Isdeleted=1 and MaKH=\'"+MaKH + "\'" + " and cast(ThoiGianTN as TEXT) like '%" +Month+" "+"3924%'";
//        +" and ThoiGianTN like %/"+Month+"/% order by MaChiTieu";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                @SuppressLint("SimpleDateFormat") ThuNhap thuNhap = new ThuNhap(cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getString(2),
                        dateFormat.parse(cursor.getString(3)),
                        cursor.getString(4)
                );
                tongTien += thuNhap.getSoTien();
            }
        return tongTien;
    }
}
