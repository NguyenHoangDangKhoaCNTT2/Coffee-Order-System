package nguyenhoangdangkhoa.edu.coffeeordersystem;

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
                    finish();
                }
            }
        });
    }
}