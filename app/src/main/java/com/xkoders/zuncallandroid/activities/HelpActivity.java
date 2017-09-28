package com.xkoders.zuncallandroid.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.adapters.ViewPagerAdapter;
import com.xkoders.zuncallandroid.components.CirclePageIndicator;

public class HelpActivity extends AppCompatActivity {
    private ViewPager mPager;
    private TextView mStart;
    private ViewPagerAdapter mAdapter;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_slide_screen);
        getWidgetsreference();

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        if (mIndicator != null) {
            mIndicator.setViewPager(mPager);
        }

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getWidgetsreference() {
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mPager = (ViewPager) findViewById(R.id.help_pager);
        mStart = (TextView) findViewById(R.id.tv_help_start);
    }
}
