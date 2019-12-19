package com.example.funtest.util;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.example.funtest.interfaces.OnFunListener;
import com.example.funtest.interfaces.OnFunRegisterListener;
import com.lib.EUIMSG;
import com.lib.FunSDK;
import com.lib.IFunSDKResult;
import com.lib.MsgContent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 13:17
 */
public class Sdkutil implements IFunSDKResult{
    private int mFunUserHandler = -1;
    private List<OnFunListener> mListeners = new ArrayList<OnFunListener>();
    private static final Sdkutil sdkutil = new Sdkutil();

    public static Sdkutil getSdkutil(Context context) {
        return sdkutil;
    }

    public int getHander() {
        return mFunUserHandler;
    }

    public void registerOnFunRegisterListener(OnFunRegisterListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    private Sdkutil() {
        mFunUserHandler = FunSDK.RegUser(this);
        Log.i("mmmmmmm","mFunUserHandler"+mFunUserHandler);
    }

    @Override
    public int OnFunSDKResult(Message message, MsgContent msgContent) {
        Log.i("aaaaaa","what:"+message.what);
        switch (message.what) {
            case EUIMSG.SYS_GET_PHONE_CHECK_CODE: {
                if (message.arg1 == 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRequestSendCodeSuccess();
                        }
                    }
                }else{
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRegisterNewUserFailed(message.arg1);
                        }
                    }
                }
            }
            case EUIMSG.SYS_REGISER_USER_XM:{
                if (message.arg1 == 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRegisterNewUserSuccess();
                        }
                    }
                }else{
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRequestSendCodeFailed(message.arg1);
                        }
                    }
                }
            }
            default:
                break;

        }
        return 0;
    }
}
