package com.example.baitaplonandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText  edUsername;
    private EditText  edPassword;
    private Button btnSignUp;
    private Button btnLogin;

    //khai báo tên file xml
    private final String SHARED_PREF_NAME ="shared_pref";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sigup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this ,SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String strUsername = preferences.getString("Username" , null);
                String strPassword = preferences.getString("Password" , null);

                //lấy ra chuỗi từ edusername
                String username_from_ed = edUsername.getText().toString();
                String password_from_ed = edPassword.getText().toString();

                if(strUsername !=null && username_from_ed !=null && strUsername.equalsIgnoreCase(username_from_ed)){
                    if(strPassword !=null && password_from_ed !=null && strPassword.equalsIgnoreCase(password_from_ed)){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this ,MainActivity.class);
                                startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Login faile",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Login faile", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
