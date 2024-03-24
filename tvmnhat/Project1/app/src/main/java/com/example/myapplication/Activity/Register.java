package com.example.myapplication.Activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Register extends AppCompatActivity {
    static String _URL = "https://dev.husc.edu.vn/tin4403/api";//http://192.168.3.125:4080
    static  String _usernameRegistered;
    static  String _fullnameRegistered;
    static  String _emailRegistered;
    static  String _passwordRegistered;
    EditText username_register;
    EditText fullname_register;
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
        fullname_register = findViewById(R.id.fullname);
        email_register = findViewById(R.id.email_toggle);
        password_register = findViewById(R.id.password_toggle2);
        confirm_password_register = findViewById(R.id.password_toggle3);
        Register_btn = findViewById(R.id.registerButton);
        login_btn = findViewById(R.id.btnlogin);

        //admin, email and admin
        Register_btn.setOnClickListener(view -> {
            String username_register_str = username_register.getText().toString();
            String fullname_register_str = fullname_register.getText().toString();
            String email_register_str = email_register.getText().toString();
            String password_register_str = password_register.getText().toString();
            String confirm_password_register_str = confirm_password_register.getText().toString();

            if (password_register_str.equals(confirm_password_register_str)) {
                Toast.makeText(Register.this, "REGISTER SUCCESSFULL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "REGISTER FAILED", Toast.LENGTH_SHORT).show();
            }

            try {
                okhttpApiRegister(username_register_str, confirm_password_register_str, fullname_register_str
                        , email_register_str);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        login_btn.setOnClickListener(view -> {
//            startActivity(new Intent(Register.this, Login.class));
            finish();
        });
    }

    void okhttpApiRegister(String user, String pass, String fullname, String email) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .add("fullname", fullname)
                .add("email", email)
                .build();
        // đưa dữ liệu của body theo api dưới đây để post lên server xem username và password có khớp không
        Request request = new Request.Builder()
                .url(_URL)
                .post(body)//method post, data đẩy lên dưới dạng JSON là body
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K45", "onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                assert response.body() != null;
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K45", errStr);
                if (!response.isSuccessful()) {
                    Register.this.runOnUiThread(() -> Toast.makeText(Register.this, errStr, Toast.LENGTH_SHORT).show());
                    return;
                }

                _usernameRegistered = user;
                _fullnameRegistered = fullname;
                _emailRegistered = email;
                _passwordRegistered = pass;
                Intent intent = new Intent(Register.this, User.class);
                startActivity(intent);
            }
        });
    }
}