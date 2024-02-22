package com.example.ltdd_02;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class CButtonLogin implements View.OnClickListener {
    private EditText m_edtUser;
    private EditText m_edtPass;
    private Context mContext;

    public CButtonLogin(Context context, EditText edtUser, EditText edtPass) {
        mContext = context;
        m_edtUser = edtUser;
        m_edtPass = edtPass;
    }

    @Override
    public void onClick(View v) {//Hàm sử lý sự kiện click button login
        String user = m_edtUser.getText().toString();
        String pass = m_edtPass.getText().toString();
        Log.d("K45", "CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
        if (user.length() < 3 || pass.length() < 6) {
            Toast.makeText(mContext, "Tài khoản hoặc mật khẩu không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //Gọi hàm dịch vụ Login
            apiLogin(user, pass);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        Toast.makeText(mContext, json, Toast.LENGTH_SHORT).show();
        Log.d("K45", json);
    }

}
