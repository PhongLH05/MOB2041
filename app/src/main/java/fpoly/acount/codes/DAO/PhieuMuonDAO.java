package fpoly.acount.codes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.acount.codes.database.DBHelper;
import fpoly.acount.codes.model.PhieuMuon;

public class PhieuMuonDAO {
    private DBHelper dbHelper;
    SQLiteDatabase database;

    public PhieuMuonDAO(Context context) {
         dbHelper = new DBHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon (){
        ArrayList<PhieuMuon> lst = new ArrayList<>();
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm desc", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                lst.add(new PhieuMuon(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return lst;
    }

    public boolean changeStatus(int mapm){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trasach", 1);
        long check = database.update("PHIEUMUON", values, "mapm=?", new String[]{String.valueOf(mapm)});

        return check != -1;
    }

    public boolean ThemPhieuMuon(PhieuMuon phieuMuon){
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("mapm", phieuMuon.getMapm());
        values.put("matv", phieuMuon.getMatv());
        values.put("matt", phieuMuon.getMatt());
        values.put("masach", phieuMuon.getMasach());
        values.put("ngay", phieuMuon.getNgay());
        values.put("trasach", phieuMuon.getTrasach());
        values.put("tienthue", phieuMuon.getTienthue());


        long check = database.insert("PHIEUMUON", null, values);
        return  check != -1;
    }

}
