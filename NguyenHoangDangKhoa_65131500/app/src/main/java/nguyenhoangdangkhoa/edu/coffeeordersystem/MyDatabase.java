package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CoffeeShop.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NGUOI_DUNG = "NguoiDung";
    private static final String COL_SDT = "sodienthoai";
    private static final String COL_HO_TEN = "hoten";
    private static final String COL_MAT_KHAU = "matkhau";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableNguoiDung = "CREATE TABLE " + TABLE_NGUOI_DUNG + " ("
                + COL_SDT + " TEXT PRIMARY KEY, "
                + COL_HO_TEN + " TEXT, "
                + COL_MAT_KHAU + " TEXT)";
        db.execSQL(createTableNguoiDung);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOI_DUNG);
        onCreate(db);
    }

    public boolean insertNguoiDung(String sdt, String hoTen, String matKhau) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SDT, sdt);
        values.put(COL_HO_TEN, hoTen);
        values.put(COL_MAT_KHAU, matKhau);

        long result = db.insert(TABLE_NGUOI_DUNG, null, values);
        return result != -1;
    }

    public boolean checkDangNhap(String sdt, String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NGUOI_DUNG + " WHERE "
                + COL_SDT + " = ? AND " + COL_MAT_KHAU + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{sdt, matKhau});

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Cursor getThongTinNguoiDung(String sdt) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NGUOI_DUNG + " WHERE " + COL_SDT + " = ?";
        return db.rawQuery(query, new String[]{sdt});
    }
}