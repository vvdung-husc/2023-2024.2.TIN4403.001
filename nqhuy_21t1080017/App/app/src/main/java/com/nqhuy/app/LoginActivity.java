package com.nqhuy.app;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    static String _usernameLogined,_passwordLogined;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL;
    ImageView btnBack;
    TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        _URL = "https://dev.husc.edu.vn/tin4403/api";
        //_URL = "http://192.168.1.113:5080";

        loiginUsername = findViewById(R.id.username);
        loginPassword=findViewById(R.id.pass);
        loginButton = findViewById(R.id.btnLogin);
        checkBoxMkLogin = findViewById(R.id.hienmatkhau);
        txtRegister = findViewById(R.id.lblRegister);
        btnBack = findViewById(R.id.btnBack);
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
                }else if (!isUsernameValid(use)) {
                    Toast.makeText(getApplicationContext(), "Tên người dùng không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isPasswordValid(pas)) {
                    // Kiểm tra xem mật khẩu có đúng định dạng không
                    Toast.makeText(getApplicationContext(), "Mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadLogin(use,pas);
                    }
                }).start();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    boolean isUsernameValid(String username) {
        return username != null && username.length() >= 3;
    }
    boolean isPasswordValid(String password) {
        return password != null && password.length() >= 5;
    }

    void threadLogin(String user, String pass) {
        //Tạo chuỗi theo cấu trúc JSON
        String jsonBody = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("threadLogin",jsonBody);

        String jsonResponse;
        try {
            jsonResponse = Global._HTTP.POST("/Login",jsonBody);
        }
        catch (IOException e){
            e.printStackTrace();
            Log.e("JSON","Network Error");
            Global.uiShowToast(getApplicationContext(),"Webservice không đang chạy, kiểm tra lại dịch vụ");
            return;
        }

        String strError = "";
        Log.d("JSON",jsonResponse);
        JSONObject oResult = null;
        int r = 0;
        try {
            oResult = new JSONObject(jsonResponse);
            Log.d("oJSON",oResult.toString());
            try {
                r = oResult.getInt("r");
                String m = oResult.getString("m");
                Log.d("r = ", String.valueOf(r));
                Log.d("m = ", m);
                if (r == 1) Global._token = m;
                else strError = oResult.toString();

            } catch (JSONException e) {
                strError = "JSON.m không đúng cấu trúc " + jsonResponse;
            }
        } catch (JSONException e) {
            strError = "JSON trả về không đúng cấu trúc " + jsonResponse;
            Log.e("JSON", strError);
        }


        if (r != 1){
            Global.uiShowToast(getApplicationContext(),strError);
            return;
        }
        User._password = pass;
        User._username = user;
        threadUserInfo();

    }//void threadLogin(String user, String pass) {
    void threadUserInfo() {
        Log.i("TOKEN", Global._token);
        Map<String,String> mapHeader = new HashMap<>();
        mapHeader.put("token",Global._token);
        String jsonResponse;
        try {
            jsonResponse = Global._HTTP.POST("/userinfo",mapHeader);
        }
        catch (IOException e){
            e.printStackTrace();
            Log.e("JSON","Network Error");
            Global.uiShowToast(getApplicationContext(),"Webservice không đang chạy, kiểm tra lại dịch vụ");
            return;
        }

        Log.d("JSON",jsonResponse);
        String strError = "";
        JSONObject oResult = null;
        JSONObject oUser = null;
        int r = 0;
        try {
            oResult = new JSONObject(jsonResponse);
            Log.d("oJSON",oResult.toString());
            try {
                r = oResult.getInt("r");
                oUser = oResult.getJSONObject("m");
                Log.i("r = ", String.valueOf(r));
                Log.i("m = ", oUser.toString());
                if (r != 1) strError = oResult.toString();
                String name = oUser.getString("fullname");
                if (name.length() > 0) User._fullname = name;
                String email = oUser.getString("email");
                if (email.length() > 0) User._email = email;

            } catch (JSONException e) {
                strError = "JSON.m không đúng cấu trúc " + jsonResponse;
            }
        } catch (JSONException e) {
            strError = "JSON trả về không đúng cấu trúc " + jsonResponse;
            Log.e("JSON", strError);
        }

        if (r != 1 || oUser == null){
            Global.uiShowToast(getApplicationContext(),strError);
            return;
        }

        //Chuyển UserActivity Form
        _usernameLogined = User._username;
        _passwordLogined = User._password;
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        startActivity(intent);
    }

}