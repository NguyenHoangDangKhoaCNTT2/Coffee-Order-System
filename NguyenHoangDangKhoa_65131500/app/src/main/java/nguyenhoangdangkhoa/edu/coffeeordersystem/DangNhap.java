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

public class DangNhap extends AppCompatActivity {

    EditText edtSDT, edtMatKhau;
    Button btnDangNhap;
    TextView txtDangKyTK;
    MyDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        dbHelper = new MyDatabase(DangNhap.this);

        edtSDT = findViewById(R.id.edtSDT);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtDangKyTK = findViewById(R.id.txtDangKyTK);

        txtDangKyTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtSDT.getText().toString().trim();
                String pass = edtMatKhau.getText().toString().trim();

                if (phone.equals("") || pass.equals("")) {
                    Toast.makeText(DangNhap.this, "Vui lòng nhập đủ số điện thoại và mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {

                    boolean checkLogin = dbHelper.checkDangNhap(phone, pass);

                    if (checkLogin) {
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        android.content.SharedPreferences sharedPreferences = getSharedPreferences("USER_DATA", android.content.Context.MODE_PRIVATE);
                        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("sdt_dang_nhap", phone);
                        editor.apply();

                        Intent intent = new Intent(DangNhap.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(DangNhap.this, "Số điện thoại hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}