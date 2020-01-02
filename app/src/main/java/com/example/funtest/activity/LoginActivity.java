package com.example.funtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.MainActivity;
import com.example.funtest.R;
import com.example.funtest.interfaces.OnFunLoginListener;
import com.example.funtest.myapplication.MyAppliation;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 9:31
 */
public class LoginActivity extends BaseActivity implements OnFunLoginListener {
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
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        initOnClick();

    }

    private void goRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
        sharedPreferences= getSharedPreferences("save", Activity.MODE_PRIVATE);
        user.setText("13758233967");
        password.setText("12345678");
    }

    @Override
    public void initOnClick() {
        sdkutil.registerOnFunLoginListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });
    }

    public void setLogin() {
        userStr = user.getText().toString();
        passwordStr = password.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",userStr);
        editor.putString("password",passwordStr);
        editor.commit();
//        FunSDK.SysGetDevList(sdkutil.getHander(), "13758233967", "12345678", 0);
        FunSDK.SysGetDevList(sdkutil.getHander(), userStr, passwordStr, 0);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(MyAppliation.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(Integer errCode) {
        Toast.makeText(MyAppliation.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
        Log.i("Loginerror", errCode.toString());
    }

    @Override
    public void onLogout() {

    }

    @Override
    protected void onDestroy() {
        sdkutil.removeOnFunLoginListener(this);
        super.onDestroy();
    }
}
