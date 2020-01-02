package com.example.funtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.activity.DeviceActivity;
import com.example.funtest.activity.MediaActivity;
import com.example.funtest.adapter.MyAdapter;
import com.example.funtest.bean.DeviceBean;
import com.example.funtest.interfaces.OnFunLoginListener;
import com.example.funtest.interfaces.OnItemClickListener;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnFunLoginListener {
    private RecyclerView recyclerView;
    private Button button;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter myAdapter;
    private List<DeviceBean> list = new ArrayList<DeviceBean>();
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);
    Boolean isPause = false;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.rec_main);
        button = findViewById(R.id.btn_device);
    }

    @Override
    public void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        list = sdkutil.getList();
        Log.i("22222", "" + sdkutil.getList().size());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(list);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void initOnClick() {
        sdkutil.registerOnFunLoginListener(this);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(MainActivity.this, MediaActivity.class);
                intent.putExtra("path", list.get(position).getDevmac());
                startActivity(intent);
            }

            @Override
            public void onItemLongClicked(View view, int position) {
                FunSDK.SysDeleteDev(sdkutil.getHander(),list.get(position).getDevmac(),"","",0);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            isPause = false;
//            FunSDK.SysGetDevList(sdkutil.getHander(), "13758233967", "12345678", 0);
            FunSDK.SysGetDevList(sdkutil.getHander(), username, password, 0);
            list = sdkutil.getList();
            Log.i("11111", "" + sdkutil.getList().size());
            myAdapter.setList(list);
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed(Integer errCode) {

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
