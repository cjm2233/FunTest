package com.example.funtest.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basic.G;
import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;
import com.example.funtest.interfaces.OnFunAddDeviceListener;
import com.example.funtest.myapplication.MyAppliation;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;
import com.lib.sdk.struct.SDBDeviceInfo;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 17:29
 */
public class AddDeviceActivity extends BaseActivity implements OnFunAddDeviceListener {
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);
    private String username;
    private String password;
    private Button getDevice;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        getDevice = findViewById(R.id.btn_getdevice);
        editText = findViewById(R.id.ed_sn);
    }

    @Override
    public void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
    }

    @Override
    public void initOnClick() {
        sdkutil.registerOnFunAddDeviceListener(this);
        getDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDBDeviceInfo info = new SDBDeviceInfo();
                G.SetValue(info.st_0_Devmac,editText.getText().toString().trim());
                G.SetValue(info.st_1_Devname,G.ToString(info.st_0_Devmac));
                G.SetValue(info.st_4_loginName,"admin");
                G.SetValue(info.st_5_loginPsw, "");
//                G.SetValue(info.st_5_loginPsw,"");
                Log.i("qqqqqq", info.toString());
                Log.i("wwwwww",editText.getText().toString());
                FunSDK.SysAddDevice(sdkutil.getHander(), G.ObjToBytes(info), "", "", 0);
            }
        });
    }

    @Override
    public void onAddDeviceSuccess() {
        Toast.makeText(MyAppliation.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
        FunSDK.SysGetDevList(sdkutil.getHander(), username, password, 0);
    }

    @Override
    public void onAddDeviceFailed(Integer errCode) {
        Toast.makeText(MyAppliation.getContext(),"添加失败"+errCode,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        sdkutil.removeOnFunAddDeviceListener(this);
        super.onDestroy();
    }
}
