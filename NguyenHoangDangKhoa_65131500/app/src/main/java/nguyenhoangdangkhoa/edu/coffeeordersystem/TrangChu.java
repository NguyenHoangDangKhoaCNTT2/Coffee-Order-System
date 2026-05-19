package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrangChu extends Fragment {

    private TextView txtNguoiDung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        txtNguoiDung = view.findViewById(R.id.txtNguoiDung);

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            String hoTenUser = sharedPreferences.getString("luu_ho_ten", "Khách Hàng");

            txtNguoiDung.setText("Chào bạn, " + hoTenUser + " 👋");
        }

        return view;
    }
}