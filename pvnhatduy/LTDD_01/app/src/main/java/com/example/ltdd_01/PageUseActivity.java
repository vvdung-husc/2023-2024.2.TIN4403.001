package com.example.ltdd_01;

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
    @Overridea
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_use);
        textWellcome = findViewById(R.id.textviewUsename);
        btnThongTinUse = findViewById(R.id.buttonThongTinUse);
        btndangxuat = findViewById(R.id.button_dangxuat);
        String s = LoginActivity._usernameLogined;
        textWellcome.setText(s);
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