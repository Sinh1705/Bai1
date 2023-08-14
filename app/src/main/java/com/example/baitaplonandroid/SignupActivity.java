package com.example.baitaplonandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class SignupActivity extends AppCompatActivity {

    private EditText edUsername;
    private EditText  edPassword;
    private EditText edConfirmPassword;
    private Button btnCreateUser;

    //khai báo tên file xml
    private final String SHARED_PREF_NAME ="shared_pref";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword = findViewById(R.id.ed_confirm_pwd);
        btnCreateUser = findViewById(R.id.btn_create_user);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy ra chuỗi và gán vào biến
                String strPassword = edPassword.getText().toString();
                String strConfirmPassword = edConfirmPassword.getText().toString();
                String strUsername = edUsername.getText().toString();

                if(strPassword !=null && strConfirmPassword !=null && strPassword.equalsIgnoreCase(strConfirmPassword)){
                    //lay ra doi tuong sharepref, k cho phep chia se
                    SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);

                    //thuc hien luu thong tin dang ký
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Password" ,strPassword);
                    editor.putString("Username" ,strUsername);

                    //thực hiện commit và kết thúc việc ghi
                    editor.commit();

                    SignupActivity.this.finish();
                }
            }
        });

    }
}
