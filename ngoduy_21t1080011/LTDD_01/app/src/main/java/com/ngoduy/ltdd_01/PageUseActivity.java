package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PageUseActivity extends AppCompatActivity {
    Button btndangxuat;
    TextView textWellcome;
    TextView btnThongTinUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_use);
        textWellcome = findViewById(R.id.textviewUsename);
        btnThongTinUse = findViewById(R.id.buttonThongTinUse);
        btndangxuat = findViewById(R.id.button_dangxuat);
//        m_lblUser.setText ("Tài khoản\t\t:\t" + User._username);
//        m_lblName.setText ("Họ và tên\t\t:\t" + User._fullname);
//        m_lblEmail.setText("Thư điện tử\t:\t" + User._email);
//        String s = LoginActivity._usernameLogined;
        textWellcome.setText(User._username);
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnThongTinUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageUseActivity.this, ThongTinUseActivity.class));
            }
        });
    }
}