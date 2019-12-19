package com.example.funtest.Base;

import android.app.Activity;

/**
 * @author hws
 * @class describe
 * @time 2019-12-19 11:02
 */
public abstract class BaseActivity extends Activity {
    public abstract void initView();
    public abstract void initData();
    public abstract void initOnClick();
}
