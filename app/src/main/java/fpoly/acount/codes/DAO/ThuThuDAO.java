package fpoly.acount.codes.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpoly.acount.codes.database.DBHelper;

public class ThuThuDAO {
    DBHelper dbHelper;
    SharedPreferences sharedPreference;


    public ThuThuDAO(Context context){
        dbHelper = new DBHelper(context);
        sharedPreference = context.getSharedPreferences("info", MODE_PRIVATE);
    }

    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{matt, matkhau});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.putString("hoten", cursor.getString(1));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }

    public boolean UpdatePass(String username, String oldPass, String newPass){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            long check = database.update("THUTHU", values, "matt = ?", new String[]{username});
            return check != -1;
        }
        return false;
    }
}
