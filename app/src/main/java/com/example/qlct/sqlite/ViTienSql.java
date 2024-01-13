package com.example.qlct.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlct.model.ThuNhap;
import com.example.qlct.model.ViTien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViTienSql extends SQLiteOpenHelper {
    private Context context;
    public static final String TableName = "ViTien";
    //tên các cột trong bảng
    public static final String MaVi = "MaVi";
    public static final String LoaiVi = "LoaiVi";
    public static final String SoTien = "SoTien";
    public static final String MaKH = "MaKH";
    public static final String IsDeleted = "IsDeleted";

    public ViTienSql( Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + TableName + " ( "
                + MaVi + " Integer Primary key AUTOINCREMENT, "
                + LoaiVi + " Text, "
                +SoTien+" REAL, "
                +MaKH+" Text, "
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
    public void addViTien(ViTien viTien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(LoaiVi, viTien.getLoaiVi());
        value.put(SoTien, viTien.getSoTien());
        value.put(MaKH, viTien.getMaKH());
        value.put(IsDeleted, 1);

        long a=db.insert(TableName,null, value);
        db.close();
    }
    public void updateViTien( ViTien viTien )
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(LoaiVi, viTien.getLoaiVi());
        value.put(SoTien, viTien.getSoTien());
        value.put(MaKH, viTien.getMaKH());
        db.update(TableName, value,MaVi + " =? ",
                new String[]{String.valueOf(viTien.getMaVi())});
        db.close();
    }
    public void updateTienTangGiam( Double tien,String maKH)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(SoTien, tien);
        db.update(TableName, value,MaKH + " =? ",
                new String[]{maKH});
        db.close();
    }
    public void deleteViTien(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(IsDeleted, 0);
        db.update(TableName, value,MaVi + " =? ",
                new String[]{String.valueOf(id)});
        db.close();
    }
    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<ViTien> getViTien(String maKH) throws ParseException {
        ArrayList<ViTien> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName + " where MaKH=\'"+maKH + "\'";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                ViTien viTien = new ViTien(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                );
                list.add(viTien);
            }
        return list;
    }
    public boolean hasViTien(String maKH) throws ParseException {
        ArrayList<ViTien> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName + " where MaKH=\'"+maKH + "\'";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                ViTien viTien = new ViTien(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                );
                list.add(viTien);
            }
        if(list.size() == 0) {
            return false;
        }
        return true;
    }
}
