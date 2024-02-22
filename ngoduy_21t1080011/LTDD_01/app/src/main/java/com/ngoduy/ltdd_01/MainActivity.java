package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText loiginUsername,loginPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loiginUsername = findViewById(R.id.input_usename);
        loginPassword=findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_dangnhap);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename = "ngoduy";
                String password = "12345";
                if(loiginUsername.getText().toString().equals(usename) && loginPassword.getText().toString().equals(password)) {
                    String name = loiginUsername.getText().toString();
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công:"+name , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}