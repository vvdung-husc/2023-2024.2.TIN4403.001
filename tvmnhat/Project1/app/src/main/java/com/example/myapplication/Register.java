package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText username_register = findViewById(R.id.username);
        EditText email_register = findViewById(R.id.email_toggle);
        EditText password_register = findViewById(R.id.password_toggle2);
        Button Register_btn = findViewById(R.id.registerButton);
        TextView login_btn = findViewById(R.id.btnlogin);


        //admin, email and admin

        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username_register.getText().toString().equals("admin") &&
                        email_register.getText().toString().equals("admin@gmail.com") &&
                        password_register.getText().toString().equals("admin")){
                    Toast.makeText(Register.this, "REGISTER SUCCESSFULL", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Register.this, "REGISTER FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login_btn.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, Login.class));
            finish();
        });

    }
}