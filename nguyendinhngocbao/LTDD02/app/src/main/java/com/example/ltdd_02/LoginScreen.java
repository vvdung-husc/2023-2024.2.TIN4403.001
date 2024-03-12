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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginScreen extends AppCompatActivity {
    static String   _usernameLogined;
    EditText m_edtUserLog,m_edtPassLog; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUserLog = (EditText)findViewById(R.id.edtNameLog);
        m_edtPassLog = (EditText)findViewById(R.id.edtPassLog);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());
    }

    public class CButtonLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String userLog = m_edtUserLog.getText().toString();
            String passLog = m_edtPassLog.getText().toString();
            Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + userLog + "/" + passLog);
            if (userLog.length() < 3 || passLog.length() < 6) {
                ShowToast("Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                //apiLogIn(userLog, passLog);
                okhttpApiLogin(userLog,passLog);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void apiLogIn(String userLog, String passLog) throws IOException {
        String json = "{\"username\":\"" + userLog + "\",\"password\":\"" + passLog +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (userLog.equals("nbao") && passLog.equals("123456"));
        if (bOk){
            _usernameLogined = userLog;
            Intent intent = new Intent(getApplicationContext(),UserScreen.class);
            startActivity(intent);
        }
        else{
            LoginScreen.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + userLog + "/" + passLog + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();

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
                .url("https://192.168.3.114:4080/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()){
                    LoginScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                _usernameLogined = user;
                Intent intent = new Intent(getApplicationContext(),UserScreen.class);
                startActivity(intent);

            }
        });//client.newCall(request).enqueue(new Callback() {
    }

    private void ShowToast(String str) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    HtmlCompat.fromHtml("<font color='red'>" + str +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
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
                LoginScreen.this.runOnUiThread(new Runnable() {
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