package fpoly.acount.codes.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.codes.database.DBHelper;
import fpoly.acount.codes.model.Sach;

public class Top10DAO {
    DBHelper dbHelper;
    public Top10DAO(Context context){
        dbHelper= new DBHelper(context);
    }

    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> lst = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);
        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {
                lst.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return lst;
    }

    public int getDoanhThu(String ngaybatdau, String ngaykethuc){
        ngaybatdau = ngaybatdau.replace("/", "");
        ngaykethuc = ngaykethuc.replace("/", "");
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON WHERE substr(ngay, 7) || substr(ngay,4,2) || substr(ngay,1,2) BETWEEN ? AND ?", new String[]{ngaybatdau, ngaykethuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
