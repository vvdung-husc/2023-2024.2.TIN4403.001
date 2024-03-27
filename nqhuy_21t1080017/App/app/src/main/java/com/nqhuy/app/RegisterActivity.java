package com.nqhuy.app;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText usenamesignup,emailsignup,passsignup,repasssignup,fullnamesignup;
    Button btndangki;
    CheckBox checkBoxMatKhau;
    TextView btnlogin;
    ImageView btnBack;
    static String  fullname,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usenamesignup = findViewById(R.id.username);
        emailsignup = findViewById(R.id.email);
        passsignup = findViewById(R.id.pass);
        repasssignup = findViewById(R.id.confirmpass);
        fullnamesignup = findViewById(R.id.fullname);

        btndangki = findViewById(R.id.btnRegister);
        btnlogin = findViewById(R.id.lblLogin);
        checkBoxMatKhau = findViewById(R.id.hienmatkhau);
        btnBack = findViewById(R.id.btnBack);
        //Hiển thị mật khẩu
        checkBoxMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxMatKhau.isChecked()==true){
                    passsignup.setTransformationMethod(null);
                    repasssignup.setTransformationMethod(null);
                }else{
                    passsignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    repasssignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btndangki.setOnClickListener(new CButtonSignup());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public class CButtonSignup implements View.OnClickListener{
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String emailPattern = "[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}";
            String user = usenamesignup.getText().toString();
            String email = emailsignup.getText().toString();
            String pass = passsignup.getText().toString();
            String repass = repasssignup.getText().toString();
            String fullname = fullnamesignup.getText().toString();
            Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6) {
                Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            if(!email.matches(emailPattern) && email.length()>0){
                Toast toast = Toast.makeText(getApplicationContext(),"Email không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            if (pass.compareTo(repass) != 0){
                Toast toast = Toast.makeText(getApplicationContext(),"Mật khẩu trùng khớp!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //apiSignup(user,email,pass,repass);
                //Gọi hàm dịch vụ Register
                JSONObject oUser = new JSONObject();
                oUser.put("username",user);
                oUser.put("password",pass);
                oUser.put("fullname",fullname);
                oUser.put("email",email);
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
    }
//    void apiSignup(String username,String email, String pass,String repass) throws IOException {
//        String json = "{\"username\":\"" + username + "\",\"password\":\"" + pass +"\"}";
//        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
//        Log.d("K45",json);
//        boolean bOk = pass.equals(repass);
//        if (bOk){
//            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//            startActivity(intent);
//        }
//        else{
//            RegisterActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String str = "Mật khẩu không trùng khớp";
//                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
//                    View view = toast.getView();
//                    view.setBackgroundColor(Color.GREEN);
//                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//                    toastMessage.setTextColor(Color.RED);
//                    toast.show();                }
//            });
//
//        }
//    }
    void okhttpApiRegister(JSONObject oUser) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = oUser.toString();
        RequestBody body = RequestBody.create(json, LoginActivity.JSON);

        Request request = new Request.Builder()
                .url(LoginActivity._URL + "/register")
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
                String strMsg = "Đăng ký thành công tài khoản [ " + usenamesignup.getText().toString() + " ]";
                Log.d("K45",strMsg);
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),strMsg,Toast.LENGTH_SHORT).show();
                    }
                });
                fullname = fullnamesignup.getText().toString();
                email = emailsignup.getText().toString();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}