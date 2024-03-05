package com.le.pp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    EditText ed_userName;
    EditText ed_passWord;
    EditText ed_fullName;

    Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_userName = (EditText)findViewById(R.id.reg_un);
        ed_passWord = (EditText)findViewById(R.id.reg_pw);
        ed_fullName = (EditText)findViewById(R.id.reg_fn);
        btnReg = (Button) findViewById(R.id.btn_register);

        btnReg.setOnClickListener(new CButtonSignUp());
    }

    public class CButtonSignUp implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ed_userName = (EditText)v.findViewById(R.id.reg_un);
            ed_passWord = (EditText)v.findViewById(R.id.reg_pw);
            ed_fullName = (EditText)v.findViewById(R.id.reg_fn);
            try {
                Log.d("K45", "clicked");
                okhttpApiSignUp(ed_fullName.getText().toString(), ed_userName.getText().toString(), ed_passWord.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void okhttpApiSignUp(String fullname, String user, String pass) throws IOException {
        Log.d("K45", "ok");
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .add("fullname", fullname)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.3.128:4080/register")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Đăng kí không thành công!.\n" + e.getMessage();
                Log.d("K45","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_LONG).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Đăng kí thành công\n" + response.body().string();
                Log.d("K45",errStr);
                if (!response.isSuccessful()) {
                    Log.d("K45", "pass");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });//client.newCall(request).enqueue(new Callback() {
    }
}