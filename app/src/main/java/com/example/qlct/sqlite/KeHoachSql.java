package com.example.qlct.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlct.model.KeHoach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class KeHoachSql extends SQLiteOpenHelper {
    private Context context;
    public static final String TableName = "KeHoach";
    //tên các cột trong bảng
    public static final String MaKeHoach = "MaKeHoach";
    public static final String TenKeHoach = "TenKeHoach";
    public static final String ThoiGianBatDau = "ThoiGianBatDau";
    public static final String ThoiGianKetThuc = "ThoiGianKetThuc";
    public static final String HanMuc = "HanMuc";
    public static final String GhiChu = "GhiChu";
    public static final String MaKH = "MaKH";
    public static final String HoanThanh = "HoanThanh";
    public static final String IsDeleted = "IsDeleted";

    public KeHoachSql(Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + TableName + " ( "
                + MaKeHoach + " Integer Primary key AUTOINCREMENT, "
                +TenKeHoach+" Text, "
                +ThoiGianBatDau+" Text, "
                +ThoiGianKetThuc+" Text, "
                +HanMuc+" Integer, "
                +GhiChu+" Text, "
                + MaKH + " REAL, "
                +HoanThanh+" Integer, "
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
    public void addKeHoach(KeHoach keHoach)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TenKeHoach, keHoach.getTenKeHoach());
        value.put(ThoiGianBatDau, keHoach.getThoiGianBatDau().toString());
        value.put(ThoiGianKetThuc, keHoach.getThoiGianKetThuc().toString());
        value.put(HanMuc, keHoach.getHanMuc());
        value.put(GhiChu, keHoach.getGhiChu());
        value.put(MaKH, keHoach.getMaKH());
        value.put(HoanThanh, keHoach.getHoanThanh());
        value.put(IsDeleted, 1);
        long a=db.insert(TableName,null, value);
        db.close();
    }
    public void updateKeHoach( KeHoach keHoach )
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TenKeHoach, keHoach.getTenKeHoach());
        value.put(ThoiGianBatDau, keHoach.getThoiGianBatDau().toString());
        value.put(ThoiGianKetThuc, keHoach.getThoiGianKetThuc().toString());
        value.put(HanMuc, keHoach.getHanMuc());
        value.put(GhiChu, keHoach.getGhiChu());
        value.put(MaKH, keHoach.getMaKH());
        value.put(HoanThanh, keHoach.getHoanThanh());
        db.update(TableName, value,MaKeHoach + " =? ",
                new String[]{String.valueOf(keHoach.getMaKeHoach())});
        db.close();
    }
    public void updateKeHoachID(int id, int hoanthanh)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(HoanThanh, hoanthanh);
        db.update(TableName, value,MaKeHoach + " =? ",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteKeHoach(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(IsDeleted, 0);
        db.update(TableName, value,MaKeHoach + " =? ",
                new String[]{String.valueOf(id)});
        db.close();
    }
    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<KeHoach> getAllKeHoach(String maKH) throws ParseException {
        ArrayList<KeHoach> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where Isdeleted=1 and MaKH="+maKH;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor!=null)
            while (cursor.moveToNext())
            {
                KeHoach keHoach = new KeHoach(cursor.getInt(0),
                        cursor.getString(1),
                        (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2)),
                        (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );
                list.add(keHoach);
            }
        return list;
    }
    public ArrayList<KeHoach> getKeHoachChuaHT() throws ParseException {
        ArrayList<KeHoach> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where Isdeleted=1 "+" and HoanThanh=0";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor!=null)
            while (cursor.moveToNext())
            {
                KeHoach keHoach = new KeHoach(cursor.getInt(0),
                        cursor.getString(1),
                        (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(2)),
                        (Date) new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );
                list.add(keHoach);
            }
        return list;
    }
    public ArrayList<KeHoach> getListKeHoach(String maKH, int hoanthanh) throws ParseException {
        ArrayList<KeHoach> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where MaKH=\'" +maKH +"\'"+" and HoanThanh=" + hoanthanh;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                @SuppressLint("SimpleDateFormat") KeHoach keHoach = new KeHoach(cursor.getInt(0),
                        cursor.getString(1),
                         new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(2)),
                        new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );
                list.add(keHoach);
            }
        return list;
    }
    public ArrayList<KeHoach> getKeHoachHT(int id) throws ParseException {
        ArrayList<KeHoach> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where MaKeHoach="+id;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);

        if(cursor!=null)
            while (cursor.moveToNext())
            {
                KeHoach keHoach = new KeHoach(cursor.getInt(0),
                        cursor.getString(1),
                         new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(2)),
                         new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );
                list.add(keHoach);
            }
        return list;
    }

    public int getKeHoach(String maKH, int hoanthanh) throws ParseException {
        ArrayList<KeHoach> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where MaKH=\'" +maKH +"\'"+" and HoanThanh=" + hoanthanh;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                KeHoach keHoach = new KeHoach(cursor.getInt(0),
                        cursor.getString(1),
                        (Date) new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(2)),
                        (Date) new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault()).parse(cursor.getString(3)),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );
                list.add(keHoach);
            }
        return list.size();
    }
}
