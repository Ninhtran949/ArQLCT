package com.example.qlct.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlct.model.LoaiChiTieu;

import java.text.ParseException;
import java.util.ArrayList;

public class LoaiChiTieuSql extends SQLiteOpenHelper {
    private Context context;
    public static final String TableName = "LoaiCT";
    //tên các cột trong bảng
    public static final String MaLoaiCT = "MaLoaiCT";
    public static final String TenLoaiCT = "TenLoaiCT";
    public static final String IsDeleted = "IsDeleted";

    public LoaiChiTieuSql(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + TableName + " ( "
                + MaLoaiCT + " Integer Primary key AUTOINCREMENT, "
                + TenLoaiCT + " Text, "
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
    public void addLoaiChiTieu(LoaiChiTieu loaiChiTieu)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MaLoaiCT, loaiChiTieu.getMaLoaiCT());
        value.put(TenLoaiCT, loaiChiTieu.getTenLoaiCT());
        value.put(IsDeleted, 1);

        long a=db.insert(TableName,null, value);
        db.close();
    }
    public void updateLoaiChiTieu( LoaiChiTieu loaiChiTieu)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(TenLoaiCT, loaiChiTieu.getTenLoaiCT());
        db.update(TableName, value,MaLoaiCT + " =? ",
                new String[]{String.valueOf(loaiChiTieu.getMaLoaiCT())});
        db.close();
    }
    public void deleteLoaiChiTieu(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(IsDeleted, 0);
        db.update(TableName, value,MaLoaiCT + " =? ",
                new String[]{String.valueOf(id)});
        db.close();
    }
    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<LoaiChiTieu> getAllLoaiCT() throws ParseException {
        ArrayList<LoaiChiTieu> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName +" where Isdeleted=1";
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                LoaiChiTieu loaiChiTieu = new LoaiChiTieu(cursor.getInt(0),
                        cursor.getString(1)
                );
                list.add(loaiChiTieu);
            }
        return list;
    }
}
