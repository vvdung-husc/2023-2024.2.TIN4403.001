package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ThongTinUseActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView fullName,useName,passWord,email;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_use);
        btnBack = findViewById(R.id.btnQuayLai);
        fullName = findViewById(R.id.txtHoTen);
        useName = findViewById(R.id.textTenTaiKhoan);
        passWord = findViewById(R.id.textMatKhau);
        email = findViewById(R.id.textEmail);
        btnUpdate= findViewById(R.id.btnChinhSua);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PageUseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}