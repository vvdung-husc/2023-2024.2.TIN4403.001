package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class User extends AppCompatActivity {
    TextView m_lblWelcome;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.Welcome);
        m_btnLogout = (Button) findViewById(R.id.buttonnLogout);

        String s = "Chào mừng : " + Login._usernameLogined;
        m_lblWelcome.setText(s);
        m_btnLogout.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(User.this, Login.class);
            startActivity(intent);
            finish();
        });

    }//protected void onCreate(Bundle savedInstanceState) {
}
