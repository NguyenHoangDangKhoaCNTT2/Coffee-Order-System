package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GioHang extends Fragment {

    private ListView lvGioHang;
    private TextView txtTongTien;
    private Button btnThanhToan;
    private MyDatabase dbHelper;
    private LinearLayout lnChonVoucher;
    private TextView txtHienThiVoucherDaChon;
    private int idVoucherDuocChon = -1;
    private String loaiVoucherDuocChon = "";
    private int tongTienGoc = 0;
    private int tongTienSauKhiGiam = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        lvGioHang = view.findViewById(R.id.lvGioHang);
        txtTongTien = view.findViewById(R.id.txtTongTienGioHang);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);

        lnChonVoucher = view.findViewById(R.id.lnChonVoucher);
        txtHienThiVoucherDaChon = view.findViewById(R.id.txtHienThiVoucherDaChon);

        dbHelper = new MyDatabase(getActivity());

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            String sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

            Cursor cursor = dbHelper.getCartByUser(sdtHienTai);

            if (cursor != null) {
                tongTienGoc = 0;
                while (cursor.moveToNext()) {
                    int soLuong = cursor.getInt(3);
                    int giaMon = cursor.getInt(4);
                    tongTienGoc += (soLuong * giaMon);
                }

                tongTienSauKhiGiam = tongTienGoc;
                txtTongTien.setText(String.format("%,d đ", tongTienSauKhiGiam).replace(',', '.'));

                cursor.moveToPosition(-1);

                String[] fromColumns = {"ten_mon", "gia_mon", "so_luong"};
                int[] toViews = {R.id.txtItemTenMonGio, R.id.txtItemGiaMonGio, R.id.txtItemSoLuongGio};

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.layout_item_gio_hang,
                        cursor,
                        fromColumns,
                        toViews,
                        0
                );

                adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        if (view.getId() == R.id.txtItemGiaMonGio) {
                            int gia = cursor.getInt(columnIndex);
                            ((TextView) view).setText(String.format("%,d đ", gia).replace(',', '.'));
                            return true;
                        }
                        if (view.getId() == R.id.txtItemSoLuongGio) {
                            int sl = cursor.getInt(columnIndex);
                            ((TextView) view).setText("x" + sl);
                            return true;
                        }
                        return false;
                    }
                });

                lvGioHang.setAdapter(adapter);
            }

            lnChonVoucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tongTienGoc == 0) {
                        Toast.makeText(getActivity(), "Hãy thêm món vào giỏ trước khi chọn voucher nhé!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    moBottomSheetChonVoucher(sdtHienTai);
                }
            });

            btnThanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursorXuly = dbHelper.getCartByUser(sdtHienTai);
                    if (cursorXuly != null && cursorXuly.getCount() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();

                        while (cursorXuly.moveToNext()) {
                            String tenMon = cursorXuly.getString(2);
                            int soLuong = cursorXuly.getInt(3);
                            stringBuilder.append(tenMon).append(" (x").append(soLuong).append("), ");
                        }
                        cursorXuly.close();

                        String thongTinDonHang = stringBuilder.toString();
                        if (thongTinDonHang.endsWith(", ")) {
                            thongTinDonHang = thongTinDonHang.substring(0, thongTinDonHang.length() - 2);
                        }

                        dbHelper.luuDonHangVaoLichSu(sdtHienTai, thongTinDonHang, tongTienSauKhiGiam);

                        if (idVoucherDuocChon != -1) {
                            dbHelper.updateTrangThaiVoucher(idVoucherDuocChon);
                        }

                        int diemTichLuy = tongTienSauKhiGiam / 10000;
                        if (diemTichLuy > 0) {
                            dbHelper.congDiemTichLuy(sdtHienTai, diemTichLuy);
                        }

                        dbHelper.xoaSachGioHang(sdtHienTai);

                        tongTienGoc = 0;
                        tongTienSauKhiGiam = 0;
                        idVoucherDuocChon = -1;
                        loaiVoucherDuocChon = "";
                        txtHienThiVoucherDaChon.setText("Chọn ưu đãi của bạn");
                        txtTongTien.setText("0 đ");
                        lvGioHang.setAdapter(null);

                        Toast.makeText(getActivity(), "Thanh toán thành công! Bạn được tích + " + diemTichLuy + " điểm.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Giỏ hàng trống, không thể thanh toán!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return view;
    }

    private void moBottomSheetChonVoucher(String sdtUser) {
        if (getActivity() == null) return;

        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        View sheetView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bottom_voucher, null);
        dialog.setContentView(sheetView);

        ListView lvVoucherList = sheetView.findViewById(R.id.lvVoucherList);

        final Cursor cursorVoucher = dbHelper.getVouchersChuaDung(sdtUser);

        if (cursorVoucher != null && cursorVoucher.getCount() > 0) {
            String[] from = {"loai_voucher"};
            int[] to = {android.R.id.text1};

            SimpleCursorAdapter voucherAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursorVoucher,
                    from,
                    to,
                    0
            );

            voucherAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    if (view instanceof TextView) {
                        String code = cursor.getString(columnIndex);
                        if (code.equals("Giam10")) {
                            ((TextView) view).setText("Mã giảm giá 10% đơn hàng");
                        } else if (code.equals("Giam30")) {
                            ((TextView) view).setText("Mã giảm giá 30% đơn hàng");
                        } else if (code.equals("Giam50")) {
                            ((TextView) view).setText("Mã giảm giá 50% đơn hàng");
                        } else if (code.equals("FreeCafe")) {
                            ((TextView) view).setText("Đổi 1 ly Cà Phê Muối miễn phí");
                        }
                        return true;
                    }
                    return false;
                }
            });

            lvVoucherList.setAdapter(voucherAdapter);

            lvVoucherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (cursorVoucher.moveToPosition(position)) {
                        idVoucherDuocChon = cursorVoucher.getInt(0);
                        loaiVoucherDuocChon = cursorVoucher.getString(1);

                        if (loaiVoucherDuocChon.equals("Giam10")) {
                            tongTienSauKhiGiam = (int) (tongTienGoc * 0.9);
                            txtHienThiVoucherDaChon.setText("Đã áp dụng mã Giảm 10%");
                        } else if (loaiVoucherDuocChon.equals("Giam30")) {
                            tongTienSauKhiGiam = (int) (tongTienGoc * 0.7);
                            txtHienThiVoucherDaChon.setText("Đã áp dụng mã Giảm 30%");
                        } else if (loaiVoucherDuocChon.equals("Giam50")) {
                            tongTienSauKhiGiam = (int) (tongTienGoc * 0.5);
                            txtHienThiVoucherDaChon.setText("Đã áp dụng mã Giảm 50%");
                        } else if (loaiVoucherDuocChon.equals("FreeCafe")) {
                            tongTienSauKhiGiam = Math.max(0, tongTienGoc - 35000);
                            txtHienThiVoucherDaChon.setText("Đã áp dụng mã FREE Cà Phê Muối");
                        }

                        txtTongTien.setText(String.format("%,d đ", tongTienSauKhiGiam).replace(',', '.'));
                        dialog.dismiss();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Ví ưu đãi của bạn đang trống. Hãy tích điểm đổi quà nhé!", Toast.LENGTH_LONG).show();
            if (cursorVoucher != null) cursorVoucher.close();
            return;
        }

        dialog.show();
    }
}