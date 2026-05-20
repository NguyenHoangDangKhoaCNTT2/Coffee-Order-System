package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrangChu extends Fragment {

    private TextView txtNguoiDung;
    private MyDatabase dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        txtNguoiDung = view.findViewById(R.id.txtNguoiDung);
        dbHelper = new MyDatabase(getActivity());

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            String sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

            Cursor cursor = dbHelper.getThongTinNguoiDung(sdtHienTai);
            if (cursor != null && cursor.moveToFirst()) {
                String hoTenTuSQL = cursor.getString(1);

                txtNguoiDung.setText("Chào bạn, " + hoTenTuSQL + "!");
                cursor.close();
            }
        }

        return view;
    }
}