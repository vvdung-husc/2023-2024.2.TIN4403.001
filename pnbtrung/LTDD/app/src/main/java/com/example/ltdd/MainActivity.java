package com.example.ltdd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.PhoneAccountSuggestion;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    TextView m_lblRegister;//Biến điều khiển Đăng ký mới
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Global._Handler =new Handler();//new Handler(Looper.getMainLooper());

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUsername);
        m_edtPass = (EditText)findViewById(R.id.edtPassword);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        m_lblRegister = (TextView) findViewById(R.id.lblRegister);

        m_edtUser.setText("admin");
        m_edtPass.setText("123456");

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());

        //Cài đặt sự kiện Click cho Button Register
        m_lblRegister.setOnClickListener(new CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("ACCOUNT","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                Global.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLogin(user,pass);
                }
            }).start();

        }//public void onClick(View v) {//Hàm sử lý sự kiện click button login

    }//public class CButtonLogin  implements View.OnClickListener {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), com.example.ltdd.RegisterActivity.class);
            startActivity(i);
        }
    }//public class CButtonRegister implements View.OnClickListener {

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
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);

    }//void threadUserInfo() {
    void okhttpApiLogin(String user, String pass) throws IOException {
        String res = Global._HTTP.GET("/");
        Log.d("RES - okhttpApiLogin() ",res);

    }//void okhttpApiLogin(String user, String pass) throws IOException {
    void okhttpApiLogin_old(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();

        Request request = new Request.Builder()
                .url(Global._URL + "/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resStr = response.body().string();
            Log.d("RES",resStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("...","OKIE");
    } //void okhttpApiLogin(String user, String pass) throws IOException{


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
            MainActivity.this.runOnUiThread(new Runnable() {
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


}//public class MainActivity extends AppCompatActivity {