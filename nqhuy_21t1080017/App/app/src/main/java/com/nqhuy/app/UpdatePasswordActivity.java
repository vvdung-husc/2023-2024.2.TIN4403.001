package com.nqhuy.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdatePasswordActivity extends AppCompatActivity {
    EditText edt_olrpass,edt_pass,edt_confirmpass;
    CheckBox checkBoxMatKhau;
    ImageView btnBack;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        edt_olrpass = findViewById(R.id.olrpass);
        edt_pass = findViewById(R.id.pass);
        edt_confirmpass = findViewById(R.id.confirmpass);

        btnBack = findViewById(R.id.btnBack);
        btnUpdate = findViewById(R.id.button_capnhat);
        checkBoxMatKhau = findViewById(R.id.hienmatkhau);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInforActivity.class);
                startActivity(intent);
                finish();
            }
        });

        checkBoxMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxMatKhau.isChecked()==true){
                    edt_olrpass.setTransformationMethod(null);
                    edt_pass.setTransformationMethod(null);
                    edt_confirmpass.setTransformationMethod(null);
                }else{
                    edt_olrpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = LoginActivity._usernameLogined;
                String oldpass = edt_olrpass.getText().toString();

                String pass = edt_pass.getText().toString();
                String repass = edt_confirmpass.getText().toString();

                if (pass.compareTo(repass) != 0){
                    Toast toast = Toast.makeText(getApplicationContext(),"Mật khẩu mới trùng khớp!",Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }
                if (oldpass.compareTo(LoginActivity._passwordLogined) != 0){
                    Toast toast = Toast.makeText(getApplicationContext(),"Mật khẩu cũ không đúng!",Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadLogin(user,oldpass, pass);
                    }
                }).start();


            }
        });
    }

    void threadLogin(String user, String oldpass, String pass){
        //Tạo chuỗi theo cấu trúc JSON
        String jsonBody = "{\"username\":\"" + user + "\",\"password\":\"" + oldpass +"\"}";
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
        threadUpdatePass(pass);

    }//void threadLogin(String user, String pass) {

    void threadUpdatePass(String pass){

        OkHttpClient client = new OkHttpClient();
        Log.i("TOKEN", Global._token);
        Map<String,String> mapHeader = new HashMap<>();
        mapHeader.put("token",Global._token);
        String json = "{\"password\":\"" + pass +"\"}";
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
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}