package com.example.project1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserForm extends AppCompatActivity{
    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Khởi tạo các biến điều khiển tương ứng trong layout
//        m_lblWelcome = (TextView)findViewById(R.id.lblWelcome);
//        m_btnLogout = (Button) findViewById(R.id.btnLogout);

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

    }
}
