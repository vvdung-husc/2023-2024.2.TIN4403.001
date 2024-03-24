package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.graphics.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    static String   _usernameLogined;
    EditText edtUser;
    EditText edtPass;
    Button btnDN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Global._Handler =new Handler();//new Handler(Looper.getMainLooper());

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPassWord);
        btnDN = findViewById(R.id.btnDN);

        btnDN.setOnClickListener(new CButtonLogin());

        edtUser.setText("admin");
        edtPass.setText("123456");

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();
            Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6) {
                Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLogin(user,pass);
                }
            }).start();

            /*try {
                //Gọi hàm dịch vụ Login
                //apiLogin(phone, pass);
                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            } */
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
        Intent intent = new Intent(getApplicationContext(), ComfirmLoginActivity.class);
        startActivity(intent);

    }//void threadUserInfo() {


    /*Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (user.equals("phudeptrai123") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Dinh Quang Phu";
            Intent intent = new Intent(LoginActivity.this,ComfirmLoginActivity.class);
            startActivity(intent);
        }
        else{
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    } */

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

    void ShowToast(String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    HtmlCompat.fromHtml("<font color='red'>" + msg +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show();
        }


    }

    ///////////// CÁCH SỬ DỤNG OKHTTP GET/POST ///////////////
    //Hàm mẫu sử dụng phương thức GET - chỉ tham khảo
    void doGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtString.setText(myResponse);
                        Log.d("K45",myResponse);
                    }
                });
            }
        });
    }

    //Hàm mẫu sử dụng phương thức POST - chỉ tham khảo
    void doPost(String url,String key, String value) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(key,value)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("K45",response.body().string());
            }
        });
    }
}