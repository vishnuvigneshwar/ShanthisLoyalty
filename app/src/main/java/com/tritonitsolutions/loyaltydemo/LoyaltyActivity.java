package com.tritonitsolutions.loyaltydemo;



import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;
import android.widget.TextView;

import com.tritonitsolutions.layaltydemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by TritonDev on 15/9/2015.
 */
public class LoyaltyActivity extends TabActivity{
    TabHost host;
    TextView tv;
    Toolbar toolbar;
    ViewPager pager=null;
    int count=0;
    Timer timer;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyalty_layout);
        ViewPagerAdapter adapter=new ViewPagerAdapter(this);
        pager=(ViewPager)findViewById(R.id.reviewpager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count <= 3) {
                            pager.setCurrentItem(count);
                            count++;
                        } else {
                            count = 0;
                            pager.setCurrentItem(count);
                        }

                    }
                });
            }
        }, 500, 3000);

        host=getTabHost();

        TabHost.TabSpec loyaltyHistory=host.newTabSpec("Loyalty History");
        loyaltyHistory.setIndicator("Loyalty History");
        Intent loyaltyHistoryIntent=new Intent(this,LoyaltyHistoryActivity.class);
        loyaltyHistory.setContent(loyaltyHistoryIntent);

        TabHost.TabSpec customerHistory=host.newTabSpec("Customer History");
        customerHistory.setIndicator("Customer History");
        Intent customerHistoryIntent=new Intent(this,CustomerHistoryActivity.class);
        customerHistory.setContent(customerHistoryIntent);

        host.addTab(loyaltyHistory);
        host.addTab(customerHistory);

        for(int i=0;i< host.getTabWidget().getChildCount();i++){
            host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#D8D8D8"));
            tv=(TextView)host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#848484"));

        }
        host.getTabWidget().setCurrentTab(0);
        host.getTabWidget().getChildAt(0).setBackground(getResources().getDrawable(R.drawable.header));
        tv=(TextView)host.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for(int j=0; j< host.getTabWidget().getChildCount();j++){
                    host.getTabWidget().getChildAt(j).setBackgroundColor(Color.parseColor("#D8D8D8"));
                    tv=(TextView)host.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#848484"));
                }
                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.header));
                tv=(TextView)host.getCurrentTabView().findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

    }
}
