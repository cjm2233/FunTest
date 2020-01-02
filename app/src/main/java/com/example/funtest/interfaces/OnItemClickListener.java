package com.example.funtest.interfaces;

import android.view.View;

/**
 * @author hws
 * @class describe
 * @time 2019-12-20 15:24
 */
public interface OnItemClickListener {
    void onItemClicked(View view, int position);
    void onItemLongClicked(View view, int position);
}
