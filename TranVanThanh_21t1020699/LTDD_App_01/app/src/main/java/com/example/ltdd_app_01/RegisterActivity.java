package com.example.ltdd_app_01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText userName,password,confirmPassword,fullName,email; //Biến điều khiển EditText
    Button registerButton; //Biến điều khiển Đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        fullName = (EditText)findViewById(R.id.fullName);
        email = (EditText)findViewById(R.id.email);
        registerButton = (Button) findViewById(R.id.registerButton);
        //Cài đặt sự kiện Click cho Button Register
        registerButton.setOnClickListener(new CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            String user = userName.getText().toString();
            String pass = password.getText().toString();
            Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                MainActivity.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            String repass = confirmPassword.getText().toString();
            if (pass.compareTo(repass) != 0){
                MainActivity.ShowToast(getApplicationContext(),"Mật khẩu không chính xác!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Register

                JSONObject oUser = new JSONObject();
                oUser.put("username",user);
                oUser.put("password",pass);
                oUser.put("fullname",fullName.getText().toString());
                oUser.put("email",email.getText().toString());
                Log.d("K45",oUser.toString());
                String json = oUser.toString();
                Log.d("K45",json);
                okhttpApiRegister(oUser);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//public class CButtonRegister implements View.OnClickListener {

    void okhttpApiRegister(JSONObject oUser) throws IOException {
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
                String strMsg = "Đăng ký thành công tài khoản [ " + userName.getText().toString() + " ]";
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

}//public class RegisterActivity