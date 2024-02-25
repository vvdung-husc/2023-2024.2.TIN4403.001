package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import java.text.BreakIterator;

public class RegisterScreen extends AppCompatActivity {
    EditText m_edtUserName,m_edtPass, m_edtPhoneNum; //Biến điều khiển EditText
    Button m_btnSubmit; //Biến điều khiển Đăng nhập
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUserName = (EditText)findViewById(R.id.edtName);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_edtPhoneNum = (EditText)findViewById(R.id.edtPhoneNum);
        m_btnSubmit = (Button) findViewById(R.id.btnSubmit);


        //Cài đặt sự kiện Click cho Button Register
        m_btnSubmit.setOnClickListener(new CButtonRegister());
    }

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            String name = m_edtUserName.getText().toString();
            String pass = m_edtPass.getText().toString();
            String phoneNum = m_edtPhoneNum.getText().toString();
            if((name.length() < 3 || pass.length() < 6 || phoneNum.length() < 10)){
                Toast toast = Toast.makeText(getApplicationContext(),"Không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Đăng ký thành công!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.GREEN);
                toast.show();
                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                i.putExtra("userName", name);
                i.putExtra("pass", pass);
                startActivity(i);
            }
        }
    }
}


