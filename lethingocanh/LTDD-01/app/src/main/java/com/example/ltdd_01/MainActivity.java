package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static String   _usernameLogined;
    EditText edtname,edtpass;
    Button btnlogin;
    TextView btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        edtname = (EditText) findViewById(R.id.edtname);
        edtpass = (EditText) findViewById(R.id.edtpass);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        btnregister = (TextView) findViewById(R.id.btnregister);

        //Cài đặt sự kiện Click cho Button Login
        btnlogin.setOnClickListener(new CButtonLogin());

        //Cài đặt sự kiện Click cho Button Register
        btnregister.setOnClickListener(new CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = edtname.getText().toString();
            String pass = edtpass.getText().toString();
            Log.d("K44","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                Toast toast = Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//public void onClick(View v) {//Hàm sử lý sự kiện click button login
    }//public class CButtonLogin  implements View.OnClickListener {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        }
    }//public class CButtonRegister implements View.OnClickListener {

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json, Toast.LENGTH_SHORT).show();
        Log.d("K44",json);

        boolean bOk = (user.equals("vvdung") && pass.equals("123456"));
        if (bOk){
            _usernameLogined = "Võ Việt Dũng";
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.GREEN);
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();                }
            });
        }
    }//void apiLogin(String user, String pass) throws IOException {
}//public class MainActivity extends AppCompatActivity {
