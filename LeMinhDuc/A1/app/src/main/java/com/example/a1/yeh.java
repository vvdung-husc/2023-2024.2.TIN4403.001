package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class yeh extends AppCompatActivity {
    TextView m_lblWelcome,m_lblUser,m_lblName,m_lblEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeh);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.textView);
        m_lblName = (TextView)findViewById(R.id.textViewname);
        m_lblEmail= (TextView)findViewById(R.id.textViewemail);
        m_lblWelcome.setText("Chào "+ User._fullname);
        m_lblName.setText("Username: "+User._username);
        m_lblEmail.setText("Email: "+User._email);
    }
}