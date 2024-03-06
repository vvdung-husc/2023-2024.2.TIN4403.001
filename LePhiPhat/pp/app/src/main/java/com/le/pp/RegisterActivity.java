package com.le.pp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    EditText ed_userName;
    EditText ed_passWord;
    EditText ed_fullName;

    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnReg =      findViewById(R.id.btn_register);

        btnReg.setOnClickListener(new CButtonSignUp());
    }

    public class CButtonSignUp implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ed_userName = findViewById(R.id.reg_un);
            ed_passWord = findViewById(R.id.reg_pw);
            ed_fullName = findViewById(R.id.reg_fn);

            try {
                Log.d("K45", "clicked");
                JSONObject user = new JSONObject();
                String userName = (ed_userName == null) ? "p1" : ed_userName.getText().toString();
                String passWord = (ed_passWord == null) ? "p1" : ed_passWord.getText().toString();
                String fullName = (ed_fullName == null) ? "p1" : ed_fullName.getText().toString();

                user.put("username", userName);
                user.put("password", passWord);
                user.put("fullname", fullName);
                Log.d("K45", user.toString());
                okhttpApiRegister(user);
            } catch (Exception e) {
                Log.d("K45", "" + e.getMessage());
            }
        }
    }

    void okhttpApiRegister(JSONObject oUser) {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, MainActivity.JSON);

        Request request = new Request.Builder()
                .url(MainActivity._URL + "/register")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng ký lỗi.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
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
                    Log.d("K45",strMsg);
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Đăng ký thành công tài khoản [ " + ed_fullName.getText().toString() + " ]";
                Log.d("K45",strMsg);
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}