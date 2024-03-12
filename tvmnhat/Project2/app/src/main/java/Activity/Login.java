package Activity;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.project2.MainActivity;
import com.example.project2.R;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginbutton;
    TextView signup_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password_toggle);
        loginbutton = findViewById(R.id.loginButton);
        signup_btn = findViewById(R.id.btnSignup);

        //admin and admin
        AddData();
    }
    public void AddData(){
        loginbutton.setOnClickListener(view -> {
            //incorrect
            if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                Toast.makeText(Login.this, "LOGIN FAILED!!!", Toast.LENGTH_SHORT).show();
            }
            //correct
            else{
                Toast.makeText(Login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(Login.this, MainActivity.class));
//            finish();
        });

        signup_btn.setOnClickListener(view ->{
//            startActivity(new Intent(Login.this, Register.class));
//            finish();
        });
    }
}