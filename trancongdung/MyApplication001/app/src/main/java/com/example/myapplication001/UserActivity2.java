package com.example.myapplication001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity2 extends AppCompatActivity {
    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.logout);

        String s = "Chào mừng : " + MainActivity._usernameLogined;
        m_lblWelcome.setText(s);
        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//protected void onCreate(Bundle savedInstanceState) {


}