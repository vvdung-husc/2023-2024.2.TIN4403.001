package com.le.pp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static String userNameLogined;
    EditText etUserName, etPassword;
    TextView tvRegister;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.inp_TaiKhoan);
        etPassword = (EditText) findViewById(R.id.inp_MatKhau);
        tvRegister = (TextView) findViewById(R.id.btn_ctDangKi);
        btnLogIn = (Button) findViewById(R.id.btn_DangNhap);

        btnLogIn.setOnClickListener(new CButtonLogin());
        tvRegister.setOnClickListener(new CButtonRegister());
    }
    public class CButtonLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String user = etUserName.getText().toString();
            String pass = etPassword.getText().toString();
            if (user.length() < 4 || pass.length() < 4) {
                Toast t = Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_LONG);
                t.show();
                return;
            }
            try {
                apiLogin(user, pass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    }

    public void apiLogin(String user, String pass) {
        Boolean success = (user.equals("admin") && pass.equals("admin"));
        if (success) {
            userNameLogined = "Le Phi Phat";
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        } else {
            String mess = "Tài khoản hoặc mật khẩu không chính xác\n";
            Toast t = Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_LONG);
            t.show();
        }
    }
}