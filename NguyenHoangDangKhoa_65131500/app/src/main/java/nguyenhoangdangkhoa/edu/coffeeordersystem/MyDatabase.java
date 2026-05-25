package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KCoffee.db";
    private static final int DATABASE_VERSION = 15;

    private static final String TABLE_NGUOI_DUNG = "NguoiDung";
    private static final String COL_SDT = "sodienthoai";
    private static final String COL_HO_TEN = "hoten";
    private static final String COL_MAT_KHAU = "matkhau";

    private static final String TABLE_SAN_PHAM = "SanPham";
    private static final String COL_MA_SP = "masanpham";
    private static final String COL_TEN_SP = "tensanpham";
    private static final String COL_GIA_SP = "giasanpham";

    private static final String TABLE_GIO_HANG = "GioHang";
    private static final String COL_ID_GIO_HANG = "id";
    private static final String COL_SDT_USER = "sdt_user";
    private static final String COL_TEN_MON_GIO = "ten_mon";
    private static final String COL_SO_LUONG_GIO = "so_luong";
    private static final String COL_GIA_MON_GIO = "gia_mon";

    private static final String TABLE_LICH_SU = "LichSuDonHang";
    private static final String COL_ID_LICH_SU = "id";
    private static final String COL_SDT_LICH_SU = "sdt_user";
    private static final String COL_THONG_TIN_DON = "thong_tin_don_hang";
    private static final String COL_TONG_TIEN_DON = "tong_tien";
    private static final String COL_NGAY_DAT = "ngay_dat";

    private static final String TABLE_VOUCHER = "VoucherNguoiDung";
    private static final String COL_ID_VOUCHER = "id";
    private static final String COL_SDT_VOUCHER = "sdt_user";
    private static final String COL_LOAI_VOUCHER = "loai_voucher";
    private static final String COL_TRANG_THAI_VOUCHER = "trang_thai";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableNguoiDung = "CREATE TABLE " + TABLE_NGUOI_DUNG + " ("
                + COL_SDT + " TEXT PRIMARY KEY, "
                + COL_HO_TEN + " TEXT, "
                + COL_MAT_KHAU + " TEXT, "
                + "diem_thuong INTEGER DEFAULT 0)";
        db.execSQL(createTableNguoiDung);

        db.execSQL("INSERT INTO " + TABLE_NGUOI_DUNG + " VALUES ('0854548595', 'Nguyễn Hoàng Đăng Khoa', '65131500', 0)");
        db.execSQL("INSERT INTO " + TABLE_NGUOI_DUNG + " VALUES ('0123456789', 'Nguyễn Văn A', '123456', 0)");

        String createTableSanPham = "CREATE TABLE " + TABLE_SAN_PHAM + " ("
                + COL_MA_SP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TEN_SP + " TEXT, "
                + COL_GIA_SP + " INTEGER)";
        db.execSQL(createTableSanPham);

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Cà Phê Đen', 25000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Cà Phê Sữa', 29000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Cà Phê Muối', 35000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Bạc Xỉu', 32000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Matcha Latte', 45000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Matcha Đá Xay', 50000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Chocolate Đá Xay', 50000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Nước Cam Ép', 35000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Nước Dừa Tươi', 30000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Trà Đào Cam Xả', 35000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Trà Trái Cây Nhiệt Đới', 45000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Sinh Tố Dâu', 45000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Sinh Tố Bơ', 45000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Kem Flan', 20000)");
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (tensanpham, giasanpham) VALUES ('Kem Bơ Đà Lạt', 40000)");

        String createTableGioHang = "CREATE TABLE " + TABLE_GIO_HANG + " ("
                + COL_ID_GIO_HANG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SDT_USER + " TEXT, "
                + COL_TEN_MON_GIO + " TEXT, "
                + COL_SO_LUONG_GIO + " INTEGER, "
                + COL_GIA_MON_GIO + " INTEGER)";
        db.execSQL(createTableGioHang);

        String createTableLichSu = "CREATE TABLE " + TABLE_LICH_SU + " ("
                + COL_ID_LICH_SU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SDT_LICH_SU + " TEXT, "
                + COL_THONG_TIN_DON + " TEXT, "
                + COL_TONG_TIEN_DON + " INTEGER, "
                + COL_NGAY_DAT + " TEXT)";
        db.execSQL(createTableLichSu);

        String createTableVoucher = "CREATE TABLE " + TABLE_VOUCHER + " ("
                + COL_ID_VOUCHER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SDT_VOUCHER + " TEXT, "
                + COL_LOAI_VOUCHER + " TEXT, "
                + COL_TRANG_THAI_VOUCHER + " INTEGER DEFAULT 0)";
        db.execSQL(createTableVoucher);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOI_DUNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIO_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LICH_SU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHER);
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
        String query = "SELECT * FROM " + TABLE_NGUOI_DUNG + " WHERE " + COL_SDT + " = ? AND " + COL_MAT_KHAU + " = ?";
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

    public Cursor getAllSanPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SAN_PHAM, null);
    }

    public void themHoacCapNhatGioHang(String sdtUser, String tenMon, int soLuong, int giaMon) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GIO_HANG + " WHERE " + COL_SDT_USER + " = ? AND " + COL_TEN_MON_GIO + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{sdtUser, tenMon});

        if (cursor != null && cursor.moveToFirst()) {
            int soLuongCu = cursor.getInt(3);
            int soLuongMoi = soLuongCu + soLuong;

            ContentValues values = new ContentValues();
            values.put(COL_SO_LUONG_GIO, soLuongMoi);

            db.update(TABLE_GIO_HANG, values, COL_SDT_USER + " = ? AND " + COL_TEN_MON_GIO + " = ?", new String[]{sdtUser, tenMon});
            cursor.close();
        } else {
            ContentValues values = new ContentValues();
            values.put(COL_SDT_USER, sdtUser);
            values.put(COL_TEN_MON_GIO, tenMon);
            values.put(COL_SO_LUONG_GIO, soLuong);
            values.put(COL_GIA_MON_GIO, giaMon);

            db.insert(TABLE_GIO_HANG, null, values);
        }
    }

    public Cursor getCartByUser(String sdtUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ID_GIO_HANG + " AS _id, " + COL_SDT_USER + ", " + COL_TEN_MON_GIO + ", " + COL_SO_LUONG_GIO + ", " + COL_GIA_MON_GIO + " FROM " + TABLE_GIO_HANG + " WHERE " + COL_SDT_USER + " = ?";
        return db.rawQuery(query, new String[]{sdtUser});
    }

    public void xoaSachGioHang(String sdtUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GIO_HANG, COL_SDT_USER + " = ?", new String[]{sdtUser});
    }

    public void luuDonHangVaoLichSu(String sdtUser, String thongTinDonHang, int tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        String currentDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put(COL_SDT_LICH_SU, sdtUser);
        values.put(COL_THONG_TIN_DON, thongTinDonHang);
        values.put(COL_TONG_TIEN_DON, tongTien);
        values.put(COL_NGAY_DAT, currentDateTime);

        db.insert(TABLE_LICH_SU, null, values);
    }

    public Cursor getLichSuByUser(String sdtUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ID_LICH_SU + " AS _id, " + COL_SDT_LICH_SU + ", " + COL_THONG_TIN_DON + ", " + COL_TONG_TIEN_DON + ", " + COL_NGAY_DAT + " FROM " + TABLE_LICH_SU + " WHERE " + COL_SDT_LICH_SU + " = ? ORDER BY id DESC";
        return db.rawQuery(query, new String[]{sdtUser});
    }

    public void congDiemTichLuy(String sdtUser, int diemCongThem) {
        SQLiteDatabase db = this.getWritableDatabase();
        int diemHienTai = 0;

        Cursor cursor = db.rawQuery("SELECT diem_thuong FROM " + TABLE_NGUOI_DUNG + " WHERE " + COL_SDT + " = ?", new String[]{sdtUser});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                diemHienTai = cursor.getInt(0);
            }
            cursor.close();
        }

        ContentValues values = new ContentValues();
        values.put("diem_thuong", diemHienTai + diemCongThem);
        db.update(TABLE_NGUOI_DUNG, values, COL_SDT + " = ?", new String[]{sdtUser});
    }

    public boolean truDiemTichLuy(String sdtUser, int diemTru) {
        SQLiteDatabase db = this.getWritableDatabase();
        int diemHienTai = 0;

        Cursor cursor = db.rawQuery("SELECT diem_thuong FROM " + TABLE_NGUOI_DUNG + " WHERE " + COL_SDT + " = ?", new String[]{sdtUser});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                diemHienTai = cursor.getInt(0);
            }
            cursor.close();
        }

        if (diemHienTai >= diemTru) {
            ContentValues values = new ContentValues();
            values.put("diem_thuong", diemHienTai - diemTru);
            db.update(TABLE_NGUOI_DUNG, values, COL_SDT + " = ?", new String[]{sdtUser});
            return true;
        }

        return false;
    }

    public void luuVoucherDaDoi(String sdtUser, String loaiVoucher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SDT_VOUCHER, sdtUser);
        values.put(COL_LOAI_VOUCHER, loaiVoucher);
        values.put(COL_TRANG_THAI_VOUCHER, 0);
        db.insert(TABLE_VOUCHER, null, values);
    }

    public Cursor getVouchersChuaDung(String sdtUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_ID_VOUCHER + " AS _id, " + COL_LOAI_VOUCHER + " FROM " + TABLE_VOUCHER + " WHERE " + COL_SDT_VOUCHER + " = ? AND " + COL_TRANG_THAI_VOUCHER + " = 0";
        return db.rawQuery(query, new String[]{sdtUser});
    }

    public void updateTrangThaiVoucher(int idVoucher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TRANG_THAI_VOUCHER, 1);
        db.update(TABLE_VOUCHER, values, COL_ID_VOUCHER + " = ?", new String[]{String.valueOf(idVoucher)});
    }
}