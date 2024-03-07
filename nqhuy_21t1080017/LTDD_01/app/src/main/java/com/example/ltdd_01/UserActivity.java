package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class UserActivity extends AppCompatActivity {
    TextView m_lblWelcome, usernameTextView;
    Button m_btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.lblWelcome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        usernameTextView = (TextView)findViewById(R.id.username);
        // Retrieve the username from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            if (username != null) {
                // Display the username in the TextView
                usernameTextView.setText("Welcome, " + username);
            } else {
                // Handle the case where the username is null
                usernameTextView.setText("Welcome");
            }
        }

        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//protected void onCreate(Bundle savedInstanceState) {


}//public class UserActivity extends AppCompatActivity {