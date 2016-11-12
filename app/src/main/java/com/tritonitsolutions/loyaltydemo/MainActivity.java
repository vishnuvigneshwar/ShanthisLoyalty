package com.tritonitsolutions.loyaltydemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    String TITLES[] = {"Profile","Shop","Refer a friend","Shop Locator","Call Us","Feedback","WishList","Logout"};
    int ICONS[] = {R.drawable.menu_profile,R.drawable.menu_shop,R.drawable.menu_review,R.drawable.menu_shop_locator,R.drawable.menu_callus,R.drawable.menu_feedback,R.drawable.menu_wishlist,R.drawable.menu_logout};
    String NAME;
    //String EMAIL = "11102000456";
    int PROFILE = R.drawable.img_user;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;
    Context cntx=this;
    ViewPager pager=null;
    int count=0;
    Timer timer;
    TextView notificationoffercount,txt_notificationdeliverycount;
    String R_user_id,randamval;
    LinearLayout ll_shop,ll_new_arrival,ll_notification,ll_loyalty;
    Intent intent;
    ArrayList<HashMap<String,String>> notificationofferarray,notificationdeliveryarray;
    int offersize,deliverysize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        NAME=getIntent().getStringExtra("user");
        System.out.println("name--->" + NAME);
        mAdapter = new RecyclerViewAdapter(TITLES,ICONS,NAME,PROFILE,this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        ll_loyalty=(LinearLayout)findViewById(R.id.iv_loyalty);
        ll_shop=(LinearLayout)findViewById(R.id.iv_shop);
        ll_new_arrival=(LinearLayout)findViewById(R.id.iv_new_arrival);
        ll_notification=(LinearLayout)findViewById(R.id.iv_notification);

        notificationoffercount=(TextView)findViewById(R.id.txt_notificationoffercount);
        txt_notificationdeliverycount=(TextView)findViewById(R.id.txt_notificationdeliverycount);

        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        R_user_id = pref.getString("User_id", null);
        System.out.println("key" + R_user_id);

        SharedPreferences preferences = getSharedPreferences("random-id", MODE_PRIVATE);
        randamval = preferences.getString("random_id", null);

        ll_loyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,LoyaltyActivity.class);
                startActivity(intent);
            }
        });
        ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,NotificationActivty.class);
                startActivity(intent);
            }
        });

        ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHandler dd=new DataBaseHandler(getApplicationContext());
                dd.delete();
                intent=new Intent(MainActivity.this,ShopCategoryActivity.class);
                startActivity(intent);


            }
        });
        ll_new_arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(MainActivity.this,NewArrivalActivity.class);
                startActivity(intent);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.nav_openDrawer,R.string.nav_closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        ViewPagerAdapter adapter=new ViewPagerAdapter(this);
        pager=(ViewPager)findViewById(R.id.reviewpager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        new NotificationOfferAsync().execute();
        new NotificationDeliveryAsync().execute();
   }

    //Notification Offer
    public class NotificationOfferAsync extends AsyncTask<Void,Void,String> {
        ProgressDialog progressDialog;
        String response;
        HashMap<String,String> hashMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();
            notificationofferarray=new ArrayList<HashMap<String,String>>();
            try{
                response=sh.makeServiceCall(URL.NOTIFICATION_OFFER_URL+R_user_id ,ServiceHandler.GET);
                JSONObject jsonObject=new JSONObject(response);
                JSONArray array=jsonObject.getJSONArray("offer");
                for (int i=0;i<array.length();i++){
                    hashMap=new HashMap<String,String>();
                    JSONObject jsonObject1=array.getJSONObject(i);
                    String title=jsonObject1.getString("title");
                    String expire=jsonObject1.getString("expire");
                    String discription=jsonObject1.getString("discription");

                    hashMap.put("title",title);
                    hashMap.put("expire",expire);
                    hashMap.put("discription", discription);
                    notificationofferarray.add(hashMap);
                }

            }catch (Exception e){

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            offersize=notificationofferarray.size();

           notificationoffercount.setText(String.valueOf(offersize));

        }
    }

    //Notification delivery
    public class NotificationDeliveryAsync extends AsyncTask<Void,Void,String> {
        ProgressDialog progressDialog;
        String response;
        HashMap<String,String> hashMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();
            notificationdeliveryarray=new ArrayList<HashMap<String,String>>();
            try{
                response=sh.makeServiceCall(URL.NOTIFICATION_DELIVERY_URL+randamval+"&id="+R_user_id,ServiceHandler.GET);
                JSONObject jsonObject=new JSONObject(response);
                JSONArray array=jsonObject.getJSONArray("accept");
                for (int i=0;i<array.length();i++){
                    hashMap=new HashMap<String,String>();
                    JSONObject jsonObject1=array.getJSONObject(i);
                    String total=jsonObject1.getString("total");
                    String p_invoice=jsonObject1.getString("p_invoice");
                    String p_desc1=jsonObject1.getString("p_desc1");
                    String p_date=jsonObject1.getString("p_date");

                    hashMap.put("total",total);
                    hashMap.put("p_invoice",p_invoice);
                    hashMap.put("p_desc1", p_desc1);
                    hashMap.put("p_date", p_date);
                    notificationdeliveryarray.add(hashMap);
                }

            }catch (Exception e){

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            deliverysize=notificationofferarray.size();

            txt_notificationdeliverycount.setText(String.valueOf(deliverysize));

        }
    }






    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit App");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}



