package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class yeh extends AppCompatActivity {
    TextView m_lblWelcome, m_lblUser, m_lblName, m_lblEmail;
    Button m_btnLogout, m_btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeh);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView) findViewById(R.id.textView);
        m_lblName = (TextView) findViewById(R.id.textViewname);
        m_lblEmail = (TextView) findViewById(R.id.textViewemail);
        m_lblWelcome.setText("Chào " + User._fullname);
        m_lblName.setText("Username: " + User._username);
        m_lblEmail.setText("Email: " + User._email);

        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                new User().reset();
                Intent intent = new Intent(getApplicationContext(), com.example.a1.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}