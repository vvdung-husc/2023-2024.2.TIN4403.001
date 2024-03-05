package com.ltdd.ltdd;

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
    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    TextView m_lblRegister;//Biến điều khiển Đăng ký mới
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUsername);
        m_edtPass = (EditText)findViewById(R.id.edtPassword);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        m_lblRegister = (TextView) findViewById(R.id.lblRegister);

        // sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());

        // sự kiện Click cho Button Register
        m_lblRegister.setOnClickListener(new CButtonRegister());

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                ShowToast("Tài khoản hoặc mật khẩu không đúng");
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                //apiLogin(user,pass);
                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), activity_register.class);
            startActivity(i);
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);

        boolean bOk = (user.equals("tvn1611") && pass.equals("tvn1611"));
        if (bOk){
            _usernameLogined = "Văn Nhật";
            Intent intent = new Intent(getApplicationContext(),activity_user.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String str = "Tài khoản hoặc mật khẩu không đúng [" + user + "/" + pass + "]";
                    ShowToast(str);
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
                //.url("https://dev.husc.edu.vn/tin4403/api/login")
                .url("http://192.168.3.103:4080/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không đúng.\n" + e.getMessage();
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
                String errStr = "Tài khoản hoặc mật khẩu không đúng.\n" + response.body().string();
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
                Intent intent = new Intent(getApplicationContext(),activity_user.class);
                startActivity(intent);

            }
        });
    }

    void ShowToast(String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
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