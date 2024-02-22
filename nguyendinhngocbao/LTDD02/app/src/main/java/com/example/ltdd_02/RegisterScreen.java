package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.BreakIterator;

public class RegisterScreen extends AppCompatActivity {

    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtName);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_btnLogin = (Button) findViewById(R.id.btnSubmit);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin(this, m_edtUser, m_edtPass));
    }

}


