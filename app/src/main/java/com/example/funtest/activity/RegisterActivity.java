package com.example.funtest.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;
import com.example.funtest.interfaces.OnFunRegisterListener;
import com.example.funtest.myapplication.MyAppliation;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 10:31
 */
public class RegisterActivity extends BaseActivity implements OnFunRegisterListener {
    //用户名
    private EditText username;
    //密码
    private EditText password1;
    //第二遍验证密码
    private EditText password2;
    //电话号码
    private EditText phone;
    //验证码
    private EditText checkcode;
    //获取验证码
    private Button getcode;
    //注册
    private Button register;
    private String usernameStr;
    private String password1Str;
    private String password2Str;
    private String checkcodeStr;
    private String phoneStr;
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        username = findViewById(R.id.ed_username);
        password1 = findViewById(R.id.ed_password1);
        password2 = findViewById(R.id.ed_password2);
        phone = findViewById(R.id.ed_phone);
        checkcode = findViewById(R.id.ed_code);
        getcode = findViewById(R.id.btn_Code);
        register = findViewById(R.id.btn_register);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOnClick() {
        sdkutil.registerOnFunRegisterListener(this);
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGetcode();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    private void setGetcode(){
        usernameStr = username.getText().toString();
        password1Str = password1.getText().toString();
        password2Str = password2.getText().toString();
        checkcodeStr = checkcode.getText().toString();
        phoneStr = phone.getText().toString();
        FunSDK.SysSendPhoneMsg(sdkutil.getHander(),usernameStr,phoneStr,0);
    }
    private void register(){
        usernameStr = username.getText().toString();
        password1Str = password1.getText().toString();
        password2Str = password2.getText().toString();
        checkcodeStr = checkcode.getText().toString();
        phoneStr = phone.getText().toString();
        if (password1 == password2) {
            FunSDK.SysRegUserToXM(sdkutil.getHander(), usernameStr, password1Str, checkcodeStr, phoneStr, 0);
        }else{
            Toast.makeText(MyAppliation.getContext(),"两次密码不同",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestSendCodeSuccess() {
        Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSendCodeFailed(Integer errCode) {
        Toast.makeText(RegisterActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();
        Log.i("yanzhenmafasongshibai",errCode.toString());
    }

    @Override
    public void onRegisterNewUserSuccess() {
        Toast.makeText(MyAppliation.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterNewUserFailed(Integer errCode) {
        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
        Log.i("zhuceshibai",errCode.toString());
    }
}
