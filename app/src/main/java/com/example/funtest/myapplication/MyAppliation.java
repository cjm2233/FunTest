package com.example.funtest.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.basic.G;
import com.lib.EFUN_ATTR;
import com.lib.FunSDK;
import com.lib.sdk.struct.SInitParam;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 9:47
 */
public class MyAppliation extends Application {
    private static final String APP_UUID = "e0534f3240274897821a126be19b6d46";
    private static final String APP_KEY = "0621ef206a1d4cafbe0c5545c3882ea8";
    private static final String APP_SECRET = "90f8bc17be2a425db6068c749dee4f5d";
    private static final int APP_MOVECARD = 2;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context= getApplicationContext();
        initSdk();
    }
    private void initSdk(){
        SInitParam param = new SInitParam();
        param.st_0_nAppType = SInitParam.LOGIN_TYPE_MOBILE;
//        String country = Locale.getDefault().getCountry();
//        String language = Locale.getDefault().getLanguage();
//        if (language.equalsIgnoreCase("zh")
//                && (country.equalsIgnoreCase("TW")
//                || country.equalsIgnoreCase("HK"))) {
//            G.SetValue(param.st_2_language, country.toLowerCase());
//        }else {
//            G.SetValue(param.st_2_language, Locale.getDefault().getLanguage());
//        }
        FunSDK.Init(0, G.ObjToBytes(param));
        int a = FunSDK.SysInitNet("223.4.33.127;54.84.132.236;112.124.0.188", 15010);
        FunSDK.XMCloundPlatformInit(APP_UUID, APP_KEY, APP_SECRET, APP_MOVECARD);
        Log.i("bbbbbb","Net:"+a);
        FunSDK.MyInitNetSDK();

        // 设置临时文件保存路径
        FunSDK.SetFunStrAttr(EFUN_ATTR.APP_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        // 设置设备更新文件保存路径
        FunSDK.SetFunStrAttr(EFUN_ATTR.UPDATE_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        // 设置SDK相关配置文件保存路径
        FunSDK.SetFunStrAttr(EFUN_ATTR.CONFIG_PATH,Environment.getExternalStorageDirectory().getAbsolutePath());

        FunSDK.LogInit(0, "", 0, "", 1);
    }

    public static Context getContext() {
        return context;
    }
}
