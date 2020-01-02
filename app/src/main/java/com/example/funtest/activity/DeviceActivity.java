package com.example.funtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 17:22
 */
public class DeviceActivity extends BaseActivity {
    private Button add1;
    private Button add2;
    private Button add3;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        add1 = findViewById(R.id.btn_add);
        add2 = findViewById(R.id.btn_add2);
        add3  =findViewById(R.id.btn_add3);
        delete = findViewById(R.id.btn_delete);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOnClick() {
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceActivity.this,AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceActivity.this,DeleteDeviceActivity.class);
                startActivity(intent);
            }
        });
    }
}
