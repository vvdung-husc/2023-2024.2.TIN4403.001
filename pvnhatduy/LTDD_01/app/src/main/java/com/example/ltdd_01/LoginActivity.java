package com.example.ltdd_01;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText loiginUsername,loginPassword;
    Button loginButton;
    CheckBox checkBoxMkLogin;
    static String   _usernameLogined,_passwordnameLogined;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //_URL = "https://dev.husc.edu.vn/tin4403/api";
        //_URL = "http://192.168.3.99:4080";
        _URL = "http://192.168.1.7:5080";

        loiginUsername = findViewById(R.id.input_usename);
        loginPassword=findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_dangnhap);
        checkBoxMkLogin = findViewById(R.id.checkBoxMKLogin);
        TextView btn = findViewById(R.id.text_dangki);
        //hiển thị mật khẩu
        checkBoxMkLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxMkLogin.isChecked()){
                    loginPassword.setTransformationMethod(null);
                }
                else{
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String use=loiginUsername.getText().toString();
                String pas=loginPassword.getText().toString();
                Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + use + "/" + pas);
                if(use.length()<3 || pas.length()<5){
                    Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }
                try {
                    //apiLogin(use,pas);
                    okhttpApiLogin(use,pas);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SingupActivity.class));
            }
        });

    }
    void apiLogin(String user,String pass) throws IOException{
        String usename = "ngoduy";
        String password = "12345";
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K44",json);
        boolean checkOk=user.equals(usename) && pass.equals(password);
        if(checkOk) {
            String name = loiginUsername.getText().toString();
            startActivity(new Intent(LoginActivity.this, PageUseActivity.class));
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công:"+name , Toast.LENGTH_LONG).show();
            _usernameLogined = user;
        }else{
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    /*                    view.setBackgroundColor(Color.GREEN);*/
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                }
            });
        }
    }
    void okhttpApiLogin(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody  body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();

        Request request = new Request.Builder()
                .url(_URL + "/login")
                //.url("https://dev.husc.edu.vn/tin4403/api/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);

                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()){
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                Log.d("K45","abc");
                _usernameLogined = user;
                _passwordnameLogined = pass;
                Intent intent = new Intent(getApplicationContext(),PageUseActivity.class);
                startActivity(intent);

            }
        });//client.newCall(request).enqueue(new Callback() {
    }
}