package com.example.funtest.util;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.basic.G;
import com.example.funtest.bean.DeviceBean;
import com.example.funtest.interfaces.OnDeleteDeviceListener;
import com.example.funtest.interfaces.OnFunAddDeviceListener;
import com.example.funtest.interfaces.OnFunListener;
import com.example.funtest.interfaces.OnFunLoginListener;
import com.example.funtest.interfaces.OnFunRegisterListener;
import com.lib.EUIMSG;
import com.lib.FunSDK;
import com.lib.IFunSDKResult;
import com.lib.MsgContent;
import com.lib.sdk.struct.SDBDeviceInfo;
import com.lib.sdk.struct.SDK_ChannelNameConfigAll;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 13:17
 */
public class Sdkutil implements IFunSDKResult {
    private int mFunUserHandler = -1;
    private List<OnFunListener> mListeners = new ArrayList<OnFunListener>();
    private List<DeviceBean> list = new ArrayList<DeviceBean>();
    private List<DeviceConfig> configList = new ArrayList<DeviceConfig>();
    private static final Sdkutil sdkutil = new Sdkutil();
    private DeviceBean deviceBean = null;
    private int mPlayStartPos = 0;
    private int mPlayEndPos = 0;

    public static Sdkutil getSdkutil(Context context) {
        return sdkutil;
    }

    public int getHander() {
        return mFunUserHandler;
    }

    private class DeviceConfig {
        DeviceBean deviceBean;
        String configName;
        int channelNo = -1;

        DeviceConfig(DeviceBean db, String cfg, int channel) {
            deviceBean = db;
            configName = cfg;
            channelNo = channel;
        }
    }

