package com.example.funtest.interfaces;

/**
 * @author hws
 * @class describe
 * @time 2019-12-21 15:32
 */
public interface OnDeleteDeviceListener extends OnFunListener {
    void onDeleteDeviceSuccess();
    void onDeleteDeviceFailed(Integer errCode);
}
