package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginbutton;
    TextView signup_btn;
    static String _usernameLogined;
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
        AddData();
    }

    public void AddData() {
        loginbutton.setOnClickListener(view -> {
            //correct
//            boolean isInserted = myDb.insertedData(username.getText().toString(),
//                    password.getText().toString());
//            if(isInserted){
//                Toast.makeText(Login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
//            }

            String user = username.getText().toString();
            String pass = password.getText().toString();

            try {
                //Gọi hàm dịch vụ Login
//                apiLogin(user, pass);
                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signup_btn.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
        Log.d("K45", json);

        boolean bOk = (user.equals("tvmnhat") && pass.equals("123456"));
        if (bOk) {
            _usernameLogined = "Trần Văn Minh Nhật";// kích hoạt activity_user
            Intent intent = new Intent(getApplicationContext(), User.class);
            startActivity(intent);
        } else {
            Login.this.runOnUiThread(() -> {
                String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.setBackgroundColor(Color.GREEN);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            });
        }
    }//void apiLogin(String user, String pass) throws IOException {

    void okhttpApiLogin(String user, String pass) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        Log.d("K45", json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();
        // đưa dữ liệu của body theo api dưới đây để post lên server xem username và password có khớp không
        Request request = new Request.Builder()
                .url("https://dev.husc.edu.vn/tin4403/api/login")
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
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K45", errStr);
                if (!response.isSuccessful()) {
                    Login.this.runOnUiThread(() -> Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_SHORT).show());
                    return;
                }

                _usernameLogined = user;
                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);

            }
        });//client.newCall(request).enqueue(new Callback() {
    } //void okhttpApiLogin(String user, String pass) throws IOException{

}