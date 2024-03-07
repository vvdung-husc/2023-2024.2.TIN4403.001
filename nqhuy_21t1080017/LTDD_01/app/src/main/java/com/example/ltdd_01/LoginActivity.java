package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ReactiveGuide;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL = "http://192.168.3.105:4080";
    static String   _phonenumberLogined;
    private CheckBox hienthimatkhau;
    private EditText password;
    private EditText phonenumber;
    private Button btndangnhap;
    private ImageButton imgbtnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hienthimatkhau = findViewById(R.id.hienthimatkhau);
        password = findViewById(R.id.password);
        btndangnhap = findViewById(R.id.btndangnhap);
        imgbtnback = findViewById(R.id.imgbtnback);
        phonenumber = findViewById(R.id.phonenumber);

//        btndangnhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tạo Intent để chuyển từ LoginActivity sang RegisterActivity
//                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
//                startActivity(intent);
//            }
//        });

        hienthimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienthimatkhau.isChecked()) {
                    // Hiển thị mật khẩu
                    password.setTransformationMethod(null);
                } else {
                    // Ẩn mật khẩu
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Hàm sử lý sự kiện click button login
                String phone = phonenumber.getText().toString();
                String pass = password.getText().toString();
                Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + phone + "/" + pass);
                if (phone.length() < 10 || pass.length() < 3){
                    ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                    return;
                }
                try {
                    //Gọi hàm dịch vụ Login
                    //apiLogin(user,pass);
                    okhttpApiLogin(phone,pass);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi icon quay lại được nhấn
                onBackPressedAction();
            }
        });
    }

    public void onBackPressedAction() {
        // Xử lý sự kiện khi icon quay lại được nhấn
        finish(); // hoặc thực hiện logic quay lại khác tùy thuộc vào yêu cầu của bạn
    }

//    public void apiLogin(String sdt, String pass) throws IOException {
//
//        String json = "{\"Phone number\":\"" + sdt + "\",\"password\":\"" + pass +"\"}";
//        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
//        Log.d("K44",json);
//
//        boolean bOk = (sdt.equals("0987654321") && pass.equals("123456"));
//        if (bOk){
//            _phonenumberLogined = "Nguyễn Quốc Huy";
//            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//            startActivity(intent);
//        }
//        else{
//            LoginActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + sdt + "/" + pass + "]";
//                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
//                    View view = toast.getView();
//                    view.setBackgroundColor(Color.GREEN);
//                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//                    toastMessage.setTextColor(Color.RED);
//                    toast.show();                }
//            });
//        }
//    }//void apiLogin(String user, String pass) throws IOException {

    void okhttpApiLogin(String phone, String pass) throws IOException{
        String json = "{\"phonenumber\":\"" + phone + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);

        RequestBody body = new FormBody.Builder()
                .add("phonenumber", phone)
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
                String errStr = "Số điện thoại hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("TIN4403","onFailure\n" + errStr);
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
                Log.d("TIN4403",errStr);
                if (!response.isSuccessful()){
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                _phonenumberLogined = phone;
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
                        Log.d("TIN4403",myResponse);
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
                Log.d("TIN4403",response.body().string());
            }
        });
    }
}