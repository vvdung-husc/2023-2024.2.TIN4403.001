package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText loiginUsername,loginPassword;
    Button loginButton;
    static String   _usernameLogined;
    String usename = "ngoduy";
    String password = "12345";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loiginUsername = findViewById(R.id.input_usename);
        loginPassword=findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_dangnhap);

        TextView btn = findViewById(R.id.text_dangki);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String use=loiginUsername.getText().toString();
                String pas=loginPassword.getText().toString();
                Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + use + "/" + pas);
                if(use.length()<3 || pas.length()<5){
                    Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }
                boolean checkOk=use.equals(usename) && pas.equals(password);
                if(checkOk) {
                    String name = loiginUsername.getText().toString();
                    startActivity(new Intent(LoginActivity.this, PageUseActivity.class));
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công:"+name , Toast.LENGTH_LONG).show();
                    _usernameLogined = use;
                }else{
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SingupActivity.class));
            }
        });

    }
}