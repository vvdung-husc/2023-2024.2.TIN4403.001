package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserScreen extends AppCompatActivity {
    TextView m_txtWellCome;
    Button m_btnLogout;
    Button m_btnChangePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_txtWellCome = (TextView)findViewById(R.id.txtWellCome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_btnChangePass = (Button) findViewById(R.id.btnChangePass);

        String s = "Chào mừng : " + LoginScreen._usernameLogined;
        m_txtWellCome.setText(s);
        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        m_btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(i);
            }
        });
    }
}