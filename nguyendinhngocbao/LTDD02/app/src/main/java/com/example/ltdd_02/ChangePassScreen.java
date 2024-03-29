package com.example.ltdd_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassScreen extends AppCompatActivity {
    EditText m_edtNameCP, m_edtOldPass, m_edtNewPass, m_edtReNewPass, m_edtEmailCP;
    CheckBox m_chkName,m_chkEmail,m_chkPass, m_chkOk;
    Button m_btnSubmitCP, m_btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_screen);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtNameCP = (EditText)findViewById(R.id.edtName2);
        m_edtOldPass = (EditText)findViewById(R.id.edtPassOld);
        m_edtNewPass = (EditText)findViewById(R.id.edtPassNew1);
        m_edtReNewPass = (EditText)findViewById(R.id.edtPassNew2);
        m_edtEmailCP = (EditText)findViewById(R.id.edtEmail2);
        m_btnSubmitCP = (Button) findViewById(R.id.btnChange);
        m_btnBack = (Button) findViewById(R.id.btnBack);

        m_chkName = ( CheckBox ) findViewById( R.id.chkName );
        m_chkEmail= ( CheckBox ) findViewById( R.id.chkEmail);
        m_chkPass = ( CheckBox ) findViewById( R.id.chkPass );
        m_chkOk = (CheckBox) findViewById(R.id.chkOk);

        m_chkName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (m_chkName.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    Log.d("CHECKED","m_chkName");
                    m_edtNameCP.setEnabled(true);
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    Log.d("UNCHECK","m_chkName");
                    m_edtNameCP.setEnabled(false);
                }
            }
        });

        m_chkEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (m_chkEmail.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    m_edtEmailCP.setEnabled(true);
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    m_edtEmailCP.setEnabled(false);
                }
            }
        });

        m_chkPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (m_chkPass.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    m_edtOldPass.setEnabled(true);
                    m_edtNewPass.setEnabled(true);
                    m_edtReNewPass.setEnabled(true);
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    m_edtOldPass.setEnabled(false);
                    m_edtNewPass.setEnabled(false);
                    m_edtReNewPass.setEnabled(false);
                }
            }
        });

        m_chkOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptChange();
            }
        });


        m_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getApplicationContext(), UserScreen.class);
                startActivity(inten);
            }
        });
    }

    private void acceptChange() {
        // Kiểm tra xem đã nhập đầy đủ thông tin chưa
        if (m_chkPass.isChecked()) {
            if(m_edtOldPass.getText().toString().equals(Global._userPassword)
                    && m_edtNewPass.getText().toString().equals(m_edtReNewPass.getText().toString())){
                m_btnSubmitCP.setEnabled(true);
                btnSubmit();
            }
            else {
                // Mật khẩu cũ không khớp, hiển thị thông báo
                Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                m_btnSubmitCP.setEnabled(false);
                return;
            }
        }

        if(m_chkName.isChecked()){
            if(m_edtNameCP.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ tên", Toast.LENGTH_SHORT).show();
                m_btnSubmitCP.setEnabled(false);
                return;
            }
            else{
                m_btnSubmitCP.setEnabled(true);
                btnSubmit();
            }
        }

        if(m_chkEmail.isChecked()){
            if(m_edtEmailCP.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy email", Toast.LENGTH_SHORT).show();
                m_btnSubmitCP.setEnabled(false);
                return;
            }
            else{
                m_btnSubmitCP.setEnabled(true);
                btnSubmit();
            }
        }
    }

    private void btnSubmit() {
        m_btnSubmitCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChangeRequest();
            }

            private void sendChangeRequest() {
                // Tạo JSON object chứa thông tin thay đổi
                JSONObject userInfo = new JSONObject();
                try {
                    if (m_chkName.isChecked()) {
                        userInfo.put("fullname", m_edtNameCP.getText().toString());
                    }
                    if (m_chkEmail.isChecked()) {
                        userInfo.put("email", m_edtEmailCP.getText().toString());
                    }
                    if (m_chkPass.isChecked()) {
                        userInfo.put("password", m_edtNewPass.getText().toString());
                    }

                    // Gửi yêu cầu thay đổi thông tin tới server
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfo.toString());
                    Request request = new Request.Builder()
                            .url(Global._URL + "/userupdate")
                            .addHeader("token", Global._token)
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            String errStr = "Thay đổi thất bại.\n" + e.getMessage();
                            Log.d("TIN4403","onFailure\n" + errStr);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), errStr, Toast.LENGTH_SHORT).show();
                                }
                            });
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            // Kiểm tra xem phản hồi có thành công không
                            if (response.isSuccessful()) {
                                // Hiển thị thông báo thành công
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        m_edtNameCP.setText("");
                                        m_edtEmailCP.setText("");
                                        m_edtOldPass.setText("");
                                        m_edtNewPass.setText("");
                                        m_edtReNewPass.setText("");
                                        Toast.makeText(getApplicationContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Xử lý khi có lỗi từ server
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    void enableButtonChange(){
        if (m_btnSubmitCP.isEnabled()) return;
        m_btnSubmitCP.setEnabled(true);
    }
    void disableButtonChange(){
        if (!m_btnSubmitCP.isEnabled()) return;
        if (m_chkName.isChecked()) return;
        if (m_chkEmail.isChecked()) return;
        if (m_chkPass.isChecked()) return;
        m_btnSubmitCP.setEnabled(false);
    }
}