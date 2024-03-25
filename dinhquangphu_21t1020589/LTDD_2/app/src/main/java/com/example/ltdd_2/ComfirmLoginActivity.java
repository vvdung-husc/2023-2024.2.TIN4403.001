package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class ComfirmLoginActivity extends AppCompatActivity {

    Button btn_DoiNK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_login);
        btn_DoiNK = findViewById(R.id.btnDoiMK);

        btn_DoiNK.setOnClickListener(new CButtonDoiMK());
    }

    public class CButtonDoiMK  implements View.OnClickListener {
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangKy
            Intent i = new Intent(ComfirmLoginActivity.this, changePassword.class);
            startActivity(i);
        }
    }
}