package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class User extends AppCompatActivity {
    TextView m_lblWelcome;
    TextView m_account;
    TextView m_fullname;
    TextView m_email;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = findViewById(R.id.lable_welcome);
        m_account = findViewById(R.id.account);
        m_fullname = findViewById(R.id.fullname);
        m_email = findViewById(R.id.email);
        m_btnLogout = findViewById(R.id.buttonnLogout);

        Login();
        AppData();
    }

    public void Login(){
        String account = "Tên tài khoản: " + Login._usernameLogined;
        String fullname = "Họ và tên: " + Login._fullnameLogined;
        String email = "Email: " + Login._emailLogined;

        m_account.setText(account);
        m_fullname.setText(fullname);
        m_email.setText(email);
    }

    public void AppData(){
        m_btnLogout.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(User.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
