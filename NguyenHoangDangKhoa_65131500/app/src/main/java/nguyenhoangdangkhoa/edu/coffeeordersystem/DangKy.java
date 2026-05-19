package nguyenhoangdangkhoa.edu.coffeeordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DangKy extends AppCompatActivity {

    EditText edtDKHoTen, edtDKSDT, edtDKMatKhau;
    Button btnDangKy;
    TextView txtQuayLaiDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        edtDKHoTen = findViewById(R.id.edtDKHoTen);
        edtDKSDT = findViewById(R.id.edtDKSDT);
        edtDKMatKhau = findViewById(R.id.edtDKMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        txtQuayLaiDangNhap = findViewById(R.id.txtQuayLaiDangNhap);

        txtQuayLaiDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = edtDKHoTen.getText().toString().trim();
                String sdt = edtDKSDT.getText().toString().trim();
                String matKhau = edtDKMatKhau.getText().toString().trim();

                if(hoTen.equals("") || sdt.equals("") || matKhau.equals("")) {
                    Toast.makeText(DangKy.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DangKy.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    android.content.SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", android.content.Context.MODE_PRIVATE);
                    android.content.SharedPreferences.Editor editor = sharedPreferences.edit();

                    String stringHoTen = edtDKHoTen.getText().toString().trim();
                    String stringSdt = edtDKSDT.getText().toString().trim();
                    editor.putString("luu_ho_ten", stringHoTen);
                    editor.putString("luu_so_dien_thoai", stringSdt);
                    editor.apply();
                    Intent intent = new Intent(DangKy.this, DangNhap.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}