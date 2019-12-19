package com.example.funtest.interfaces;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 14:01
 */
public interface OnFunRegisterListener extends OnFunListener{
    void onRequestSendCodeSuccess();
    void onRequestSendCodeFailed(final Integer errCode);
    void onRegisterNewUserSuccess();
    void onRegisterNewUserFailed(final Integer errCode);
}
