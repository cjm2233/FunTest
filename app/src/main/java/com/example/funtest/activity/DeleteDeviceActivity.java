package com.example.funtest.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;
import com.example.funtest.interfaces.OnDeleteDeviceListener;
import com.example.funtest.myapplication.MyAppliation;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;
import com.lib.sdk.struct.SDBDeviceInfo;

/**
 * @author hws
 * @class describe
 * @time 2019-12-21 15:22
 */
public class DeleteDeviceActivity extends BaseActivity implements OnDeleteDeviceListener {
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);
    private String username;
    private String password;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        editText = findViewById(R.id.ed_delete_sn);
        button = findViewById(R.id.btn_delete_device);
    }

    @Override
    public void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
    }

    @Override
    public void initOnClick() {
        sdkutil.registerOnFunDeleteDeviceListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDevice();
            }
        });
    }
    private void deleteDevice(){
        SDBDeviceInfo info = new SDBDeviceInfo();
        info.st_0_Devmac = editText.getText().toString().getBytes();
        String sn = editText.getText().toString();
//        FunSDK.SysDeleteDev(sdkutil.getHander(),sn,"13758233967","12345678",0);
        FunSDK.SysDeleteDev(sdkutil.getHander(),sn,username,password,0);
    }

    @Override
    public void onDeleteDeviceSuccess() {
        Toast.makeText(MyAppliation.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
        FunSDK.SysGetDevList(sdkutil.getHander(), username, password, 0);
    }

    @Override
    public void onDeleteDeviceFailed(Integer errCode) {
        Toast.makeText(MyAppliation.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        sdkutil.removeOnFunDeleteDeviceListener(this);
        super.onDestroy();
    }
}
