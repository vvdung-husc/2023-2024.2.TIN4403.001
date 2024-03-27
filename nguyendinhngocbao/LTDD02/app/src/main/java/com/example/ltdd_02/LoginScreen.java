package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginScreen extends AppCompatActivity {

    EditText m_edtUserLog,m_edtPassLog; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Global._Handler =new Handler();//new Handler(Looper.getMainLooper());

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUserLog = (EditText)findViewById(R.id.edtNameLog);
        m_edtPassLog = (EditText)findViewById(R.id.edtPassLog);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());

        m_edtUserLog.setText("baondn_k45");
        m_edtPassLog.setText("123456");
    }

    public class CButtonLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String userLog = m_edtUserLog.getText().toString();
            String passLog = m_edtPassLog.getText().toString();
            Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + userLog + "/" + passLog);
            if (userLog.length() < 3 || passLog.length() < 6) {
                Global.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLogin(userLog,passLog);
                }
            }).start();

        }
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

        User._username = user;
        String password = pass; // Lấy mật khẩu từ quá trình đăng nhập
        Global._userPassword = password; // Lưu mật khẩu vào biến userPassword trong lớp Global
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
        Intent intent = new Intent(getApplicationContext(), UserScreen.class);
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
            LoginScreen.this.runOnUiThread(new Runnable() {
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





   }