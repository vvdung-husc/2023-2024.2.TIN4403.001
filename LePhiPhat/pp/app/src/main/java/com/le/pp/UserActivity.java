package com.le.pp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        String s = "Hello " + MainActivity.userNameLogined;
        TextView txtHello = (TextView) findViewById(R.id.txt_Hello);
        txtHello.setText(s);
    }
}