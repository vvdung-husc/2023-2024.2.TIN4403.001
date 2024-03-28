package com.nqhuy.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInforActivity extends AppCompatActivity {

    EditText txtfullname, txtemail;
    TextView txtpassword;
    ImageView btnBack;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.button_capnhat);

        txtfullname = findViewById(R.id.txtfullname);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);



        txtfullname.setText (User._fullname);
        txtemail.setText (User._email);

        txtpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtpassword.getBackground().setColorFilter(getResources().getColor(R.color.CDCDCD), PorterDuff.Mode.SRC_ATOP);
                txtpassword.invalidate(); // Cần phải vẽ lại Button để hiển thị màu mới
                Intent intent = new Intent(getApplicationContext(), UpdatePasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = txtemail.getText().toString().trim();
                String fullname = txtfullname.getText().toString().trim();
                String user = LoginActivity._usernameLogined;
                String pass = LoginActivity._passwordLogined;

                if (fullname.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Email không hợp lệ.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadLogin(user,pass,email,fullname);
                    }
                }).start();


            }
        });

    }

    void threadLogin(String user, String pass, String email, String fullname){
        //Tạo chuỗi theo cấu trúc JSON
        String jsonBody = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("threadUpdate",jsonBody);

        String jsonResponse;
        try {
            jsonResponse = Global._HTTP.POST("/Login",jsonBody);
        }
        catch (IOException e){
            e.printStackTrace();
            Log.e("JSON","Network Error");
            Global.uiShowToast(getApplicationContext(),"Webservice không đang chạy, kiểm tra lại dịch vụ");
            return;
        }

        String strError = "";
        Log.d("JSON",jsonResponse);
        JSONObject oResult = null;
        int r = 0;
        try {
            oResult = new JSONObject(jsonResponse);
            Log.d("oJSON",oResult.toString());
            try {
                r = oResult.getInt("r");
                String m = oResult.getString("m");
                Log.d("r = ", String.valueOf(r));
                Log.d("m = ", m);
                if (r == 1) Global._token = m;
                else strError = oResult.toString();

            } catch (JSONException e) {
                strError = "JSON.m không đúng cấu trúc " + jsonResponse;
            }
        } catch (JSONException e) {
            strError = "JSON trả về không đúng cấu trúc " + jsonResponse;
            Log.e("JSON", strError);
        }

        if (r != 1){
            Global.uiShowToast(getApplicationContext(),strError);
            return;
        }
        threadUserUpdate(email, fullname);

    }//void threadLogin(String user, String pass) {

    void threadUserUpdate(String email, String fullname){

        OkHttpClient client = new OkHttpClient();
        Log.i("TOKEN", Global._token);
        Map<String,String> mapHeader = new HashMap<>();
        mapHeader.put("token",Global._token);
        String json = "{\"email\":\"" + email + "\",\"fullname\":\"" + fullname +"\"}";
        RequestBody body = RequestBody.create(json, LoginActivity.JSON);

        Request request = new Request.Builder()
                .url(Global._URL + "/userupdate")
                .post(body)
                .headers(Headers.of(mapHeader))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Cập nhật thông tin lỗi.\n" + e.getMessage();
                Log.e("THREAD", "onFailure\n" + errStr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_SHORT).show();
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String strMsg = "Cập nhật thông tin lỗi.\n" + response.body().string();
                    Log.e("THREAD", strMsg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Cập nhật thông tin thành công.";
                Log.i("THREAD", strMsg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_SHORT).show();
                    }
                });
                User._fullname = fullname;
                User._email = email;
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
