package com.example.funtest.bean;

import com.lib.sdk.struct.SDK_ChannelNameConfigAll;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 13:19
 */
public class DeviceBean {
    private String Devmac;
    private String devname;
    private String devip;
    private String loginName;
    private String loginPsw;
    private SDK_ChannelNameConfigAll channel;
    public int CurrChannel = 0;

    public DeviceBean(String devmac, String devname, String devip, String loginName, String loginPsw) {
        this.Devmac = devmac;
        this.devname = devname;
        this.devip = devip;
        this.loginName = loginName;
        this.loginPsw = loginPsw;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getDevip() {
        return devip;
    }

    public void setDevip(String devip) {
        this.devip = devip;
    }

    public String getDevmac() {
        return Devmac;
    }

    public void setDevmac(String devmac) {
        Devmac = devmac;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPsw() {
        return loginPsw;
    }

    public void setLoginPsw(String loginPsw) {
        this.loginPsw = loginPsw;
    }

    public int getId() {
        return this.hashCode();
    }

    public SDK_ChannelNameConfigAll getChannel() {
        return channel;
    }

    public void setChannel(SDK_ChannelNameConfigAll channel) {
        this.channel = channel;
    }

    public int getCurrChannel() {
        return CurrChannel;
    }

    public void setCurrChannel(int currChannel) {
        CurrChannel = currChannel;
    }
}
