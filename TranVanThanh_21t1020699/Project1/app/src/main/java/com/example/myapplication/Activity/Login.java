package com.example.myapplication.Activity;

import android.content.Intent;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;


import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Login extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //thay đổi _URL đúng với IP đang chạy dịch vu WebService
    static String _URL = "https://dev.husc.edu.vn/tin4403/api";//http://192.168.3.125:4080
    EditText username;
    EditText password;
    Button loginbutton;
    TextView signup_btn;
    static  String _usernameLogined;
    static  String _fullnameLogined = "Trần Văn Thanh";
    static  String _emailLogined = "tranvanthanh.12012003@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginButton);
        signup_btn = findViewById(R.id.signupBtn);

        //admin and admin
        AddData();
    }

    public void AddData(){
        loginbutton.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();

            try {
                okhttpApiLogin(user, pass);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signup_btn.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    void apiLogin(String user, String pass) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(Login.this, json, Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (user.equals("tvthanh") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Trần Văn Thanh";// kích hoạt activity_user
            Intent intent = new Intent(Login.this, User.class);
            startActivity(intent);
        }
        else{
            Login.this.runOnUiThread(() -> {
                String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                Toast toast = Toast.makeText(Login.this, str,Toast.LENGTH_SHORT);
                toast.show();
            });
        }
    }//void apiLogin(String user, String pass) throws IOException {

    void okhttpApiLogin(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();
        // đưa dữ liệu của body theo api dưới đây để post lên server xem username và password có khớp không
        Request request = new Request.Builder()
                .url(_URL + "/login")
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
                    Login.this.runOnUiThread(() -> Toast.makeText(Login.this, errStr, Toast.LENGTH_SHORT).show());
                    return;
                }

                _usernameLogined = user;
                Intent intent = new Intent(Login.this, User.class);
                startActivity(intent);
            }
        });
    }
}