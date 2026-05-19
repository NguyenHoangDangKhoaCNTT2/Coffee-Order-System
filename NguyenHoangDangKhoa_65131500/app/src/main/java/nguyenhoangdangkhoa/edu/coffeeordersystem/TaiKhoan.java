package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        txtTenNguoiDung = view.findViewById(R.id.txtTenNguoiDung);
        txtSdtNguoiDung = view.findViewById(R.id.txtSdtNguoiDung);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            String hoTenUser = sharedPreferences.getString("luu_ho_ten", "Chưa cập nhật");
            String sdtUser = sharedPreferences.getString("luu_so_dien_thoai", "Chưa cập nhật");

            txtTenNguoiDung.setText("Họ và tên: " + hoTenUser);
            txtSdtNguoiDung.setText("Số điện thoại: " + sdtUser);
        }

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Đã đăng xuất tài khoản!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DangNhap.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        return view;
    }
}