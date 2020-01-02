package com.example.funtest.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.funtest.Base.BaseActivity;
import com.example.funtest.R;
import com.example.funtest.bean.DeviceBean;
import com.example.funtest.modles.FunStreamType;
import com.example.funtest.util.FunVideoView;
import com.example.funtest.util.Sdkutil;
import com.lib.FunSDK;
import com.lib.sdk.bean.JsonConfig;

/**
 * @author hws
 * @class describe
 * @time 2019-12-21 9:31
 */
public class MediaActivity extends BaseActivity {
    private String sn;
    private FunVideoView mFunVideoView = null;
    private Sdkutil sdkutil = Sdkutil.getSdkutil(this);
    private DeviceBean deviceBean = null;
    public static final String CONFIG_NAME = JsonConfig.CAMERA_PARAM;
    private Button getConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        initView();
        initData();
        initOnClick();
    }

    @Override
    public void initView() {
        mFunVideoView = findViewById(R.id.video);
        getConfig = findViewById(R.id.btn_getconfig);
    }

    @Override
    public void initData() {
        sn = getIntent().getStringExtra("path");
        startMedia();
//        startMedia2();
    }

    @Override
    public void initOnClick() {
        getConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGetConfig();
                Log.i("getconfig","onclick");
            }
        });
    }

    private void stopMedia() {
        if (null != mFunVideoView) {
            mFunVideoView.stopPlayback();
            mFunVideoView.stopRecordVideo();
        }
    }

    @Override
    protected void onDestroy() {
        stopMedia();
        super.onDestroy();
    }

    private void startMedia() {
        deviceBean = sdkutil.findDevice(sn);
        mFunVideoView.setStreamType(FunStreamType.STREAM_MAIN);
        mFunVideoView.setRealDevice(sn, deviceBean.getCurrChannel());
    }
//    private void startMedia2(){
//        deviceBean = sdkutil.findDevice(path);
//        String path2 = "real://" + path;
//        FunSDK.MediaRealPlay(sdkutil.getHander(),path2,deviceBean.getCurrChannel(),0,view,0);
//    }
    private void setGetConfig(){
        FunSDK.DevGetConfigByJson(sdkutil.getHander(),sn,JsonConfig.SYSTEM_INFO,4096,0,10000,0);
    }
    private void setConfig(){

    }
}
