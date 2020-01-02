package com.example.funtest.interfaces;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 8:54
 */
public interface OnFunLoginListener extends OnFunListener {
    void onLoginSuccess();
    void onLoginFailed(Integer errCode);
    void onLogout();
}
