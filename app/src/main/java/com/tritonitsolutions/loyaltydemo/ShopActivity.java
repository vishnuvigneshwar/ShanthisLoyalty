package com.tritonitsolutions.loyaltydemo;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 8/19/2015.
 */
public class ShopActivity extends ActionBarActivity  {
    Toolbar toolbar;
    public static final String TAG_STORE="store";
    public static final String TAG_STORE_NAME="p_name";
    public static final String TAG_STORE_IMAGE="p_img";
    ArrayList<HashMap<String,String>>store_list;
    GridView gv;
    ProgressDialog dialog;
    JSONArray list=null;
    ShopAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        gv=(GridView)findViewById(R.id.gridView);
        store_list=new ArrayList<HashMap<String, String>>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        new loadStoreData().execute();
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                store_list.clear();
                new loadStoreData().execute(URL.STORE_DETAILS);
            }
        });
    }

    private class loadStoreData extends AsyncTask<String,Void,Void>{
        String jsonStr;
        protected void onPreExecute(){
//            dialog=new ProgressDialog(ShopActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler=new ServiceHandler();
                jsonStr=handler.makeServiceCall(URL.STORE_DETAILS,ServiceHandler.GET);
                System.out.println("values" + jsonStr);
            }catch (Exception e){
                e.printStackTrace();
            }

         if(jsonStr != null){
             try {

                 JSONObject obj=new JSONObject(jsonStr);
                 list=obj.getJSONArray(TAG_STORE);
                 System.out.println("jjjjjjjj---->"+list);

                     for (int i = 0; i < list.length(); i++) {

                         System.out.println("jjjj----->" + list);
                         JSONObject ob = list.getJSONObject(i);
                         String st_name = ob.getString(TAG_STORE_NAME);
                         String st_image = ob.getString(TAG_STORE_IMAGE);

                         HashMap<String, String> stores = new HashMap<String, String>();
                         stores.put(TAG_STORE_NAME, st_name);
                         stores.put(TAG_STORE_IMAGE, st_image);
                         store_list.add(stores);
                     }

             }catch (JSONException e){
                 e.printStackTrace();
             } catch (Exception ex){
                 ex.printStackTrace();
             }

         }
//            else {
//             ShopActivity.this.runOnUiThread(new Runnable() {
//                 @Override
//                 public void run() {
//                     Toast.makeText(getApplicationContext(), "Now there is no store available !!", Toast.LENGTH_LONG).show();
//
//                 }
//             });
//
//         }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        protected void onPostExecute(Void result) {

            adapter = new ShopAdapter(ShopActivity.this, store_list);
            gv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // dialog.dismiss();
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);

        }



    }





}
