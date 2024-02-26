package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    private CheckBox hienthimatkhau;
    private EditText matkhau;
    private Button btndangnhap;
    private ImageButton imgbtnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hienthimatkhau = findViewById(R.id.hienthimatkhau);
        matkhau = findViewById(R.id.matkhau);
        btndangnhap = findViewById(R.id.btndangnhap);
        imgbtnback = findViewById(R.id.imgbtnback);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển từ LoginActivity sang RegisterActivity
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        hienthimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienthimatkhau.isChecked()) {
                    // Hiển thị mật khẩu
                    matkhau.setTransformationMethod(null);
                } else {
                    // Ẩn mật khẩu
                    matkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi icon quay lại được nhấn
                onBackPressedAction();
            }
        });
    }

    public void onBackPressedAction() {
        // Xử lý sự kiện khi icon quay lại được nhấn
        finish(); // hoặc thực hiện logic quay lại khác tùy thuộc vào yêu cầu của bạn
    }

}