package com.example.myapplication.Activity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.R;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Register extends AppCompatActivity {
    static String _URL = "https://dev.husc.edu.vn/tin4403/api";//http://192.168.3.125:4080
    static  String _fullnameRegistered;
    static  String _emailRegistered;
    EditText username_register;
    EditText fullname_register;
    EditText email_register;
    EditText password_register;
    EditText confirm_password_register;
    Button Register_btn;
    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_register = findViewById(R.id.username);
        fullname_register = findViewById(R.id.fullname);
        email_register = findViewById(R.id.email_toggle);
        password_register = findViewById(R.id.password_toggle2);
        confirm_password_register = findViewById(R.id.password_toggle3);
        Register_btn = findViewById(R.id.registerButton);
        login_btn = findViewById(R.id.btnlogin);

        //admin, email and admin
        Register_btn.setOnClickListener(view -> {
            String username_register_str = username_register.getText().toString();
            String fullname_register_str = fullname_register.getText().toString();
            String email_register_str = email_register.getText().toString();
            String password_register_str = password_register.getText().toString();
            String confirm_password_register_str = confirm_password_register.getText().toString();

            if (password_register_str.equals(confirm_password_register_str)) {
                Toast.makeText(Register.this, "REGISTER SUCCESSFULL", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "REGISTER FAILED", Toast.LENGTH_SHORT).show();
            }

            try {
                JSONObject oUser = new JSONObject();
                oUser.put("username",username_register_str);
                oUser.put("password",password_register_str);
                oUser.put("fullname",fullname_register.getText().toString());
                oUser.put("email",email_register.getText().toString());
                Log.d("K45",oUser.toString());
                String json = oUser.toString();
                Log.d("K45",json);
                okhttpApiRegister(oUser);
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        login_btn.setOnClickListener(view -> {
//            startActivity(new Intent(Register.this, Login.class));
            finish();
        });
    }

    void okhttpApiRegister(JSONObject oUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, Login.JSON);

        Request request = new Request.Builder()
                .url(Login._URL + "/register")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng ký lỗi.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Register.this.runOnUiThread(new Runnable() {
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
                    Log.d("K45",strMsg);
                    Register.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Đăng ký thành công tài khoản [ " + username_register.getText().toString() + " ]";
                Log.d("K45",strMsg);
                Register.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
                    }
                });

                _fullnameRegistered = String.valueOf(fullname_register);
               _emailRegistered = String.valueOf(email_register);

                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);
            }
        });
    }
}