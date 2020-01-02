package com.example.funtest.interfaces;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 16:14
 */
public interface OnFunAddDeviceListener extends OnFunListener {
    void onAddDeviceSuccess();
    void onAddDeviceFailed(Integer errCode);
}
