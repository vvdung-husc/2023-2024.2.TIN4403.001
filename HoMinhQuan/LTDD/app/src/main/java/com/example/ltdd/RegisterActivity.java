package com.example.ltdd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText edtFullName;
    EditText edtEmail;
    EditText edtUserName;
    EditText edtPass;
    EditText edtComfirmPass;
    Button btnReg;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullName = findViewById(R.id.edtFullNameReg);
        edtEmail = findViewById(R.id.edtEmailReg);
        edtUserName = findViewById(R.id.edtUserNameReg);
        edtPass = findViewById(R.id.edtPassReg);
        edtComfirmPass = findViewById(R.id.edtConfirmPassReg);
        btnReg = findViewById(R.id.btnReg);
        tvLogin  = findViewById(R.id.lblLogin);

        btnReg.setOnClickListener(new CBtnReg());
        tvLogin.setOnClickListener(new CTvLogin());
    }

    public class CBtnReg implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            String fullName = edtFullName.getText().toString();
            String email = edtEmail.getText().toString();
            String userName = edtUserName.getText().toString();
            String pass = edtPass.getText().toString();
            String comfirmPass = edtComfirmPass.getText().toString();
            if (fullName.length() == 0 || email.length() == 0 || userName.length() == 0
                    || pass.length() == 0 || comfirmPass.length() == 0) {
                Toast.makeText(getApplicationContext(), "Yêu cầu điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(myIntent);
            finish();

        }
    }

    public class CTvLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Finish the registration screen and return to the Login activity
            Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(myIntent);
            finish();
        }
    }
}