package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class User extends AppCompatActivity {
    TextView m_lblWelcome;
    TextView m_account;
    TextView m_fullname;
    TextView m_email;
    Button m_btnUpdate;
    Button m_btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = findViewById(R.id.lable_welcome);
        m_account = findViewById(R.id.account);
        m_fullname = findViewById(R.id.fullname);
        m_email = findViewById(R.id.email);
        m_btnUpdate = findViewById(R.id.buttonUpdate);
        m_btnLogout = findViewById(R.id.buttonnLogout);

        Login();
//        Register();
        AppData();
    }//protected void onCreate(Bundle savedInstanceState) {

    public void Login(){
        String account = "Account    : " + Login._usernameLogined;
        String fullname = "Full name    : " + Login._fullnameRegistered;
        String email = "Email   : " + Login._emailRegistered;

        m_account.setText(account);
        m_fullname.setText(fullname);
        m_email.setText(email);
    }

    public void Register(){
//        String account = "Account    : " + Login._usernameRegistered;
//        String fullname = "Full name    : " + Login._fullnameRegistered;
//        String email = "Email   : " + Login._emailRegistered;
//
//        m_account.setText(account);
//        m_fullname.setText(fullname);
//        m_email.setText(email);
    }

    public void AppData(){
        m_btnLogout.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(User.this, Login.class);
            startActivity(intent);
            finish();
        });

        m_btnUpdate.setOnClickListener(v -> {

            Toast.makeText(User.this, "UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        });
    }
}
