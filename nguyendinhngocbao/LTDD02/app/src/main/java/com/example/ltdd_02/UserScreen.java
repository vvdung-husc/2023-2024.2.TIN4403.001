package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class UserScreen extends AppCompatActivity {
    TextView m_txtUserName, m_txtName, m_txtEmail;
    Button m_btnLogout;
    Button m_btnChangePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_txtUserName = (TextView)findViewById(R.id.txtUser);
        m_txtName = (TextView)findViewById(R.id.txtName);
        m_txtEmail = (TextView)findViewById(R.id.txtEmail);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_btnChangePass = (Button) findViewById(R.id.btnChangePass);

        String s = "Chào mừng";

        // Cập nhật giao diện với thông tin tài khoản
        m_txtUserName.setText("Tên đăng nhập: " + User._username);
        m_txtName.setText("Tên người dùng: " + User._fullname);
        m_txtEmail.setText("Email: " + User._email);
        // Gửi yêu cầu để lấy thông tin tài khoản từ máy chủ
        getUserInfoFromServer();


        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        m_btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChangePassScreen.class);
                startActivity(i);
            }
        });
    }

    private void getUserInfoFromServer() {
        OkHttpClient client = new OkHttpClient();

        // Tạo JSON object chứa token
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", Global._token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo request body từ JSON object
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject), API.JSON);

        // Tạo request
        Request request = new Request.Builder()
                .url(Global._URL + "/userinfo")
                .post(requestBody)
                .build();

        // Thực hiện request bất đồng bộ
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Xử lý khi có lỗi
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    // Xử lý response từ máy chủ và cập nhật giao diện
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    // Xử lý khi có lỗi từ máy chủ
                }
            }
        });
    }
}