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

/**
 * Created by TritonDev on 23/10/2015.
 */
public class NotificationActivty extends TabActivity {
    TabHost host;
    TextView tv;
    Toolbar toolbar;
    ViewPager pager = null;
    int count = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        host=getTabHost();
        TabHost.TabSpec Offer = host.newTabSpec("Notification Offer");
        Offer.setIndicator("Notification Offer");
        Intent loyaltyHistoryIntent = new Intent(this, NotificationOffer.class);
        Offer.setContent(loyaltyHistoryIntent);
        TabHost.TabSpec Delivery = host.newTabSpec("Delivery detail");
        Delivery.setIndicator("Delivery detail");
        Intent customerHistoryIntent = new Intent(this, NoticationDelivery.class);
        Delivery.setContent(customerHistoryIntent);
        host.addTab(Offer);
        host.addTab(Delivery);

        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#D8D8D8"));
            tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#848484"));

        }
        host.getTabWidget().setCurrentTab(0);
        host.getTabWidget().getChildAt(0).setBackground(getResources().getDrawable(R.drawable.header));
        tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for (int j = 0; j < host.getTabWidget().getChildCount(); j++) {
                    host.getTabWidget().getChildAt(j).setBackgroundColor(Color.parseColor("#D8D8D8"));
                    tv = (TextView) host.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#848484"));
                }
                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.header));
                tv = (TextView) host.getCurrentTabView().findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });

    }
}