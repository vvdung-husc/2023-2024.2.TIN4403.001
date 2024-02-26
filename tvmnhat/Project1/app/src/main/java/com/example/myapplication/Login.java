package com.example.myapplication;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password_toggle);
        Button loginbutton = findViewById(R.id.loginButton);
        TextView signup_btn = findViewById(R.id.btnSignup);

        //admin and admin
        loginbutton.setOnClickListener(view -> {
            //correct
            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                Toast.makeText(Login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            }
            //incorrect
            else{
                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
            }
        });

        signup_btn.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });
    }
}