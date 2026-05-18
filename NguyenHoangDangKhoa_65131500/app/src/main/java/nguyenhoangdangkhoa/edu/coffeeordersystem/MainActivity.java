package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView thanhMenuDuoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thanhMenuDuoi = findViewById(R.id.thanh_menu_duoi);

        getSupportFragmentManager().beginTransaction().replace(R.id.vung_chua_fragment, new TrangChu()).commit();

        thanhMenuDuoi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentDuocChon = null;
                int id = item.getItemId();

                if (id == R.id.itTrangChu) {
                    fragmentDuocChon = new TrangChu();
                } else if (id == R.id.itTichDiem) {
                    fragmentDuocChon = new TichDiem();
                } else if (id == R.id.itLichSu) {
                    fragmentDuocChon = new LichSu();
                } else if (id == R.id.itTaiKhoan) {
                    fragmentDuocChon = new TaiKhoan();
                }

                if (fragmentDuocChon != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.vung_chua_fragment, fragmentDuocChon).commit();
                }
                return true;
            }
        });
    }
}