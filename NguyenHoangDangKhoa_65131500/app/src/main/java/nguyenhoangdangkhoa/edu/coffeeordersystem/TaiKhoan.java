package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaiKhoan extends Fragment {
    private TextView txtTenNguoiDung, txtSdtNguoiDung;
    private Button btnDangXuat;
    private MyDatabase dbHelper;
    private String sdtHienTai = "";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        txtTenNguoiDung = view.findViewById(R.id.txtTenNguoiDung);
        txtSdtNguoiDung = view.findViewById(R.id.txtSdtNguoiDung);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        dbHelper = new MyDatabase(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    if (sdtHienTai.isEmpty()) {
                        Intent intent = new Intent(getActivity(), DangNhap.class);
                        startActivity(intent);
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Toast.makeText(getActivity(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
                        onResume();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

        if (sdtHienTai == null || sdtHienTai.isEmpty()) {
            txtSdtNguoiDung.setText("Số điện thoại: Chưa đăng nhập");
            txtTenNguoiDung.setText("Họ và tên: Chưa đăng nhập");
            btnDangXuat.setText("ĐĂNG NHẬP / ĐĂNG KÝ");
        } else {
            txtSdtNguoiDung.setText("Số điện thoại: " + sdtHienTai);
            btnDangXuat.setText("ĐĂNG XUẤT");

            Cursor cursor = dbHelper.getThongTinNguoiDung(sdtHienTai);
            if (cursor != null && cursor.moveToFirst()) {
                String hoTen = cursor.getString(1);
                txtTenNguoiDung.setText("Họ và tên: " + hoTen);
                cursor.close();
            } else {
                txtTenNguoiDung.setText("Họ và tên: Khách hàng");
            }
        }
    }
}