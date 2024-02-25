package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_Login;
    Button btn_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Login = findViewById(R.id.btnDangNhap);
        btn_Register = findViewById(R.id.btnDangKy);

        btn_Register.setOnClickListener(new ButtonDangKy());
        btn_Login.setOnClickListener(new ButtonDangNhap());
    }

    public class ButtonDangKy implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangKy
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }
    }

    public class ButtonDangNhap implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangNhap
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}