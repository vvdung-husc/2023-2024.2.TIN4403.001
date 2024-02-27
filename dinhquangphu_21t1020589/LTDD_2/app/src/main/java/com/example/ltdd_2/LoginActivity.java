package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    static String   _usernameLogined;
    EditText edtUser;
    EditText edtPass;
    Button btnDN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPassWord);
        btnDN = findViewById(R.id.btnDN);

        btnDN.setOnClickListener(new CButtonLogin());

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String phone = edtUser.getText().toString();
            String pass = edtPass.getText().toString();
            Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + phone + "/" + pass);
            if (phone.length() < 3 || pass.length() < 6) {
                Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogin(phone, pass);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (user.equals("phudeptrai123") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Dinh Quang Phu";
            Intent intent = new Intent(LoginActivity.this,ComfirmLoginActivity.class);
            startActivity(intent);
        }
        else{
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}