package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class TrangChu extends Fragment {

    private TextView txtNguoiDung;
    private MyDatabase dbHelper;
    private TextView[] txtTenMonArray = new TextView[15];
    private TextView[] txtGiaMonArray = new TextView[15];
    private Button[] btnMuaMonArray = new Button[15];
    private int soLuongHienTai = 1;
    private String sdtHienTai = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        txtNguoiDung = view.findViewById(R.id.txtNguoiDung);
        dbHelper = new MyDatabase(getActivity());

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

            Cursor cursorUser = dbHelper.getThongTinNguoiDung(sdtHienTai);
            if (cursorUser != null && cursorUser.moveToFirst()) {
                String hoTenTuSQL = cursorUser.getString(1);
                txtNguoiDung.setText("Chào bạn " + hoTenTuSQL + "!");
                cursorUser.close();
            } else {
                txtNguoiDung.setText("Chào bạn!");
            }
        }

        for (int i = 0; i < 15; i++) {
            String tenID = "txtTenMon" + (i + 1);
            String giaID = "txtGiaMon" + (i + 1);
            String btnID = "btnMuaMon" + (i + 1);

            int resTenID = getResources().getIdentifier(tenID, "id", getActivity().getPackageName());
            int resGiaID = getResources().getIdentifier(giaID, "id", getActivity().getPackageName());
            int resBtnID = getResources().getIdentifier(btnID, "id", getActivity().getPackageName());

            txtTenMonArray[i] = view.findViewById(resTenID);
            txtGiaMonArray[i] = view.findViewById(resGiaID);
            btnMuaMonArray[i] = view.findViewById(resBtnID);
        }

        Cursor cursorProduct = dbHelper.getAllSanPham();
        if (cursorProduct != null) {
            int index = 0;
            while (cursorProduct.moveToNext() && index < 15) {
                String tenMonTuSQL = cursorProduct.getString(1);
                int giaMonTuSQL = cursorProduct.getInt(2);

                if (txtTenMonArray[index] != null) {
                    txtTenMonArray[index].setText(tenMonTuSQL);
                }
                if (txtGiaMonArray[index] != null) {
                    txtGiaMonArray[index].setText(String.format("%,d đ", giaMonTuSQL).replace(',', '.'));
                }

                final String tenMonChon = tenMonTuSQL;
                final int giaMonChon = giaMonTuSQL;
                if (btnMuaMonArray[index] != null) {
                    btnMuaMonArray[index].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moBottomSheetMuaHang(tenMonChon, giaMonChon);
                        }
                    });
                }

                index++;
            }
            cursorProduct.close();
        }

        return view;
    }

    private void moBottomSheetMuaHang(String tenMon, int giaMon) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bottom_mua, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView txtBSName = bottomSheetView.findViewById(R.id.txtTenMonMua);
        TextView txtBSPrice = bottomSheetView.findViewById(R.id.txtGiaMonMua);
        TextView txtBSSoLuong = bottomSheetView.findViewById(R.id.txtSoLuongMua);
        Button btnGiam = bottomSheetView.findViewById(R.id.btnGiamSoLuong);
        Button btnTang = bottomSheetView.findViewById(R.id.btnTangSoLuong);
        Button btnXacNhan = bottomSheetView.findViewById(R.id.btnXacNhanGioHang);

        txtBSName.setText(tenMon);
        txtBSPrice.setText(String.format("%,d đ", giaMon).replace(',', '.'));

        soLuongHienTai = 1;
        txtBSSoLuong.setText(String.valueOf(soLuongHienTai));

        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuongHienTai++;
                txtBSSoLuong.setText(String.valueOf(soLuongHienTai));
            }
        });

        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuongHienTai > 1) {
                    soLuongHienTai--;
                    txtBSSoLuong.setText(String.valueOf(soLuongHienTai));
                }
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    if (sdtHienTai == null || sdtHienTai.isEmpty()) {
                        Toast.makeText(getActivity(), "Bạn cần đăng nhập/đăng ký để đặt nước!", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        return;
                    }

                    dbHelper.themHoacCapNhatGioHang(sdtHienTai, tenMon, soLuongHienTai, giaMon);
                    String message = "Đã thêm " + soLuongHienTai + " x " + tenMon + " vào giỏ hàng!";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            }
        });

        bottomSheetDialog.show();
    }
}