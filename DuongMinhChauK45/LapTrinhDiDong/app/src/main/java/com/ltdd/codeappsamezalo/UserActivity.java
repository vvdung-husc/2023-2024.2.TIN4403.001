package com.ltdd.codeappsamezalo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import okhttp3.MediaType;

public class UserActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    TextView m_lblWelcome;
    Button m_btnLogout;

    Button m_resetPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView) findViewById(R.id.lblWelcome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_resetPasswordBtn = (Button) findViewById(R.id.resetPass_btn);

        String s = "Chào mừng : " + MainActivity._usernameLogined;
        m_lblWelcome.setText(s);

        m_resetPasswordBtn.setOnClickListener(new CButtonChangePass());

        m_btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public class CButtonChangePass implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(i);
        }
    }
}