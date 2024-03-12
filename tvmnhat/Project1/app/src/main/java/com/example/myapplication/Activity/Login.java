package com.example.myapplication.Activity;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginbutton;
    TextView signup_btn;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password_toggle);
        loginbutton = findViewById(R.id.loginButton);
        signup_btn = findViewById(R.id.btnSignup);

        //admin and admin
        AddData();
    }
    public void AddData(){
        loginbutton.setOnClickListener(view -> {
            //incorrect
            if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
            }
            //correct
            else{
                Toast.makeText(Login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            }

            boolean isInserted = myDb.insertedData(username.getText().toString(),
                    password.getText().toString());
            if(isInserted) {
                Toast.makeText(Login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(Login.this, HomePage.class));
//            finish();
        });

        signup_btn.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, Register.class));
//            finish();
        });
    }
}