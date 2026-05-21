package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TichDiem extends Fragment {

    private TextView txtDiemTichLuy;
    private LinearLayout lnDoiVoucher10, lnDoiVoucher30, lnDoiVoucher50, lnDoiVoucherCafe;
    private MyDatabase dbHelper;
    private String sdtHienTai = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tich_diem, container, false);

        txtDiemTichLuy = view.findViewById(R.id.txtDiemTichLuy);
        lnDoiVoucher10 = view.findViewById(R.id.lnDoiVoucher10);
        lnDoiVoucher30 = view.findViewById(R.id.lnDoiVoucher30);
        lnDoiVoucher50 = view.findViewById(R.id.lnDoiVoucher50);
        lnDoiVoucherCafe = view.findViewById(R.id.lnDoiVoucherCafe);

        dbHelper = new MyDatabase(getActivity());

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

            taiSoDiemHienTai();
        }

        lnDoiVoucher10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeuCauXacNhanDoiQua("Voucher giảm 10%", 20);
            }
        });

        lnDoiVoucher30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeuCauXacNhanDoiQua("Voucher giảm 30%", 40);
            }
        });

        lnDoiVoucher50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeuCauXacNhanDoiQua("Voucher giảm 50%", 70);
            }
        });

        lnDoiVoucherCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeuCauXacNhanDoiQua("Voucher FREE Cà Phê Muối", 100);
            }
        });

        return view;
    }

    private void yeuCauXacNhanDoiQua(final String tenVoucher, final int soDiemCanTru) {
        if (getActivity() == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác Nhận Đổi Thưởng");
        builder.setMessage("Bạn có chắc chắn muốn dùng " + soDiemCanTru + " điểm để đổi \"" + tenVoucher + "\" không?");
        builder.setCancelable(false);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xulyDoiUuDai(tenVoucher, soDiemCanTru);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void xulyDoiUuDai(String tenVoucher, int soDiemCanTru) {
        if (sdtHienTai.isEmpty()) return;

        boolean thanhCong = dbHelper.truDiemTichLuy(sdtHienTai, soDiemCanTru);
        if (thanhCong) {
            String loaiMa = "";
            if (tenVoucher.contains("10%")) loaiMa = "Giam10";
            else if (tenVoucher.contains("30%")) loaiMa = "Giam30";
            else if (tenVoucher.contains("50%")) loaiMa = "Giam50";
            else if (tenVoucher.contains("Cà Phê Muối")) loaiMa = "FreeCafe";

            dbHelper.luuVoucherDaDoi(sdtHienTai, loaiMa);

            Toast.makeText(getActivity(), "Đổi thành công " + tenVoucher + ". Đã thêm vào ví ưu đãi của bạn!", Toast.LENGTH_LONG).show();
            taiSoDiemHienTai();
        } else {
            Toast.makeText(getActivity(), "Bạn không đủ điểm để đổi voucher này!", Toast.LENGTH_SHORT).show();
        }
    }

    private void taiSoDiemHienTai() {
        Cursor cursor = dbHelper.getThongTinNguoiDung(sdtHienTai);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idxDiem = cursor.getColumnIndex("diem_thuong");
                if (idxDiem != -1) {
                    int diemThuong = cursor.getInt(idxDiem);
                    txtDiemTichLuy.setText(diemThuong + " điểm");
                } else {
                    txtDiemTichLuy.setText("0 điểm");
                }
            }
            cursor.close();
        }
    }
}