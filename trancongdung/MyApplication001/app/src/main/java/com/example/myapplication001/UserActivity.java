package com.example.myapplication001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.lblWelcome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);

        String s = "Chào mừng : " + MainActivity._usernameLogined;
        m_lblWelcome.setText(s);


        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        );
        Button btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện điều hướng đến màn hình đổi mật khẩu
                Intent intent = new Intent(UserActivity.this, ChangePass.class);
                startActivity(intent);
            }
        });

    }//protected void onCreate(Bundle savedInstanceState) {


}//public class UserActivity extends AppCompatActivity {