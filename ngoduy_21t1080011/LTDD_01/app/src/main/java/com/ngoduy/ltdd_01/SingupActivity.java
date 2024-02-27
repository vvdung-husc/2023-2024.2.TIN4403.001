package com.ngoduy.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class SingupActivity extends AppCompatActivity {
    TextView usenamesignup,sdtsignup,passsignup,repasssignup;
    Button btndangki;
    TextView btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        usenamesignup = findViewById(R.id.input_usename);
        sdtsignup = findViewById(R.id.input_sdt);
        passsignup = findViewById(R.id.input_passwordsignup);
        repasssignup = findViewById(R.id.input_repeatpass);
        btndangki = findViewById(R.id.button_dangki);
        btnlogin = findViewById(R.id.textdangnhap);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingupActivity.this, LoginActivity.class));
            }
        });
        btndangki.setOnClickListener(new CButtonSignup());
    }
    public  class CButtonSignup implements View.OnClickListener{
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String usename = usenamesignup.getText().toString();
            String sdt = sdtsignup.getText().toString();
            String pass = passsignup.getText().toString();
            String repass = repasssignup.getText().toString();
            Log.d("K45","CLICK BUTTON SIGNUP ACCOUNT " + usename + "/"+sdt+"/" + pass+"/"+repass);
            if (usename.length() < 3|| sdt.length()!=10 || repass.length()<6 || pass.length() < 6){
                Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiSignup(usename,sdt,pass,repass);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void apiSignup(String username,String sdt, String pass,String repass) throws IOException {
        String json = "{\"username\":\"" + username + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K45",json);
        boolean bOk = pass.equals(repass);
        if (bOk){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
        else{
            SingupActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Mật khẩu không trùng khớp";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();                }
            });
        }
    }
}