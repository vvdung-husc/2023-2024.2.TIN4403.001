package com.ltdd.ltdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
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
    //thay đổi _URL đúng với IP đang chạy dịch vu WebService
    static String _URL = "http://192.168.3.125:5080";//"https://dev.husc.edu.vn/tin4403/api";
    static String   _usernameLogined;// Hiển thị tại Form User sau khi đã đăng nhập
    EditText edtUser,edtPass; //Biến điều khiển EditText
    Button btnLogin; //Biến điều khiển Đăng nhập
    TextView lblRegister;//Biến điều khiển Đăng ký mới
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        edtUser = (EditText)findViewById(R.id.edtUsername);
        edtPass = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        lblRegister = (TextView) findViewById(R.id.lblRegister);

        // Click cho Button Login
        btnLogin.setOnClickListener(new CButtonLogin());

        // Click cho Button Register
        lblRegister.setOnClickListener(new CButtonRegister());

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();
            Log.d("TIN4403","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Login

                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//sự kiện click button register
            Intent i = new Intent(getApplicationContext(), activity_register.class);
            startActivity(i);
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("TIN4403",json);

        boolean bOk = (user.equals("tvn1611") && pass.equals("161103"));
        if (bOk){
            Log.d("TIN4403", "loi");
            _usernameLogined = "Trương Văn Nhật";
            Intent intent = new Intent(getApplicationContext(),activity_user.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không đúng [" + user + "/" + pass + "]";
                    ShowToast(getApplicationContext(),str);
                }
            });
        }
    }

    void okhttpApiLogin(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("TIN4403",json);
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
                String baoloi = "Tài khoản hoặc mật khẩu không đúng.\n" + e.getMessage();
                Log.d("TIN4403","onFailure\n" + baoloi);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),baoloi,Toast.LENGTH_SHORT).show();
                    }
                });

                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String baoloi = "Tài khoản hoặc mật khẩu không đúng.\n" + response.body().string();
                Log.d("TIN4403",baoloi);
                if (!response.isSuccessful()){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),baoloi,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                _usernameLogined = user;
                Intent intent = new Intent(getApplicationContext(),activity_user.class);
                startActivity(intent);

            }
        });
    }

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


    }

    // ham get okhttp
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
                        Log.d("TIN4403",myResponse);
                    }
                });
            }
        });
    }

    //post okhttp
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
                Log.d("TIN4403",response.body().string());
            }
        });
    }

}