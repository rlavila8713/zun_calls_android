package com.xkoders.zuncallandroid.interfaces;

import android.view.View;

import com.xkoders.zuncallandroid.models.Call;


public interface OnItemClickListener {
    void onItemClick(Call item);
    void onItemClick(View view, int position);
}
