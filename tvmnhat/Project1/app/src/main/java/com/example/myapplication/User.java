package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {
    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = findViewById(R.id.lblWelcome);
        m_btnLogout = findViewById(R.id.btnLogout);

        String s = "Chào mừng : " + Login._usernameLogined;
        m_lblWelcome.setText(s);
        m_btnLogout.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(User.this, Login.class);
            startActivity(intent);
            finish();
        });

    }//protected void onCreate(Bundle savedInstanceState) {
}