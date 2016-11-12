package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 9/9/2015.
 */
public class NotificationOffer extends ActionBarActivity {
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> notification_store;
    JSONArray list=null;
    public static final String NOTIFICATION="offer";
  // public static final String NOTIFICATION_ID = "id";
    public static final String TITLE="title";
    public static final String DESCRIPTION="discription";
    //public static final String COUPON_CODE="code";
    public static final String EXPIRE_DATE="expire";
    NotificationOfferAdapter adapter;
    ListView lv;
    ProgressDialog dialog;
    LinearLayout layout;
    String R_user_id;

    private SwipeRefreshLayout swipe_refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_offer);
//        toolbar=(Toolbar)findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("NotificationActivty");
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        R_user_id = pref.getString("User_id", null);
        System.out.println("key"+R_user_id);
        notification_store=new ArrayList<HashMap<String, String>>();
        lv=(ListView)findViewById(R.id.lv_notification);
        swipe_refresh_layout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.GREEN);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                ( new Handler()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipe_refresh_layout.setRefreshing(false);

                    }
                }, 1000);
            }
        });

        new loadNotificationOfferData().execute();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view.findViewById(R.id.tv_desc);
                copyTextToClipboard(textView);


                return false;
            }
        });

    }
    public void copyTextToClipboard(TextView txtView){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txtView.getText().toString());
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",txtView.getText().toString());
            clipboard.setPrimaryClip(clip);
//            String str=clip.toString();
//            Toast.makeText(getApplicationContext(),"Coupon code copied:"+ str,Toast.LENGTH_LONG).show();
        }

    }
    private class loadNotificationOfferData extends AsyncTask<String,Void,Void>{
        String jsonStr;
        protected void onPreExecute(){
            dialog=new ProgressDialog(NotificationOffer.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                System.out.println("Url format"+URL.NOTIFICATION_OFFER_URL +R_user_id);
                ServiceHandler handler=new ServiceHandler();
                jsonStr=handler.makeServiceCall(URL.NOTIFICATION_OFFER_URL +R_user_id,ServiceHandler.GET);
                System.out.println("values----------->" + jsonStr);
            }catch (Exception e){

                e.printStackTrace();
            }

            if(jsonStr != null){
                try {

                    JSONObject obj=new JSONObject(jsonStr);
                    System.out.println("notifytitle_jsonstring"+jsonStr);
                    list=obj.getJSONArray(NOTIFICATION);
                    for(int i=0;i<list.length();i++){
                        JSONObject ob=list.getJSONObject(i);
                       // String notofi_id=ob.getString(NOTIFICATION_ID);
                        //String notfi_code=ob.getString(COUPON_CODE);
                        String notfi_title=ob.getString(TITLE);
                        System.out.println("notifytitle"+notfi_title);
                        String notfi_date=ob.getString(EXPIRE_DATE);
                        String notfi_des=ob.getString(DESCRIPTION);


                        HashMap<String,String>notification= new HashMap<String,String>();
                       // notification.put(NOTIFICATION_ID,notofi_id);
                        notification.put(TITLE,notfi_title);
                        notification.put(DESCRIPTION,notfi_des);
                        notification.put(EXPIRE_DATE,notfi_date);
                       // notification.put(COUPON_CODE,notfi_code);
                        notification_store.add(notification);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            return null;
        }
        protected void onPostExecute(Void result){
            swipe_refresh_layout.setRefreshing(false);
            adapter=new NotificationOfferAdapter(NotificationOffer.this,notification_store);
            lv.setAdapter(adapter);
             dialog.dismiss();

        }
    }

}
