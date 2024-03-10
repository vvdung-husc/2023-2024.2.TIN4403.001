package com.hnc.ltud_01;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText m_edtUser,m_edtPass,m_edtRePass,m_edtName,m_edtEmail; //Biến điều khiển EditText
    Button m_btnRegister; //Biến điều khiển Đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.r_editUsername);
        m_edtPass = (EditText)findViewById(R.id.r_editPassWord);
        m_edtRePass = (EditText)findViewById(R.id.r_editCFPass);
        m_edtName = (EditText)findViewById(R.id.editName);
        m_edtEmail = (EditText)findViewById(R.id.editEmail);
        m_btnRegister = (Button) findViewById(R.id.button);
        //Cài đặt sự kiện Click cho Button Register
        m_btnRegister.setOnClickListener(new RegisterActivity.CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                MainActivity.ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            String repass = m_edtRePass.getText().toString();
            if (pass.compareTo(repass) != 0){
                MainActivity.ShowToast(getApplicationContext(),"Mật khẩu không chính xác!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Register

                JSONObject oUser = new JSONObject();
                oUser.put("username",user);
                oUser.put("password",pass);
                oUser.put("fullname",m_edtName.getText().toString());
                oUser.put("email",m_edtEmail.getText().toString());
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
                String strMsg = "Đăng ký thành công tài khoản [ " + m_edtUser.getText().toString() + " ]";
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
