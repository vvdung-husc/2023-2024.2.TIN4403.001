package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Dangky extends AppCompatActivity {

    EditText edttaikhoan,edtmatkhau,edthoten,edtemail;
    Button btndangky;
    TextView btndangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        edttaikhoan=(EditText) findViewById(R.id.edtdktk);
        edtmatkhau=(EditText) findViewById(R.id.edtdkmk);
        edthoten=(EditText) findViewById(R.id.edtdkht);
        edtemail=(EditText) findViewById(R.id.edtdkemail);

        btndangnhap=(TextView) findViewById(R.id.btndangnhap);

        btndangky.setOnClickListener(new ButtonDangKy());
        btndangnhap.setOnClickListener(new ButtonDangNhap());
    }

    public class ButtonDangNhap implements View.OnClickListener{
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
    public class ButtonDangKy  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = edttaikhoan.getText().toString();
            String pass = edtmatkhau.getText().toString();
            String hoten = edthoten.getText().toString();
            String email = edtemail.getText().toString();
            Log.d("K45","CLICK BUTTON DANG KY " + user + "/" + pass + "/" + hoten + "/" +email);
            if (user.length() < 3 || pass.length() < 6 || hoten.length() < 3 || email.length() < 10){
                ShowToast("Thông tin không hợp lệ!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                //apiLogin(user,pass);
                okhttpRegister(user,pass,hoten,email);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//public void onClick(View v) {//Hàm sử lý sự kiện click button login
    }



    void okhttpRegister(String user,String pass,String fullname,String email) throws IOException {
        String json = "{\"username\":\"" + user
                + "\",\"password\":\"" + pass
                +"\" ,\"fullname\":\""+fullname
                +"\",\"email\":\""+email+"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username",user)
                .add("password",pass)
                .add("fullname",fullname)
                .add("email",email)
                .build();

        Request request = new Request.Builder()
                //.url("https://dev.husc.edu.vn/tin4403/api/login")
                .url("http://192.168.3.104:4080/Dangky/register")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản đã tồn tại.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài Khoản đã tồn tại 1.\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()){
                    Dangky.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }
    void ShowToast(String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    HtmlCompat.fromHtml("<font color='red'>" + msg +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show();
        }


    }

}