package com.example.ltdd_01;

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

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static String   _usernameLogined;
    EditText edttk, edtpass;
    Button btnlogin,btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edttk = findViewById(R.id.edttk);
        edtpass= findViewById(R.id.edtpass);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =edttk.getText().toString();
                String password = edtpass.getText().toString();

                if(user.length()<3 || password.length()<6){
                    Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                    TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;

                }
                else{
                    try {
                        apiLogin(user,password);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);

        boolean bOk = (user.equals("ttnhat") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Thái Thanh Nhật";
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                }
            });
        }
    }

}