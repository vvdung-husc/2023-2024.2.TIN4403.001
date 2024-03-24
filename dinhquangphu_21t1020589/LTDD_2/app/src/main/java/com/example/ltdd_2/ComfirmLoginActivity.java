package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComfirmLoginActivity extends AppCompatActivity {

    TextView m_textUserName , m_textName , m_textMail;
    Button btn_DoiNK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfirm_login);

        m_textUserName = findViewById(R.id.txtUser);
        m_textName = findViewById(R.id.txtName);
        m_textMail  = findViewById(R.id.txtGmail);
        btn_DoiNK = findViewById(R.id.btnDoiMK);

        // Cập nhật giao diện với thông tin tài khoản
        m_textUserName.setText("Tên đăng nhập: " + User._username);
        m_textName.setText("Tên người dùng: " + User._fullname);
        m_textMail.setText("Email: " + User._email);
        // Gửi yêu cầu để lấy thông tin tài khoản từ máy chủ
        getUserInfoFromServer();


        btn_DoiNK.setOnClickListener(new CButtonDoiMK());
    }

    public class CButtonDoiMK  implements View.OnClickListener {
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangKy
            Intent i = new Intent(ComfirmLoginActivity.this, changePassword.class);
            startActivity(i);
        }
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