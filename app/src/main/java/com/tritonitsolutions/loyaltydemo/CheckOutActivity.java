package com.tritonitsolutions.loyaltydemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.datamodel.CheckOutData;
import com.tritonitsolutions.layaltydemo.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TritonDev on 15/10/2015.
 */
public class CheckOutActivity extends ActionBarActivity {
    Toolbar toolbar;
    ListView lv;
    CheckOutAdapter adapter;
    Button checkout;
    String ch_name,ch_size,ch_price,ch_count,ch_total,user_id,user_name,ch_coupcode,ch_id;
    TextView checkout_count;
    int value;
    String randamval;
    List<CheckOutData> check;
    int qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_layout);
        RandomValue randomValue=new RandomValue();
        randamval=randomValue.randomString();
        System.out.println("Random valu Generation--------->" + randamval);
        SharedPreferences pred = getApplicationContext().getSharedPreferences("random-id", MODE_PRIVATE);
        SharedPreferences.Editor editorp = pred.edit();
        editorp.putString("random_id", randamval);
        editorp.commit();

        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        lv=(ListView)findViewById(R.id.lv_checkout);
        checkout=(Button)findViewById(R.id.btn_chk);
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getApplicationContext());
        check=dataBaseHandler.getAllData();
        adapter=new CheckOutAdapter(CheckOutActivity.this,check);
        lv.setAdapter(adapter);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        SharedPreferences preferences=getSharedPreferences("cus-name", MODE_PRIVATE);
        user_name=preferences.getString("cus_name", null);
        value = adapter.getCount();


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("count------>" + value);
                for (int i = 0; i < value; i++) {
                    ch_name = check.get(i).getPro_name();
                    ch_size = check.get(i).getPro_size();
                    ch_price = check.get(i).getPro_price();
                    ch_count = check.get(i).getPro_count();
                    ch_total = check.get(i).getPro_total();
                    ch_coupcode = check.get(i).getPro_coup();
                    ch_id = check.get(i).getPro_id();

                    System.out.println("json------>"+ ch_id + ch_name + ch_size + ch_price + ch_count + ch_total+"&coupcode="+ch_coupcode);
                    new loadCartData().execute();

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.updatecount_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        checkout_count = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);

        for(int j=0;j< value;j++){
             qty+=Integer.parseInt(check.get(j).getPro_count());
            checkout_count.setText(String.valueOf(qty));
        }

        return super.onCreateOptionsMenu(menu);
    }
    private class loadCartData extends AsyncTask<String ,Void,Void> {
        String jsonValue;
        String url;
        List<NameValuePair> list = new ArrayList<NameValuePair>();

        protected void onPreExecute(){
            try {

                url = URL.CART_URL + URLEncoder.encode(user_id) +"&proid="+ URLEncoder.encode(ch_id) + "&size=" + URLEncoder.encode(ch_size) + "&count=" + URLEncoder.encode(ch_count)+ "&total=" + URLEncoder.encode(ch_total)+"&coupcode="+URLEncoder.encode(ch_coupcode)+"&pname="+URLEncoder.encode(ch_name)+"&random="+randamval;
                System.out.println("url...."+url);

            }catch (Exception e){
                e.printStackTrace();

            }

        }


        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(url, ServiceHandler.GET);

            }catch (Exception ex){
                ex.printStackTrace();
            }



            return null;
        }
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            try {
                JSONObject object = new JSONObject(jsonValue);
                JSONArray Jarray = object.getJSONArray("cart");
                JSONObject Jasonobject = Jarray.getJSONObject(0);
                int returnedResult = Jasonobject.getInt("value");
                if(returnedResult==1){
                    Toast.makeText(getApplicationContext(), "Successfully ordered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CheckOutActivity.this,CheckOutDetails.class);
                    intent.putExtra("user",user_name);
                    intent.putExtra("userid",user_id);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Technical error",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CheckOutActivity.this,ShopCategoryActivity.class);
        startActivity(intent);
        finish();

    }
}
