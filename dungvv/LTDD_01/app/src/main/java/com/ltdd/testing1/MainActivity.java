package com.ltdd.testing1;

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

import com.ltdd.testing1.R;
import com.ltdd.testing1.UserActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static String _token;
    public static String _username;
    static String _URL = "http://192.168.56.1:5080";//"https://dev.husc.edu.vn/tin4403/api";
    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Đăng nhập
    TextView m_lblRegister;//Biến điều khiển Đăng ký mới
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUsername);
        m_edtPass = (EditText)findViewById(R.id.edtPassword);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        m_lblRegister = (TextView) findViewById(R.id.lblRegister);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());

        //Cài đặt sự kiện Click cho Button Register
        m_lblRegister.setOnClickListener(new CButtonRegister());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("K45","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
                ShowToast(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!");
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                //apiLogin(user,pass);
                okhttpApiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }//public void onClick(View v) {//Hàm sử lý sự kiện click button login
    }//public class CButtonLogin  implements View.OnClickListener {

    public class CButtonRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), com.ltdd.testing1.RegisterActivity.class);
            startActivity(i);
        }
    }//public class CButtonRegister implements View.OnClickListener {

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);

        boolean bOk = (user.equals("vvdung") && pass.equals("123456"));
        if (bOk){
            _username= "Võ Việt Dũng";
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        }
        else{
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                    String str = "Tài khoản hoặc mật khẩu không chính xác [" + user + "/" + pass + "]";
                    ShowToast(getApplicationContext(),str);
                }
            });
        }
    } //void apiLogin(String user, String pass) throws IOException {

    void okhttpApiLogin(String user, String pass) throws IOException{
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Log.d("K45",json);
        RequestBody body = new FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build();

        Request request = new Request.Builder()
                .url(_URL + "/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("K45",e.getMessage());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Lỗi api login",Toast.LENGTH_SHORT).show();
                    }
                });

                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("K45",json);
                JSONObject oResult = null;
                try {
                    oResult = new JSONObject(json);
                    Log.d("K45", oResult.toString());
                } catch (Throwable t) {
                    Log.e("K45", "Could not parse malformed JSON: \"" + json + "\"");
                }
                if (!response.isSuccessful() || oResult == null){
                    String errStr;
                    if (oResult != null) errStr = oResult.toString();
                    else errStr = "JSON trả về không đúng cấu trúc\n" + response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }//if (!response.isSuccessful()){

                String token = "";
                try {
                    //Gọi hàm dịch vụ Register
                    token = oResult.get("m").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                _token = token;
                _username = user;
                Log.d("TOKEN:",_token);
                Log.d("USER:",_username);
                try {
                    okhttpApiUserInfo(_token);
                }
                catch (IOException e){
                    e.printStackTrace();
                };

                Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);

            }
        });//client.newCall(request).enqueue(new Callback() {
    } //void okhttpApiLogin(String user, String pass) throws IOException{

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
    void okhttpApiUserInfo(String token) throws IOException {
        RequestBody body = RequestBody.create("", null);
        Request request = new Request.Builder()
                .url(MainActivity._URL + "/userinfo")
                .post(body)
                .addHeader("token",token)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response res = null;
        String json = "";
        try {
            res = client.newCall(request).execute();
            json = res.body().string();
            Log.d("RES",res.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Log.d("JSON",json);
        JSONObject oResult = null;
        try {
            oResult = new JSONObject(json);
            Log.d("K45", oResult.toString());
        } catch (Throwable t) {
            Log.e("K45", "Could not parse malformed JSON: \"" + json + "\"");
            return;
        }
        if (oResult == null){
            String errStr = "JSON trả về không đúng cấu trúc" + json;
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }//if (!response.isSuccessful()){

        JSONObject oUser = null;
        try {
            oUser = oResult.getJSONObject("m");
            Log.d("K45", oUser.toString());
            String name = oUser.get("fullname").toString();
            if (name.length() > 0) UserActivity._fullname = name;
            String email = oUser.get("email").toString();
            if (email.length() > 0) UserActivity._email = email;
        } catch (JSONException e) {
            Log.e("K45", "Could not parse malformed JSON: \"" + json + "\"");
            return;
        }

    }//void okhttpApiUserInfo(String token) throws IOException {
    ///////////// CÁCH SỬ DỤNG OKHTTP GET/POST ///////////////
    //Hàm mẫu sử dụng phương thức GET - chỉ tham khảo
    void doGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtString.setText(myResponse);
                        Log.d("K45",myResponse);
                    }
                });
            }
        });
    }

    //Hàm mẫu sử dụng phương thức POST - chỉ tham khảo
    void doPost(String url,String key, String value) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(key,value)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("K45",response.body().string());
            }
        });
    }

}//public class MainActivity extends AppCompatActivity {