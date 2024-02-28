package com.example.ltdd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnForgetNext;
    TextView tvForgetBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        edtEmail = findViewById(R.id.edtEmailForget);
        btnForgetNext = findViewById(R.id.btnForgetNext);
        tvForgetBack = findViewById(R.id.lblForgetBack);

        btnForgetNext.setOnClickListener(new ForgetActivity.CBtnForgetNext());

        tvForgetBack.setOnClickListener(new tvForgetBack());
    }

    public class CBtnForgetNext  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String email = edtEmail.getText().toString();
            if (email.length() == 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập email đã đăng ký!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public class tvForgetBack implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Finish the registration screen and return to the Login activity
            Intent myIntent = new Intent(ForgetActivity.this, LoginActivity.class);
            ForgetActivity.this.startActivity(myIntent);
            finish();
        }
    }
}