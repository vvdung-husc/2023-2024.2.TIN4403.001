package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class register extends AppCompatActivity {
    TextView textView3;
    EditText edtusername,edtpassword,edtfullname,edtemail;
    Button btnrgter,btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        edtfullname = findViewById(R.id.edtfullname);
        edtemail = findViewById(R.id.edtemail);
        btnrgter = findViewById(R.id.btnrgter);
        btnback = findViewById(R.id.btnback);

        btnrgter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtusername.getText().toString();
                String password = edtpassword.getText().toString();
                String fullname = edtfullname.getText().toString();
                String email = edtemail.getText().toString();

                try {
                    okhttpRegister(username,password,fullname,email);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void okhttpRegister(String user,String pass,String fullname,String email) throws IOException {
        String json = "{\"username\":\"" + user
                    + "\",\"password\":\"" + pass
                    +"\" ,\"fullname\":\""+fullname
                    +"\",\"email\":\""+email+"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username",user)
                .add("password",pass)
                .add("fullname",fullname)
                .add("email",email)
                .build();

        Request request = new Request.Builder()
                //.url("https://dev.husc.edu.vn/tin4403/api/login")
                .url("http://192.168.3.99:4080/register/register")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản đã tồn tại.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài Khoản đã tồn tại 1.\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()){
                    register.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }
}