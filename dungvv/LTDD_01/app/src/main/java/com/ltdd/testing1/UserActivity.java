package com.ltdd.testing1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity {
    public static String _fullname = "<rỗng>";
    public static String _email = "<chưa có>";

    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.lblWelcome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);

        String s  = "*** Chào mừng ***\n\n";
               s += "Tài khoản  :\t" + MainActivity._username + "\n";
               s += "Họ và tên  :\t" + _fullname + "\n";
               s += "Thư điện tử:\t" + _email;
        m_lblWelcome.setText(s);

        Log.d("NAME",_fullname);
        Log.d("EMAIL",_email);

        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), com.ltdd.testing1.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//protected void onCreate(Bundle savedInstanceState) {



}//public class UserActivity extends AppCompatActivity {