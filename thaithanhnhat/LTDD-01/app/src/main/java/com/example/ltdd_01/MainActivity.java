package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static String   _usernameLogined;
    EditText edttk, edtpass;
    Button btnlogin,btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edttk = findViewById(R.id.edttk);
        edtpass= findViewById(R.id.edtpass);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =edttk.getText().toString();
                String password = edtpass.getText().toString();

                if(user.length()<3 || password.length()<6){
                    Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                    TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;

                }
                else{
                    try {
                        okhttpLogin(user,password);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
    void okhttpLogin(String user,String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username",user)
                .add("password",pass).build();

        Request request = new Request.Builder()
                //.url("https://dev.husc.edu.vn/tin4403/api/login")
                .url("http://192.168.56.1:4080/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                _usernameLogined = user;
                Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);

            }
        });
    }
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);

        boolean bOk = (user.equals("ttnhat") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Thái Thanh Nhật";
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                }
            });
        }
    }

}