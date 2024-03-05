package com.example.myapplication001;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePass extends AppCompatActivity {
    EditText editTextOldPassword, editTextNewPassword, editTextConfirmPassword;
    Button buttonChangePassword, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);

        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý đăng xuất ở đây
                logout();
            }
        });
    }

    private void changePassword() {
        String oldPassword = editTextOldPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        // Kiểm tra các trường nhập liệu
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API để thay đổi mật khẩu
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("old_password", oldPassword)
                .add("new_password", newPassword)
                .build();

        Request request = new Request.Builder()
                .url("YOUR_CHANGE_PASSWORD_API_URL")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangePass.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePass.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Điều hướng đến màn hình đăng nhập sau khi thay đổi mật khẩu thành công
                    Intent intent = new Intent(ChangePass.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng activity hiện tại
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePass.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void logout() {
        // Thực hiện các thao tác đăng xuất, ví dụ: xóa token, xóa dữ liệu người dùng đã lưu trữ trên thiết bị, vv.
        // Sau đó chuyển hướng đến màn hình đăng nhập
        Intent intent = new Intent(ChangePass.this, MainActivity.class);
        startActivity(intent);
        finish(); // Đóng activity hiện tại
    }
}
