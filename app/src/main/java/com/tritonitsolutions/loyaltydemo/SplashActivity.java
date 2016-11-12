package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 8/17/2015.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIME=3000;
    Intent intent;
    String user,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        boolean net = isNetworkAvailable();
        startAnimation();
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user = pref.getString("User_id", null);

        SharedPreferences preferences = getSharedPreferences("cus-name", MODE_PRIVATE);
        name = preferences.getString("cus_name", null);


        if (net) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(user!= null){
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("user",name);
                        startActivity(intent);
                        finish();

                    }else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }, SPLASH_TIME);

        }
        else {
            AlertDialog dialog=new AlertDialog.Builder(SplashActivity.this).create();
            dialog.setTitle("Internet Connection");
            dialog.setMessage("Now Internet connection is not available.Check your Network");
            dialog.setIcon(R.drawable.ic_internet);
            dialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.show();
        }

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return info !=null && info.isConnected();
    }
    private void startAnimation(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.ll_layout);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView)findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);


    }
}
