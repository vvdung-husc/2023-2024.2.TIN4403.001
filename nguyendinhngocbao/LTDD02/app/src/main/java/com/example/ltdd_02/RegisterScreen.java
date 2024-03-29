package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
    EditText m_edtUser,m_edtPass,m_edtName,m_edtEmail; //Biến điều khiển EditText
    Button m_btnRegister; //Biến điều khiển Đăng nhập

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUserName);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_edtName = (EditText)findViewById(R.id.edtFullName);
        m_edtEmail = (EditText)findViewById(R.id.edtEmail);
        m_btnRegister = (Button) findViewById(R.id.btnSubmit);
        //Cài đặt sự kiện Click cho Button Register
        m_btnRegister.setOnClickListener(new RegisterScreen.CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("TIN4403","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                ShowToast("Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Register

                JSONObject oUser = new JSONObject();
                oUser.put("username",user);
                oUser.put("password",pass);
                oUser.put("fullname",m_edtName.getText().toString());
                oUser.put("email",m_edtEmail.getText().toString());
                Log.d("TIN4403",oUser.toString());
                String json = oUser.toString();
                Log.d("TIN4403",json);
                okhttpApiRegister(oUser);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//public class CButtonRegister implements View.OnClickListener {

    private void ShowToast(String str) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    HtmlCompat.fromHtml("<font color='red'>" + str +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show();
        }
    }

    void okhttpApiRegister(JSONObject oUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, API.JSON);

        Request request = new Request.Builder()
                .url(Global._URL + "/register")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng ký lỗi.\n" + e.getMessage();
                Log.d("TIN4403","onFailure\n" + errStr);
                RegisterScreen.this.runOnUiThread(new Runnable() {
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
                    Log.d("TIN4403",strMsg);
                    RegisterScreen.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), strMsg,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                String strMsg = "Đăng ký thành công tài khoản [ " + m_edtUser.getText().toString() + " ]";
                Log.d("TIN4403",strMsg);
                RegisterScreen.this.runOnUiThread(new Runnable() {
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