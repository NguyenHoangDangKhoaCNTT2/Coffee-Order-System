package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LichSu extends Fragment {

    private ListView lvLichSu;
    private MyDatabase dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_su, container, false);

        lvLichSu = view.findViewById(R.id.lvLichSu);
        dbHelper = new MyDatabase(getActivity());

        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
            String sdtHienTai = sharedPreferences.getString("sdt_dang_nhap", "");

            Cursor cursor = dbHelper.getLichSuByUser(sdtHienTai);

            if (cursor != null) {
                String[] fromColumns = {"thong_tin_don_hang", "tong_tien", "ngay_dat"};
                int[] toViews = {R.id.txtItemThongTinDon, R.id.txtItemTongTienLichSu, R.id.txtItemNgayDat};

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.layout_item_lich_su,
                        cursor,
                        fromColumns,
                        toViews,
                        0
                );

                adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        if (view.getId() == R.id.txtItemTongTienLichSu) {
                            int tongTien = cursor.getInt(columnIndex);
                            ((TextView) view).setText(String.format("%,d đ", tongTien).replace(',', '.'));
                            return true;
                        }
                        return false;
                    }
                });

                lvLichSu.setAdapter(adapter);
            }
        }

        return view;
    }
}