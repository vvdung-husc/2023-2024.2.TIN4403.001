package com.ltdd.codeappsamezalo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChangePassword extends AppCompatActivity {

    EditText  m_edtPass,m_edtRePass,m_edtRePassNew;
    Button m_btnChangePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_edtRePass = (EditText)findViewById(R.id.edtRePass);
        m_edtRePassNew = (EditText)findViewById(R.id.edtRePassNew);
        m_btnChangePass = (Button) findViewById(R.id.btnChangePass);

        m_btnChangePass.setOnClickListener(new ChangePassword.CButtonChangePassword());
    }

    public class CButtonChangePassword implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button change password
            String oldpass = m_edtPass.getText().toString();
            String newpass = m_edtRePass.getText().toString();
            String repass = m_edtRePassNew.getText().toString();
            Log.d("TIN4403","CLICK BUTTON TO UPDATE YOUR PASSWORD " + oldpass+ "/" + newpass);
            if (oldpass.length() < 6 || newpass.length() < 6|| repass.length() < 6){
                MainActivity.ShowToast(getApplicationContext(),"Mật khẩu nhập vào không hợp lệ!");
                return;
            }


            if (oldpass.compareTo(newpass) == 0){
                MainActivity.ShowToast(getApplicationContext(),"Mật khẩu nhập vào không được trùng với nhập khẩu cũ!");
                return;
            }

            if (newpass.compareTo(repass) != 0){
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
                Log.d("TIN4403",oUser.toString());
                String json = oUser.toString();
                Log.d("TIN4403",json);
                okhttpApiChange(oUser);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}