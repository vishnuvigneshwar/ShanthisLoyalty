package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.datamodel.CheckOutData;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TritonDev on 9/10/2015.
 */
public class FinalCartActivity extends ActionBarActivity implements View.OnClickListener {
    TextView pro_name,pro_size,pro_price,pro_desc;
    String cart_name,cart_img1,cart_img2,cart_img3,cart_size,cart_price,cart_desc,cart_id;
    Button cart,buy,chk_coupon;
    Button plus,minus;
    String user_id;
    int plus_count=0;
    TextView total_number,total_amt,cart_count,final_price;
    Toolbar toolbar;
    ImageView img_pro,img_pro_1,img_pro_2,img_pro_3;
    EditText coupon_code;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
        pro_name=(TextView)findViewById(R.id.tv_pro_name);
        pro_size=(TextView)findViewById(R.id.tv_pro_size);
        pro_price=(TextView)findViewById(R.id.tv_pro_price);
        pro_desc=(TextView)findViewById(R.id.tv_pro_description);
        plus=(Button)findViewById(R.id.btn_plus);
        minus=(Button)findViewById(R.id.btn_minus);
        cart=(Button)findViewById(R.id.btn_cart);
        buy=(Button)findViewById(R.id.btn_buy);
        total_number=(TextView)findViewById(R.id.tv_total);
        total_amt=(TextView)findViewById(R.id.tv_total_amount);
        img_pro=(ImageView)findViewById(R.id.iv_img);
        img_pro_1=(ImageView)findViewById(R.id.img_1);
        img_pro_2=(ImageView)findViewById(R.id.img_2);
        img_pro_3=(ImageView)findViewById(R.id.img_3);
        coupon_code=(EditText)findViewById(R.id.et_coupon);
        chk_coupon=(Button)findViewById(R.id.btn_check);
        final_price=(TextView)findViewById(R.id.tv_final_price);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");

        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        System.out.println("id------->"+user_id);


        Intent i=getIntent();
        cart_name=i.getStringExtra("p_name");
        cart_id=i.getStringExtra("p_id");
        System.out.println("pro id----->"+cart_id);
        cart_size=i.getStringExtra("p_size");
        cart_price=i.getStringExtra("p_prize");
        cart_desc=i.getStringExtra("p_desc");
        cart_img1=URL.BASE_URL+"url/"+i.getStringExtra("p_img1");
        cart_img2=URL.BASE_URL+"url/"+i.getStringExtra("p_img2");
        cart_img3=URL.BASE_URL+"url/"+i.getStringExtra("p_img3");
        pro_name.setText(cart_name);
        pro_size.setText(cart_size);
        pro_price.setText(cart_price);
        pro_desc.setText(cart_desc);
        Picasso.with(FinalCartActivity.this).load(cart_img1).into(img_pro);
        Picasso.with(FinalCartActivity.this).load(cart_img1).into(img_pro_1);
        Picasso.with(FinalCartActivity.this).load(cart_img2).into(img_pro_2);
        Picasso.with(FinalCartActivity.this).load(cart_img3).into(img_pro_3);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ++plus_count;
                total_number.setText(String.valueOf(plus_count));
                //cart_count.setText(String.valueOf(plus_count));
                System.out.println("value----->" + pro_price);
                try {
                    int temp = Integer.parseInt(pro_price.getText().toString());
                    total_amt.setText(String.valueOf(plus_count * temp));
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No price given from server",Toast.LENGTH_SHORT).show();
                }


            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (plus_count != 0) {
                    --plus_count;
                } else {
                    Toast.makeText(getApplicationContext(), "No value selected", Toast.LENGTH_SHORT).show();
                }

                total_number.setText(String.valueOf(plus_count));
                //cart_count.setText(String.valueOf(plus_count));
                try {
                    int temp = Integer.parseInt(pro_price.getText().toString());
                    total_amt.setText(String.valueOf(plus_count * temp));

                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"No price given from server",Toast.LENGTH_SHORT).show();
                }

            }
        });
        chk_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(coupon_code.getText().toString().matches(""))){
                  new loadCouponData().execute();
                }
                else {
                    coupon_code.setError("Enter coupon code !");

                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plus_count==0){
                    Toast.makeText(getApplicationContext(),"No Product Selected",Toast.LENGTH_SHORT).show();
                }else
                {
                    if(final_price.getText().toString().matches("")){
                        final_price.setText(total_amt.getText().toString());
                    }

                  DataBaseHandler handler=new DataBaseHandler(getApplicationContext());
                    CheckOutData data=new CheckOutData();
                    data.setPro_img(cart_img1);
                    data.setPro_name(pro_name.getText().toString());
                    data.setPro_size(pro_size.getText().toString());
                    data.setPro_price(pro_price.getText().toString());
                    data.setPro_count(total_number.getText().toString());
                    data.setPro_total(final_price.getText().toString());
                    data.setPro_coup(coupon_code.getText().toString());
                    data.setPro_id(cart_id);
                    handler.addData(data);
                    buy.setVisibility(View.VISIBLE);
                    System.out.println("coup code value"+coupon_code.getText().toString());
                    cart_count.setText(String.valueOf(plus_count));
                    Toast.makeText(getApplicationContext(),"Successfully Added your product to cart",Toast.LENGTH_SHORT).show();

                }
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FinalCartActivity.this,CheckOutActivity.class);
                buy.setVisibility(View.GONE);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });
        img_pro_1.setOnClickListener(this);
        img_pro_2.setOnClickListener(this);
        img_pro_3.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.updatecount_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        cart_count = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        return super.onCreateOptionsMenu(menu);
    }
        @Override
    public void onClick(View v) {

     switch (v.getId()){
         case R.id.img_1:
             img_pro.setImageDrawable(img_pro_1.getDrawable());
             break;
         case R.id.img_2:
             img_pro.setImageDrawable(img_pro_2.getDrawable());
             break;
         case R.id.img_3:
             img_pro.setImageDrawable(img_pro_3.getDrawable());
             break;
     }

    }
    private class loadCouponData extends AsyncTask<String,Void,String>{
        String jsonData;
        String url;
        protected  void onPreExecute(){
            dialog=new ProgressDialog(FinalCartActivity.this);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
             url=URL.COUPON_URL+user_id+"&totalamt="+total_amt.getText().toString()+"&coupon="+coupon_code.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

            System.out.println("url---for coup cide---->"+url);
            ServiceHandler handler=new ServiceHandler();
            jsonData=handler.makeServiceCall(url,ServiceHandler.GET);
                System.out.println("json tesponce"+jsonData);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject jsonObject=new JSONObject(jsonData);
                JSONArray array=jsonObject.getJSONArray("coupon");
                JSONObject object=array.getJSONObject(0);
                int id=object.getInt("id");
                int amt=object.getInt("finalamt");
                if(id==1) {
                    final_price.setText(String.valueOf(amt));

                }else {
                    Toast.makeText(getApplicationContext(),"coupon code exist",Toast.LENGTH_LONG).show();
                    final_price.setText(String.valueOf(amt));
                    coupon_code.setText("");

                }
                dialog.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }


        }
    }

}
