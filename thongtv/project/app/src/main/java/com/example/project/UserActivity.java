package com.example.project;




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

public class UserActivity extends AppCompatActivity {

    TextView m_lblWelcome,m_lblUser,m_lblName,m_lblEmail;
    EditText m_edtName,m_edtEmail,m_edtPassOld,m_edtPassNew1,m_edtPassNew2;
    CheckBox m_chkName,m_chkEmail,m_chkPass, m_chkOk;;
    Button m_btnLogout,m_btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_lblWelcome = (TextView)findViewById(R.id.lblWelcome);
        m_lblUser = (TextView)findViewById(R.id.lblUser2);
        m_lblName = (TextView)findViewById(R.id.lblName2);
        m_lblEmail= (TextView)findViewById(R.id.lblEmail2);



        m_edtName    = ( EditText ) findViewById( R.id.edtName2 );
        m_edtEmail   = ( EditText ) findViewById( R.id.edtEmail2 );
        m_edtPassOld = ( EditText ) findViewById( R.id.edtPassOld );
        m_edtPassNew1= ( EditText ) findViewById( R.id.edtPassNew1);
        m_edtPassNew2= ( EditText ) findViewById( R.id.edtPassNew2);

        m_chkName = ( CheckBox ) findViewById( R.id.chkName );
        m_chkEmail= ( CheckBox ) findViewById( R.id.chkEmail);
        m_chkPass = ( CheckBox ) findViewById( R.id.chkPass );
        m_chkOk = (CheckBox) findViewById(R.id.chkOk);

        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_btnChange = (Button) findViewById(R.id.btnChange);

        m_lblWelcome.setText("*** Chào mừng ***");

        m_lblUser.setText ("Tài khoản\t\t:\t" + User._username);
        m_lblName.setText ("Họ và tên\t\t:\t" + User._fullname);
        m_lblEmail.setText("Thư điện tử\t:\t" + User._email);

        getUserInfoFromServer();


        Log.d("NAME",User._fullname);
        Log.d("EMAIL",User._email);

        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), com.example.project.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        m_btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
            }
        });



        m_chkName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (m_chkName.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    Log.d("CHECKED","m_chkName");
                    enableButtonChange();
                    m_edtName.setEnabled(true);
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    Log.d("UNCHECK","m_chkName");
                    disableButtonChange();
                    m_edtName.setEnabled(false);
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
                    enableButtonChange();
                    m_edtEmail.setEnabled(true);
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    disableButtonChange();
                    m_edtEmail.setEnabled(false);
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

                    m_edtPassOld.setEnabled(true);
                    m_edtPassNew1.setEnabled(true);
                    m_edtPassNew2.setEnabled(true);
                    acceptChange();
                }
                else
                {
                    //Perform action when you touch on checkbox and it change to unselected state
                    disableButtonChange();
                    m_edtPassOld.setEnabled(false);
                    m_edtPassNew1.setEnabled(false);
                    m_edtPassNew2.setEnabled(false);

                }
            }
        });
        m_chkOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptChange();
            }
        });

    }//protected void onCreate(Bundle savedInstanceState) {
    private void getUserInfoFromServer() {
        OkHttpClient client = new OkHttpClient();

        // Tạo JSON object chứa token
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", Global._token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo request body từ JSON object
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject), API.JSON);

        // Tạo request
        Request request = new Request.Builder()
                .url(Global._URL + "/userinfo")
                .post(requestBody)
                .build();

        // Thực hiện request bất đồng bộ
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Xử lý khi có lỗi
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    // Xử lý response từ máy chủ và cập nhật giao diện
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    // Xử lý khi có lỗi từ máy chủ
                }
            }
        });
    }


    private void acceptChange() {
        m_btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem đã nhập đầy đủ thông tin chưa
                if (m_chkPass.isChecked() && (m_edtPassOld.getText().toString().isEmpty() || m_edtPassNew1.getText().toString().isEmpty() || m_edtPassNew2.getText().toString().isEmpty())) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem mật khẩu cũ có khớp không
                String oldPassword = m_edtPassOld.getText().toString();
                if (m_chkPass.isChecked() && !oldPassword.equals(Global._userPassword)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo JSON object chứa thông tin thay đổi
                JSONObject userInfo = new JSONObject();
                try {
                    if (m_chkName.isChecked()) {
                        userInfo.put("fullname", m_edtName.getText().toString());
                    }
                    if (m_chkEmail.isChecked()) {
                        userInfo.put("email", m_edtEmail.getText().toString());
                    }
                    if (m_chkPass.isChecked()) {
                        userInfo.put("password", m_edtPassNew1.getText().toString());
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
        if (m_btnChange.isEnabled()) return;
        m_btnChange.setEnabled(true);
    }
    void disableButtonChange(){
        if (!m_btnChange.isEnabled()) return;
        if (m_chkName.isChecked()) return;
        if (m_chkEmail.isChecked()) return;
        if (m_chkPass.isChecked()) return;
        m_btnChange.setEnabled(false);
    }


}//public class UserActivity extends AppCompatActivity {