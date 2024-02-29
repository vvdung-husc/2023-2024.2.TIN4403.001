package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    static String   _sdtLogined;
    private CheckBox hienthimatkhau;
    private EditText matkhau;
    private EditText sodienthoai;
    private Button btndangnhap;
    private ImageButton imgbtnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hienthimatkhau = findViewById(R.id.hienthimatkhau);
        matkhau = findViewById(R.id.matkhau);
        btndangnhap = findViewById(R.id.btndangnhap);
        imgbtnback = findViewById(R.id.imgbtnback);
        sodienthoai = findViewById(R.id.sodienthoai);

//        btndangnhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tạo Intent để chuyển từ LoginActivity sang RegisterActivity
//                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
//                startActivity(intent);
//            }
//        });

        hienthimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hienthimatkhau.isChecked()) {
                    // Hiển thị mật khẩu
                    matkhau.setTransformationMethod(null);
                } else {
                    // Ẩn mật khẩu
                    matkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Hàm sử lý sự kiện click button login
                String sdt = sodienthoai.getText().toString();
                String pass = matkhau.getText().toString();
                Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + sdt + "/" + pass);
                if (sdt.length() < 3 || pass.length() < 6){
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                    Toast toast = Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();
                    return;
                }
                try {
                    //Gọi hàm dịch vụ Login
                    apiLogin(sdt,pass);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        imgbtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi icon quay lại được nhấn
                onBackPressedAction();
            }
        });
    }

    public void onBackPressedAction() {
        // Xử lý sự kiện khi icon quay lại được nhấn
        finish(); // hoặc thực hiện logic quay lại khác tùy thuộc vào yêu cầu của bạn
    }

    public void apiLogin(String sdt, String pass) throws IOException {

        String json = "{\"Phone number\":\"" + sdt + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K44",json);

        boolean bOk = (sdt.equals("0987654321") && pass.equals("123456"));
        if (bOk){
            _sdtLogined = "Nguyễn Quốc Huy";
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        }
        else{
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + sdt + "/" + pass + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();                }
            });
        }
    }//void apiLogin(String user, String pass) throws IOException {
}