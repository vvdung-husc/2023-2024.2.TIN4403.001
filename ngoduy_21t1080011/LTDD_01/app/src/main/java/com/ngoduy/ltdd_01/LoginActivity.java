package com.ngoduy.ltdd_01;

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
    static String   _usernameLogined,_passwordnameLogined;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //_URL = "https://dev.husc.edu.vn/tin4403/api";
        _URL = "http://192.168.1.4:5080";

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
//                try {
//                    //apiLogin(use,pas);
//                    okhttpApiLogin(use,pas);
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadLogin(use,pas);
                    }
                }).start();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SingupActivity.class));
            }
        });

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

    }
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
        Intent intent = new Intent(getApplicationContext(), PageUseActivity.class);
        startActivity(intent);

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
    void okhttpApiLogin(String user, String pass) throws IOException {
        String res = Global._HTTP.GET("/");
        Log.d("RES - okhttpApiLogin() ",res);

    }//void okhttpApiLogin(String user, String pass) throws IOException {
    void okhttpApiUserInfo(String token) throws IOException {
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(Global._URL + "/userinfo")
                .post(body)
                .addHeader("token",token)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response res = null;
        String json = "";
        try {
            res = client.newCall(request).execute();
            json = res.body().string();
            Log.d("RES",res.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Log.d("JSON",json);
        JSONObject oResult = null;
        try {
            oResult = new JSONObject(json);
            Log.d("K45", oResult.toString());
        } catch (Throwable t) {
            Log.e("K45", "Could not parse malformed JSON: \"" + json + "\"");
            return;
        }
        if (oResult == null){
            String errStr = "JSON trả về không đúng cấu trúc" + json;
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }//if (!response.isSuccessful()){

        JSONObject oUser = null;
        try {
            oUser = oResult.getJSONObject("m");
            Log.d("K45", oUser.toString());
            String name = oUser.get("fullname").toString();
            if (name.length() > 0) User._fullname = name;
            String email = oUser.get("email").toString();
            if (email.length() > 0) User._email = email;
        } catch (JSONException e) {
            Log.e("K45", "Could not parse malformed JSON: \"" + json + "\"");
            return;
        }

    }//void okhttpApiUserInfo(String token) throws IOException {

    void okhttpApiLogin_old(String user, String pass) throws IOException{
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