    //注册监听
    public void registerOnFunRegisterListener(OnFunRegisterListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeOnFunRegisterListener(OnFunRegisterListener l) {
        if (mListeners.contains(l)) {
            mListeners.remove(l);
        }
    }

    //登录监听
    public void registerOnFunLoginListener(OnFunLoginListener l) {
        if (!mListeners.contains(l)) {
            mListeners.add(l);
        }
    }

    public void removeOnFunLoginListener(OnFunLoginListener l) {
        if (mListeners.contains(l)) {
            mListeners.remove(l);
        }
    }

    //添加设备监听
    public void registerOnFunAddDeviceListener(OnFunAddDeviceListener l) {
        if (!mListeners.contains(l)) {
            mListeners.add(l);
        }
    }

    public void removeOnFunAddDeviceListener(OnFunAddDeviceListener l) {
        if (mListeners.contains(l)) {
            mListeners.remove(l);
        }
    }

    //删除设备监听
    public void registerOnFunDeleteDeviceListener(OnDeleteDeviceListener l) {
        if (!mListeners.contains(l)) {
            mListeners.add(l);
        }
    }

    public void removeOnFunDeleteDeviceListener(OnDeleteDeviceListener l) {
        if (mListeners.contains(l)) {
            mListeners.remove(l);
        }
    }

    private Sdkutil() {
        mFunUserHandler = FunSDK.RegUser(this);
        Log.i("mmmmmmm", "mFunUserHandler" + mFunUserHandler);
    }

    @Override
    public int OnFunSDKResult(Message message, MsgContent msgContent) {
        Log.i("aaaaaa", "what:" + message.what);
        switch (message.what) {
            case EUIMSG.SYS_GET_PHONE_CHECK_CODE: {
                Log.i("bbbbbbb", "SYS_GET_PHONE_CHECK_CODE");
                if (message.arg1 == 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRequestSendCodeSuccess();
                        }
                    }
                } else {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRequestSendCodeFailed(message.arg1);
                        }
                    }
                }
            }
            break;
            case EUIMSG.SYS_REGISER_USER_XM: {
                Log.i("cccccc", "SYS_REGISER_USER_XM" + message.arg1);

                if (message.arg1 == 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRegisterNewUserSuccess();
                        }
                    }
                } else {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunRegisterListener) {
                            ((OnFunRegisterListener) listener).onRegisterNewUserFailed(message.arg1);
                        }
                    }
                }
            }
            break;
            case EUIMSG.SYS_GET_DEV_INFO_BY_USER:
                Log.i("ddddddd", "SYS_GET_DEV_INFO_BY_USER");
                if (message.arg1 >= 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunLoginListener) {
                            ((OnFunLoginListener) listener).onLoginSuccess();
                        }
                    }
                    updateDeviceList(msgContent.pData);
                } else {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunLoginListener) {
                            ((OnFunLoginListener) listener).onLoginFailed(message.arg1);
                        }
                    }
                }
                break;
            case EUIMSG.SYS_ADD_DEVICE: {
                if (message.arg1 >= 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunAddDeviceListener) {
                            ((OnFunAddDeviceListener) listener).onAddDeviceSuccess();
                        }
                    }
                } else {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnFunAddDeviceListener) {
                            ((OnFunAddDeviceListener) listener).onAddDeviceFailed(message.arg1);
                        }
                    }
                }
                break;
            }
            case EUIMSG.DEV_GET_CHN_NAME: {
                if (message.arg1 > 0) {
                    if (msgContent.pData != null && msgContent.pData.length > 0) {
                        SDK_ChannelNameConfigAll Channel = new SDK_ChannelNameConfigAll();
                        G.BytesToObj(Channel, msgContent.pData);
                        Channel.nChnCount = message.arg1;
                        deviceBean.setChannel(Channel);
                    }
                }
                break;
            }
            case EUIMSG.SYS_DELETE_DEV: {
                if (message.arg1 >= 0) {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnDeleteDeviceListener) {
                            ((OnDeleteDeviceListener) listener).onDeleteDeviceSuccess();
                        }
                    }
                } else {
                    for (OnFunListener listener : mListeners) {
                        if (listener instanceof OnDeleteDeviceListener) {
                            ((OnDeleteDeviceListener) listener).onDeleteDeviceFailed(message.arg1);
                        }
                    }
                }
                break;
            }
            case EUIMSG.START_PLAY: {
                if (message.arg1 >= FunError.EE_OK) {
                    // 播放成功
                    Log.i("33333333", "成功");
                }
                break;
            }
            case EUIMSG.STOP_PLAY:
                break;
            case EUIMSG.PAUSE_PLAY:
                break;
            case EUIMSG.ON_PLAY_INFO: {
                break;
            }
            case EUIMSG.ON_PLAY_END: {
                break;
            }
            case EUIMSG.ON_PLAY_BUFFER_BEGIN: {
                break;
            }
            case EUIMSG.ON_PLAY_BUFFER_END: {
                break;
            }
            case EUIMSG.DEV_GET_JSON:{
                Log.i("configjson:","DEV_GET_JSON");
                if (message.arg1>=0){
                    String json =G.ToString(msgContent.pData);
                    Log.i("configjson:",json);
                }else{
                    Log.i("configjson:","失败"+message.arg1);
                }
                break;
            }
            default:
                break;

        }
        return 0;
    }

    private void updateDeviceList(byte[] pData) {
        list.clear();
        try {
            SDBDeviceInfo info = new SDBDeviceInfo();
            int nItemLen = G.Sizeof(info);
            int nCount = pData.length / nItemLen;
            SDBDeviceInfo infos[] = new SDBDeviceInfo[nCount];
            for (int i = 0; i < nCount; ++i) {
                infos[i] = new SDBDeviceInfo();
            }
            G.BytesToObj(infos, pData);
            for (int i = 0; i < nCount; ++i) {
                Log.i("xuliehao", G.ToString(infos[i].st_4_loginName));
                DeviceBean deviceBean = new DeviceBean(G.ToString(infos[i].st_0_Devmac),
                        G.ToString(infos[i].st_1_Devname),
                        G.ToString(infos[i].st_2_Devip),
                        G.ToString(infos[i].st_4_loginName),
                        G.ToString(infos[i].st_5_loginPsw));
                list.add(deviceBean);
                Log.i("666666666",deviceBean.getDevmac()+"    "+deviceBean.getLoginName()+"   "+deviceBean.getLoginPsw());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getList() {
        return list;
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public DeviceBean findDevice(String devsn) {
        for (DeviceBean funDev : list) {
            if (devsn.equals(funDev.getDevmac())) {
                return funDev;
            }
        }
        return null;
    }


}
