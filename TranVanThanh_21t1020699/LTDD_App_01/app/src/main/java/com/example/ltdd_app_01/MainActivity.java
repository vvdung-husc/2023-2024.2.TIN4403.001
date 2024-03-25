package com.example.ltdd_app_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL;
    static String   _usernameLogined;
    EditText username,password;
    Button loginButton;
    TextView signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _URL = "http://192.168.3.106:4080";
        //Khởi tạo các biến điều khiển tương ứng trong layout
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        signupBtn = (Button) findViewById(R.id.signupBtn);

        //Cài đặt sự kiện Click cho Button Login
        loginButton.setOnClickListener(new CButtonLogin());

        //Cài đặt sự kiện Click cho Button Register
        signupBtn.setOnClickListener(new CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = username.getText().toString();
            String pass = password.getText().toString();
            Log.d("K44","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//public void onClick(View v) {//Hàm sử lý sự kiện click button login
    }//public class CButtonLogin  implements View.OnClickListener {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        }
    }//public class CButtonRegister implements View.OnClickListener {

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K44",json);

        boolean bOk = (user.equals("vvdung") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Võ Việt Dũng";
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    ShowToast(getApplicationContext(),str);
                }
            });
        }
    }
    void okhttpApiLogin(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();

        Request request = new Request.Builder()
                .url(_URL + "/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                MainActivity.this.runOnUiThread(new Runnable() {
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
        });//client.newCall(request).enqueue(new Callback() {
    } //void okhttpApiLogin(String user, String pass) throws IOException{
    static public void ShowToast(Context ctx, String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(ctx,msg,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(ctx,
                    HtmlCompat.fromHtml("<font color='red'>" + msg +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show();
        }

    }//void apiLogin(String user, String pass) throws IOException {
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
                MainActivity.this.runOnUiThread(new Runnable() {
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

}//public class MainActivity extends AppCompatActivity {
