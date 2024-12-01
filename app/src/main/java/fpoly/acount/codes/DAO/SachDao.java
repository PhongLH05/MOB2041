package fpoly.acount.codes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.codes.database.DBHelper;
import fpoly.acount.codes.model.Sach;

public class SachDao {
    private DBHelper dbHelper;

    public SachDao(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Sach> getDSSach(){
        ArrayList<Sach> lst = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM SACH", null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                lst.add(new Sach(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4)
                        ));
            }while (cursor.moveToNext());
        }

        cursor.close();

        return lst;
    }

    public boolean themSach(String tensach,String tacgia, int giaTien, int maLoai){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", tensach);
        values.put("tacgia", tacgia);
        values.put("giaban", giaTien);
        values.put("maloai", maLoai);

        long chekc = database.insert("SACH", null, values);
        return chekc != -1;
    }

    public boolean updateSach(int masach, String tensach, String tacgia, int giaban, int maloai){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", tensach);
        values.put("tacgia", tacgia);
        values.put("giaban", giaban);
        values.put("maloai", maloai);

        long check = database.update("SACH", values, "masach = ?", new String[]{String.valueOf(masach)});

        return check != -1;
    }

    public int deleteSach(int masach){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = database.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});

        if (check == -1){
            return 0;
        }

        return 1;
    }

}
