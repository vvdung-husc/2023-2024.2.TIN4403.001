package com.example.ltdd_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassScreen extends AppCompatActivity {
    EditText m_edtNameCP, m_edtOldPass, m_edtNewPass, m_edtReNewPass;
    Button m_btnSubmitCP;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_screen);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtNameCP = (EditText)findViewById(R.id.edtNameCP);
        m_edtOldPass = (EditText)findViewById(R.id.edtOldPass);
        m_edtNewPass = (EditText)findViewById(R.id.edtNewPass);
        m_edtReNewPass = (EditText)findViewById(R.id.edtReNewPass);
        m_btnSubmitCP = (Button) findViewById(R.id.btnSubmit);

        //Cài đặt sự kiện Click cho Button Register
        m_btnSubmitCP.setOnClickListener(new CButtonChangePass());
    }

    private class CButtonChangePass implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            String nameCP = m_edtNameCP.getText().toString();
            String oldPass = m_edtOldPass.getText().toString();
            String newPass = m_edtNewPass.getText().toString();
            String reNewPass = m_edtReNewPass.getText().toString();

            // Kiểm tra các trường dữ liệu
            if (nameCP.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || reNewPass.isEmpty()) {
                Toast.makeText(ChangePassScreen.this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(reNewPass)) {
                Toast.makeText(ChangePassScreen.this, "Mật khẩu mới không khớp.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi hàm thay đổi mật khẩu
            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", nameCP);
                requestBody.put("old_password", oldPass);
                requestBody.put("new_password", newPass);

                changePassword(requestBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void changePassword(JSONObject requestBody) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(requestBody.toString(), LoginScreen.JSON);

        Request request = new Request.Builder()
                .url(LoginScreen._URL + "/change_password")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showToast("Thay đổi mật khẩu thất bại.");
                    return;
                }

                showToast("Thay đổi mật khẩu thành công.");
                Intent intent = new Intent(ChangePassScreen.this, LoginScreen.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                showToast("Thay đổi mật khẩu thất bại: " + e.getMessage());
                call.cancel();
            }
        });
    }

    private void showToast(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePassScreen.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}