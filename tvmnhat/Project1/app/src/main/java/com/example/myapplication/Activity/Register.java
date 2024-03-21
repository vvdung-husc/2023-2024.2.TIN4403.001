package com.example.myapplication.Activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;

public class Register extends AppCompatActivity {
    EditText username_register;
    EditText email_register;
    EditText password_register;
    EditText confirm_password_register;
    Button Register_btn;
    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_register = findViewById(R.id.username);
        email_register = findViewById(R.id.email_toggle);
        password_register = findViewById(R.id.password_toggle2);
        confirm_password_register = findViewById(R.id.password_toggle3);
        Register_btn = findViewById(R.id.registerButton);
        login_btn = findViewById(R.id.btnlogin);

        //admin, email and admin
        Register_btn.setOnClickListener(view -> {
            String password_register_str = password_register.getText().toString();
            String confirm_password_register_str = confirm_password_register.getText().toString();

            if (username_register.getText().toString().equals("admin") &&
                    email_register.getText().toString().equals("admin@gmail.com") &&
                    password_register_str.equals(confirm_password_register_str)) {
                Toast.makeText(Register.this, "REGISTER SUCCESSFULL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "REGISTER FAILED", Toast.LENGTH_SHORT).show();
            }
        });

        login_btn.setOnClickListener(view -> {
//            startActivity(new Intent(Register.this, Login.class));
            finish();
        });

    }
}