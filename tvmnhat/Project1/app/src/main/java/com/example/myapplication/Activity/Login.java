package com.example.myapplication.Activity;

import android.content.Intent;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Fraggment.HomePage;
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
    static  String _fullnameRegistered = "Trần Văn Minh Nhật";
    static  String _emailRegistered = "minhnhat6403@gmail.com";
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
//            //correct
//            else{
//                Toast.makeText(Login.this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//            }
//
//            boolean isInserted = myDb.insertedData(username.getText().toString(),
//                    password.getText().toString());
//            if(isInserted) {
//                Toast.makeText(Login.this, "LOGIN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
//            }

            String user = username.getText().toString();
            String pass = password.getText().toString();

            try {
                //Gọi hàm dịch vụ Login
//                apiLogin(user,pass);
                okhttpApiLogin(user, pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

            startActivity(new Intent(Login.this, User.class));
//            finish();
        });

        signup_btn.setOnClickListener(view ->{
            startActivity(new Intent(Login.this, Register.class));
//            finish();
        });
    }

    void apiLogin(String user, String pass) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(Login.this, json, Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (user.equals("tvmnhat") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Trần Văn Minh Nhật";// kích hoạt activity_user
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