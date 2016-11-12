package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by TritonDev on 23/10/2015.
 */
public class NoticationDelivery extends Activity {
    String R_user_id;
    ArrayList<HashMap<String,String>> notificationdelivery_store;
    public  final static  String PRO_DELIVERY="accept";
    public  final  static  String PRO_TOTAL="total";
    public  final  static  String PRO_INVOICE="p_invoice";
    public  final  static  String PRO_REPORT="p_desc1";
    public  final  static  String PRO_DATE="p_date";
    NotificationdeliveryAdapter adapter;
    ListView lv;
    ProgressDialog dialog;
    LinearLayout layout;
    String randamval;
    private SwipeRefreshLayout swipe_refresh_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_delivery_report);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        R_user_id = pref.getString("User_id", null);
        System.out.println("key" + R_user_id);
        SharedPreferences preferences = getSharedPreferences("random-id", MODE_PRIVATE);
        randamval = preferences.getString("random_id", null);
        lv=(ListView)findViewById(R.id.lv_notification_delivery_report);
        notificationdelivery_store=new ArrayList<HashMap<String, String>>();
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
        new loadNotificationDeliveryData().execute();
    }
    private class loadNotificationDeliveryData extends AsyncTask<String,Void,Void>{
        String jsonStr;
        String notfi_invoice;
        protected void onPreExecute(){
            dialog=new ProgressDialog(NoticationDelivery.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();


        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                System.out.println("Url format"+URL.NOTIFICATION_DELIVERY_URL +randamval+"&id="+R_user_id);
                ServiceHandler handler=new ServiceHandler();
                jsonStr=handler.makeServiceCall(URL.NOTIFICATION_DELIVERY_URL + randamval +"&id="+R_user_id,ServiceHandler.GET);
                System.out.println("Ask----------->" + jsonStr);
            }catch (Exception e){

                e.printStackTrace();
            }

            if(jsonStr != null){
                try {

                    JSONObject obj=new JSONObject(jsonStr);
                    JSONArray list=obj.getJSONArray(PRO_DELIVERY);
                    for(int i=0;i<list.length();i++){
                        JSONObject ob=list.getJSONObject(i);
                        String notofi_total=ob.getString(PRO_TOTAL);
                        if(ob.isNull(PRO_INVOICE)){
                            notfi_invoice="";
                        }
                        else {
                            notfi_invoice=ob.getString(PRO_INVOICE);
                        }

                        String notfi_report=ob.getString(PRO_REPORT);
                        String notfi_date = ob.getString(PRO_DATE);


                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            Date testDate = null;
                            try {
                                    testDate = sdf.parse(notfi_date);

                            } catch (Exception ex) {

                                ex.printStackTrace();
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy");
                            String newFormat = formatter.format(testDate);


                        HashMap<String,String>notification= new HashMap<String,String>();
                        notification.put(PRO_TOTAL,notofi_total);
                        notification.put(PRO_INVOICE,notfi_invoice);
                        notification.put(PRO_REPORT,notfi_report);
                        notification.put(PRO_DATE,newFormat);
                        notificationdelivery_store.add(notification);


                    }

                }catch (JSONException e){
                    e.printStackTrace();
                } catch (Exception ex){
                    ex.printStackTrace();
                }

            }


            return null;
        }

        protected void onPostExecute(Void result){
             super.onPostExecute(result);
            swipe_refresh_layout.setRefreshing(false);
                adapter=new NotificationdeliveryAdapter(NoticationDelivery.this,notificationdelivery_store);
                lv.setAdapter(adapter);
                dialog.dismiss();



        }
    }
}
