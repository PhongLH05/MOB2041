package fpoly.acount.codes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.codes.database.DBHelper;
import fpoly.acount.codes.model.LoaiSach;

public class LoaiSachDAO {
    private DBHelper dbHelper;

    public LoaiSachDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<LoaiSach> getDSLoaiSach (){
        ArrayList<LoaiSach> lst = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                lst.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        cursor.close();

        return lst;
    }


    public boolean themLoaiSach(String tenLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenloai", tenLoai);
        long check = sqLiteDatabase.insert("LOAISACH", null, values);
        return check != -1;
    }

    public boolean suaLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put("tenloai", loaiSach.getTenloai());

        int check = sqLiteDatabase.update("LOAISACH", values, "maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        return check != 0;
    }

    public int xoaLoaiSach(int maLoai){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(maLoai)});
        if (cursor.getCount() > 0){
            return 0;
        }else {
            int check = database.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(maLoai)});
            if (check == 0) return -1;
            else return 1;
        }
    }
}
