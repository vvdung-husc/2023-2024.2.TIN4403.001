package com.example.ltdd_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import java.text.BreakIterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterScreen extends AppCompatActivity {
    EditText m_edtUserName, m_edtPass, m_edtRePass, m_edtFullName, m_edtEmail; //Biến điều khiển EditText
    Button m_btnSubmit; //Biến điều khiển Đăng nhập
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUserName = (EditText)findViewById(R.id.edtName);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_edtRePass = (EditText)findViewById(R.id.edtRePass);
        m_edtFullName = (EditText)findViewById(R.id.edtFullName);
        m_edtEmail = (EditText)findViewById(R.id.edtEmail);
        m_btnSubmit = (Button) findViewById(R.id.btnSubmit);


        //Cài đặt sự kiện Click cho Button Register
        m_btnSubmit.setOnClickListener(new CButtonRegister());
    }

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            String name = m_edtUserName.getText().toString();
            String pass = m_edtPass.getText().toString();
            String rePass = m_edtRePass.getText().toString();
            Log.d("TIN4403","CLICK BUTTON LOGIN ACCOUNT " + name + "/" + pass);
            if((name.length() < 3 || pass.length() < 6)&& rePass != pass){
                Toast toast = Toast.makeText(getApplicationContext(),"Không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Register

                JSONObject oUser = new JSONObject();
                oUser.put("username",name);
                oUser.put("password",pass);
                oUser.put("fullname",m_edtFullName.getText().toString());
                oUser.put("email",m_edtEmail.getText().toString());
                Log.d("TIN4403",oUser.toString());
                String json = oUser.toString();
                Log.d("TIN4403",json);
                okhttpApiRegister(oUser);
            } catch (JSONException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void okhttpApiRegister(JSONObject oUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, LoginScreen.JSON);

        Request request = new Request.Builder()
                .url(LoginScreen._URL + "/register")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String strMsg = "Đăng ký lỗi.\n" + response.body().string();
                    Log.d("TIN4403", strMsg);
                    RegisterScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Đăng ký thành công tài khoản [ " + m_edtUserName.getText().toString() + " ]";
                Log.d("TIN4403", strMsg);
                RegisterScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng ký lỗi.\n" + e.getMessage();
                Log.d("TIN4403", "onFailure\n" + errStr);
                RegisterScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }
        }
        );
    }
}