package com.nqhuy.app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    TextView txt_infor, txt_setting;
    Button btndangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txt_infor = (TextView) findViewById(R.id.txtemail);
        txt_setting = (TextView) findViewById(R.id.txtsetting);

        txt_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_infor.getBackground().setColorFilter(getResources().getColor(R.color.CDCDCD), PorterDuff.Mode.SRC_ATOP);
                txt_infor.invalidate(); // Cần phải vẽ lại Button để hiển thị màu mới

                // Tạo một Intent để chuyển đến UserInforActivity
                Intent intent = new Intent(WelcomeActivity.this, UserInforActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btndangxuat = findViewById(R.id.button_dangxuat);
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}