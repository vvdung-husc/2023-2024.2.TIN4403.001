package com.example.ltdd_2;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;

import android.os.Handler;

public class Global {
    public static Handler _Handler;
    public static String _token;
    public static String _URL ="https://dev.husc.edu.vn/tin4403/api";//"http://192.168.1.13:5080";//
    public static API _HTTP = new API(_URL);
    public static String _userPassword;

    public static void ShowToast(Context ctx, String msg){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast toast = Toast.makeText(ctx,msg,Toast.LENGTH_LONG);
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
    public static void uiShowToast(Context ctx, String msg){//called not in UIThread
        _Handler.post(new Runnable() {
            @Override
            public void run() {
                ShowToast(ctx,msg);
            }
        });
    }
}