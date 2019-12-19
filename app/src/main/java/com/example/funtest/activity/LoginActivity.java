package com.example.funtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 9:31
 */
public class LoginActivity extends BaseActivity {
    //账号
    private EditText user;
    //密码
    private EditText password;
    //登录
    private Button login;
    //注册
    private Button register;
    private String userStr;
    private String passwordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initOnClick();

    }
    private void goRegister(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void initView() {
        user = findViewById(R.id.ed_user);
        password = findViewById(R.id.ed_password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_go_register);
    }

    @Override
    public void initData() {
        userStr = user.getText().toString();
        passwordStr = password.getText().toString();
    }

    @Override
    public void initOnClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });
    }
}
