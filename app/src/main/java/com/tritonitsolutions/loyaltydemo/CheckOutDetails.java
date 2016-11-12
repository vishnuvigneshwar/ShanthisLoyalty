package com.tritonitsolutions.loyaltydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.datamodel.CheckOutData;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Triton on 1/20/2016.
 */
public class CheckOutDetails extends ActionBarActivity {
    Toolbar toolbar;
    EditText check_address, check_address1, check_address2, check_address3,check_city, check_pin, check_state, check_country;
    Button check_out_submit;
    Intent intent;
    String user_id,user_name;
    TextView checkout_count;
    int value;
    List<CheckOutData> check;
    int qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_details_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        check_address = (EditText) findViewById(R.id.et_checkout_address);
        check_address1 = (EditText) findViewById(R.id.et_checkout_address_line1);
        check_address2 = (EditText) findViewById(R.id.et_checkout_address_line2);
        check_address3 = (EditText) findViewById(R.id.et_checkout_address_line3);
        check_city = (EditText) findViewById(R.id.et_checkout_city);
        check_pin = (EditText) findViewById(R.id.et_pin_no);
        check_state = (EditText) findViewById(R.id.et_checkout_state);
        check_country = (EditText) findViewById(R.id.et_checkout_country);

        check_out_submit = (Button) findViewById(R.id.btn_checkout_details_submit);


        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        SharedPreferences preferences=getSharedPreferences("cus-name", MODE_PRIVATE);
        user_name=preferences.getString("cus_name", null);



        check_out_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidAddress, isValidAddressLine1, isValidAddressLine2, isValidAddressLine3, isValidCity, isValidPinNo, isValidState, isValidCountry;
                if (check_address.getText().toString().matches("")) {
                    check_address.setError("Enter address !");
                    isValidAddress = false;
                } else {
                    isValidAddress = true;
                }
                if (check_address1.getText().toString().matches("")) {
                    check_address1.setError("Enter address line1 !");
                    isValidAddressLine1 = false;

                } else {
                    isValidAddressLine1 = true;
                }
                if (check_address2.getText().toString().matches("")) {
                    check_address2.setError("Enter address line2 !");
                    isValidAddressLine2 = false;
                } else {
                    isValidAddressLine2 = true;
                }
                if (check_address3.getText().toString().matches("")) {
                    check_address3.setError("Enter address line3 !");
                    isValidAddressLine3 = false;

                } else {
                    isValidAddressLine3 = true;

                }
                if (check_city.getText().toString().matches("")) {
                    check_city.setError("Enter city !");
                    isValidCity = false;

                } else {
                    isValidCity = true;

                }
                if (check_pin.getText().toString().matches("")) {
                    check_pin.setError("Enter pin no !");
                    isValidPinNo = false;

                } else {
                    isValidPinNo = true;

                }

                if (check_state.getText().toString().matches("")) {
                    check_state.setError("Enter state !");
                    isValidState = false;

                } else {
                    isValidState = true;

                }

                if (check_country.getText().toString().matches("")) {
                    check_country.setError("Enter country !");
                    isValidCountry = false;

                } else {
                    isValidCountry = true;

                }
                if (isValidAddress && isValidAddressLine1 && isValidAddressLine2 && isValidAddressLine3 && isValidCity && isValidPinNo && isValidState && isValidCountry) {
                    new newUserAsynTask().execute();
                    intent = new Intent(CheckOutDetails.this, MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Successfully Checkout Details Registered", Toast.LENGTH_LONG).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }


  /*  @Override
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
    }*/

    public class newUserAsynTask extends AsyncTask<String, String, String> {
        String jsonStr;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
//"&usname=" +  user_name +
                String url = URL.CHECKOUT_DETAILS_URL +"&usid="+ user_id + "&checkaddress=" + check_address.getText().toString() + "&checkaddress1=" + check_address1.getText().toString() + "&checkaddress2=" + check_address2.getText().toString() + "&checkaddress3=" + check_address3.getText().toString() + "&checkcity=" + check_city.getText().toString() + "&checkpinno=" + check_pin.getText().toString() + "&checkstate=" + check_state.getText().toString() + "&checkcountry=" +check_country.getText().toString();
                System.out.println("URL VALUES-------->" + url);
                ServiceHandler handler = new ServiceHandler();
                jsonStr = handler.makeServiceCall(url, ServiceHandler.GET);
                System.out.println("data----->" + jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(jsonStr);
                JSONArray jArray = object.getJSONArray("check");
                JSONObject jasonObject = jArray.getJSONObject(0);
                int returnedResult = jasonObject.getInt("value");



                if(returnedResult==1){
                    Toast.makeText(getApplicationContext(), "Successfully Checkout Details Registered", Toast.LENGTH_LONG).show();
                    intent = new Intent(CheckOutDetails.this, MainActivity.class);

                    startActivity(intent);
                        check_address.setText("");
                        check_address1.setText("");
                        check_address2.setText("");
                        check_address3.setText("");
                        check_city.setText("");
                        check_pin.setText("");
                        check_state.setText("");
                        check_country.setText("");

                }else {
                    Toast.makeText(getApplicationContext(),"Technical error",Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setCancelable(false);
        builder.setMessage("Enter all fields and click submit");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.show();
    }
}
