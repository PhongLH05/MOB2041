package fpoly.acount.codes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.codes.database.DBHelper;
import fpoly.acount.codes.model.ThanhVien;

public class ThanhVienDAO {
    DBHelper  dbHelper;
    SQLiteDatabase database;
    public ThanhVienDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> lst = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                lst.add(new ThanhVien(cursor.getInt(0), cursor  .getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }

        return lst;
    }

    public boolean themThanhVien(String hoten, String namsinh){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = database.insert("THANHVIEN", null, values);
        return check!= -1;
    }

    public boolean UpdateThanhVien(int matv, String hoten, String namsinh){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = database.update("THANHVIEN", values, "matv=?", new String[]{String.valueOf(matv)});
        return check != -1;
    }

    public int xoaThanhVien(int matv){
        database = dbHelper.getWritableDatabase();
        Cursor cursor= database.rawQuery("SELECT * FROM PHIEUMUON WHERE matv = ?", new String[]{String.valueOf(matv)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = database.delete("THANHVIEN", "matv = ?", new String[]{String.valueOf(matv)});
        if (check == -1){
            return 0;
        }

        return 1;
    }
}
