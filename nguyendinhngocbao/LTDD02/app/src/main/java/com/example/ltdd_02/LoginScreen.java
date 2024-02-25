package com.example.ltdd_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoginScreen extends AppCompatActivity {
    static String   _usernameLogined;
    EditText m_edtUserLog,m_edtPassLog; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUserLog = (EditText)findViewById(R.id.edtNameLog);
        m_edtPassLog = (EditText)findViewById(R.id.edtPassLog);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());
    }

    public class CButtonLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String userLog = m_edtUserLog.getText().toString();
            String passLog = m_edtPassLog.getText().toString();
            Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + userLog + "/" + passLog);
            if (userLog.length() < 3 || passLog.length() < 6) {
                Toast toast = Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogIn(userLog, passLog);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void apiLogIn(String userLog, String passLog) throws IOException {
        String json = "{\"username\":\"" + userLog + "\",\"password\":\"" + passLog +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K45",json);

        boolean bOk = (userLog.equals("nbao") && passLog.equals("123456"));
        if (bOk){
            _usernameLogined = userLog;
            Intent intent = new Intent(getApplicationContext(),UserScreen.class);
            startActivity(intent);
        }
        else{
            LoginScreen.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + userLog + "/" + passLog + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();                }
            });
        }
    }

}