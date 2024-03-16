package com.example.ltdd_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity {

    TextView m_lblWelcome,m_lblUser,m_lblName,m_lblEmail;
    EditText m_edtName,m_edtEmail,m_edtPassOld,m_edtPassNew1,m_edtPassNew2;
    CheckBox m_chkName,m_chkEmail,m_chkPass;
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

        m_chkName = ( CheckBox ) findViewById( R.id.chkName );
        m_chkEmail= ( CheckBox ) findViewById( R.id.chkEmail);
        m_chkPass = ( CheckBox ) findViewById( R.id.chkPass );

        m_edtName    = ( EditText ) findViewById( R.id.edtName2 );
        m_edtEmail   = ( EditText ) findViewById( R.id.edtEmail2 );
        m_edtPassOld = ( EditText ) findViewById( R.id.edtPassOld );
        m_edtPassNew1= ( EditText ) findViewById( R.id.edtPassNew1);
        m_edtPassNew2= ( EditText ) findViewById( R.id.edtPassNew2);

        m_btnLogout = (Button) findViewById(R.id.btnLogout);
        m_btnChange = (Button) findViewById(R.id.btnChange);

        m_lblWelcome.setText("*** Chào mừng ***");

        m_lblUser.setText ("Tài khoản\t\t:\t" + User._username);
        m_lblName.setText ("Họ và tên\t\t:\t" + User._fullname);
        m_lblEmail.setText("Thư điện tử\t:\t" + User._email);


        Log.d("NAME",User._fullname);
        Log.d("EMAIL",User._email);

        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), com.example.ltdd_01.MainActivity.class);
                startActivity(intent);
                finish();
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
                    enableButtonChange();
                    m_edtPassOld.setEnabled(true);
                    m_edtPassNew1.setEnabled(true);
                    m_edtPassNew2.setEnabled(true);
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

    }//protected void onCreate(Bundle savedInstanceState) {


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