package fpoly.acount.codes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, "QUANLYTHUVIEN", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tLoaiSach = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        sqLiteDatabase.execSQL(tLoaiSach);

        sqLiteDatabase.execSQL("INSERT INTO LOAISACH (maloai,tenloai) VALUES ('1','thieu nhi'),('2','Lich Su'),('3','Action'); \n" +
                "\n");

        String tSach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, tacgia text, giaban integer, maloai integer references LOAISACH(maloai))";
        sqLiteDatabase.execSQL(tSach);

        sqLiteDatabase.execSQL("INSERT INTO SACH (masach,tensach,tacgia,giaban,maloai) VALUES ('1','Hoàng tử bé','Antoine de Saint-Exupéry','20000', '1'), ('2','Những cuộc phiêu lưu của Pinocchio','Carlo Collodi','30000', '1');");

        String tThuThu = "CREATE TABLE THUTHU(matt text primary key , hoten text, matkhau text, loaitaikhoan text)";
        sqLiteDatabase.execSQL(tThuThu);

        sqLiteDatabase.execSQL("INSERT INTO THUTHU(matt, hoten, matkhau, loaitaikhoan) VALUES ('admin', 'Le Hong Phong', '123456', 'admin'), ('thuthu', 'Nguyen Van B', '123456', 'thuthu')");

        String tNguoiDung = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        sqLiteDatabase.execSQL(tNguoiDung);

        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN(matv, hoten, namsinh) VALUES ('1', 'Le Ba Phong', '12/11/2002'), ('2', 'Nguyen Van A', '11/12/2022'), ('3', 'thanhvien', '12/12/2012')");

        String tPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement, matv integer references THANHVIEN(matv), matt text references THUTHU(matt), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        sqLiteDatabase.execSQL(tPhieuMuon);

        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON VALUES (1, 1, 'admin',1 , '12/12/2024', 1, 20000)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            onCreate(sqLiteDatabase);
        }
    }
}
