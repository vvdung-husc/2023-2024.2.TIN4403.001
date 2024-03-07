package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private EditText username, phonenumber, password, configpassword;
    private Button btndangky;
    private ImageButton imgbtnback;
    private CheckBox hienthimatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        hienthimatkhau = findViewById(R.id.hienthimatkhau);
        btndangky = findViewById(R.id.btndangky);
        imgbtnback = findViewById(R.id.imgbtnback);
        username = findViewById(R.id.username);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        configpassword = findViewById(R.id.configpassword);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phonenumber.getText().toString();
                String pass = password.getText().toString();

                Log.d("TIN4403","CLICK BUTTON LOGIN ACCOUNT " + phone + "/" + pass);
                if (phone.length() < 10 || pass.length() < 3){
                    RegisterActivity.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                    return;
                }
                String repass = configpassword.getText().toString();
                if (pass.compareTo(repass) != 0){
                    RegisterActivity.ShowToast(getApplicationContext(),"Mật khẩu không chính xác!");
                    return;
                }
                try {
                    //Gọi hàm dịch vụ Register

                    JSONObject oUser = new JSONObject();
                    oUser.put("username",username);
                    oUser.put("password",pass);
                    oUser.put("phonenumber",phone);
                    oUser.put("fullname",username.getText().toString());
                    Log.d("TIN4403",oUser.toString());
                    String json = oUser.toString();
                    Log.d("TIN4403",json);
                    okhttpApiRegister(oUser);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        hienthimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienthimatkhau.isChecked()) {
                    // Hiển thị mật khẩu
                    password.setTransformationMethod(null);
                    configpassword.setTransformationMethod(null);
                } else {
                    // Ẩn mật khẩu
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    configpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedAction();
            }
        });
    }

    public void onBackPressedAction() {
        // Xử lý sự kiện khi icon quay lại được nhấn
        finish(); // hoặc thực hiện logic quay lại khác tùy thuộc vào yêu cầu của bạn
    }

    private void saveCredentials(String phone, String pass) {
        // Lưu thông tin đăng ký vào SharedPreferences hoặc Database
        // Ví dụ sử dụng SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phone_number", phone);
        editor.putString("password", pass);
        editor.apply();
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

    void okhttpApiRegister(JSONObject oUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, RegisterActivity.JSON);

        Request request = new Request.Builder()
                .url(LoginActivity._URL + "/register")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng ký lỗi.\n" + e.getMessage();
                Log.d("TIN4403","onFailure\n" + errStr);
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()){
                    String strMsg = "Đăng ký lỗi.\n" + response.body().string();
                    Log.d("TIN4403",strMsg);
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Đăng ký thành công tài khoản [ " + username.getText().toString() + " ]";
                Log.d("TIN4403",strMsg);
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
