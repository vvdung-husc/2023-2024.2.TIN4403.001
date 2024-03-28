package com.example.ltdd_2;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.text.HtmlCompat;

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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    static String _URL;
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    TextView m_lblRegister;//Biến điều khiển Đăng ký mới
    Button btn_Login;
    Button btn_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _URL = "https://dev.husc.edu.vn/tin4403/api";
        //_URL = "http://192.168.1.7:4080";

        btn_Login = findViewById(R.id.btnDangNhap);
        btn_Register = findViewById(R.id.btnDangKy);

        btn_Register.setOnClickListener(new ButtonDangKy()) ;
        btn_Login.setOnClickListener(new ButtonDangNhap());
    }

    public class ButtonDangKy implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangKy
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }

    }

    public class ButtonDangNhap implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button DangNhap
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }


    static public void ShowToast(Context ctx, String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(ctx,msg,Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.GREEN);
            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.RED);
            toast.show();
        }
        else {
            Toast.makeText(ctx,
                    HtmlCompat.fromHtml("<font color='red'>" + msg +"</font>" , HtmlCompat.FROM_HTML_MODE_LEGACY),
                    Toast.LENGTH_LONG).show();
        }


    }
}