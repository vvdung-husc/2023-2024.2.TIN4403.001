package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PageUseActivity extends AppCompatActivity {
    Button btnlogin,btnsignup,btndangxuat;
    TextView textWellcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_use);
        textWellcome = findViewById(R.id.textviewUsename);
        btndangxuat = findViewById(R.id.button_dangxuat_use);
        btnlogin = findViewById(R.id.button_login_use);
        btnsignup = findViewById(R.id.button_signup_use);
        String s = LoginActivity._usernameLogined;
        textWellcome.setText(s);
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PageUseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SingupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}