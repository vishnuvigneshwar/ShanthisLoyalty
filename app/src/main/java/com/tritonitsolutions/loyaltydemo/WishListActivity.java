package com.tritonitsolutions.loyaltydemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
/**
 * Created by TritonDev on 14/9/2015.
 */
public class WishListActivity extends ActionBarActivity {
    Toolbar toolbar;
    EditText wishlist_name,wishlist_email,wishlist_ph,wishlist_loyalty,wishlist_description;
    Button wishlist_submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_layout);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        wishlist_name=(EditText)findViewById(R.id.et_wishlist_name);
        wishlist_email=(EditText)findViewById(R.id.et_wishlist_email);
        wishlist_ph=(EditText)findViewById(R.id.et_wishlist_phno);
        wishlist_loyalty=(EditText)findViewById(R.id.et_wishlist_loyalty_no);
        wishlist_description=(EditText)findViewById(R.id.et_wishlist_description);
        wishlist_submit=(Button)findViewById(R.id.btn_wishlist_submit);
        wishlist_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidName,isValidEmail = false,isValidPh = false,isValidlcn = false,isValidDec;
                if(wishlist_name.getText().toString().matches("")){
                    wishlist_name.setError("Enter name !");
                    isValidName=false;
                }
                else {
                    isValidName=true;
                }
                if(wishlist_email.getText().toString().matches("")){
                    wishlist_email.setError("Enter eMail !");
                }
                else if (!(wishlist_email.getText().toString().matches(emailPattern))){
                    wishlist_email.setError("Enter valid eMail !");
                    isValidEmail=false;

                }else {
                    isValidEmail=true;
                }
                if(wishlist_ph.getText().toString().matches("")){
                    wishlist_ph.setError("Enter phone number !");
                }else if(!(wishlist_ph.getText().toString().length()==10)){
                    wishlist_ph.setError("Enter 10-digit number !");
                    isValidPh=false;

                }else {
                    isValidPh=true;
                }
                if(wishlist_loyalty.getText().toString().matches("")){
                    wishlist_loyalty.setError("Enter card number !");
                } else if(!(wishlist_loyalty.getText().toString().length()==12)){
                    wishlist_loyalty.setError("Enter 12-digit number !");
                    isValidlcn=false;
                }else {
                    isValidlcn=true;
                }
                if(wishlist_description.getText().toString().matches("")){
                    wishlist_description.setError("Enter your description");
                    isValidDec=false;

                }else {
                    isValidDec=true;
                }
                if(isValidName && isValidEmail && isValidPh && isValidlcn && isValidDec){
                    new loadWishlistData().execute();

                }

            }
        });

    }
    private class loadWishlistData extends AsyncTask<String,Void,String>{
        String json;

        @Override
        protected String doInBackground(String... params) {
            try {
                String url = URL.WISH_LIST_URL + "name=" + wishlist_name.getText().toString() + "&email=" + wishlist_email.getText().toString() + "&phone=" + wishlist_ph.getText().toString() + "&loyalty_card=" + wishlist_loyalty.getText().toString() + "&wishlist=" + URLEncoder.encode(wishlist_description.getText().toString());
                ServiceHandler serviceHandler = new ServiceHandler();
                json = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
                System.out.println("JsonValues------->"+json);
            }
                catch (Exception e) {
                    e.printStackTrace();

                }

                return null;
            }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray("wishlistt");
                JSONObject object=jsonArray.getJSONObject(0);
                int wishlist=object.getInt("value");
                if(wishlist==1){
                    Toast.makeText(getApplicationContext(),"Successfully sent your wishlist",Toast.LENGTH_LONG).show();
                    intent=new Intent(WishListActivity.this,MainActivity.class);
                    startActivity(intent);
                    wishlist_name.setText("");
                    wishlist_email.setText("");
                    wishlist_ph.setText("");
                    wishlist_loyalty.setText("");
                    wishlist_description.setText("");
                }
                else {

                    Toast.makeText(getApplicationContext(),"invalid",Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }

        }

        }

    }

