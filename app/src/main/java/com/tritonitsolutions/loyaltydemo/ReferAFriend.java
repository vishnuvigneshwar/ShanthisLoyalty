package com.tritonitsolutions.loyaltydemo;

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
import com.tritonitsolutions.layaltydemo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TritonDev on 13/10/2015.
 */
public class  ReferAFriend extends ActionBarActivity {
    Toolbar toolbar;
    EditText refer_email, refer_name, refer_phone;
    Button send;
   // TextView code;
    String user_id, emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referafriend);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences("User-id", MODE_PRIVATE);
        user_id = pref.getString("User_id", null);
        refer_email = (EditText) findViewById(R.id.et_refer);
        refer_name = (EditText) findViewById(R.id.et_refer_name);
        refer_phone = (EditText) findViewById(R.id.et_refer_phone);
        send = (Button) findViewById(R.id.btn_send);
       // code = (TextView) findViewById(R.id.tv_refer_code);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidEmail = false, isValidName, isValidPhone = false;
                if (refer_name.getText().toString().matches("")) {
                    refer_name.setError("Enter name !");
                    isValidName = false;
                } else {
                    isValidName = true;
                }
                if (refer_phone.getText().toString().matches("")) {
                    refer_phone.setError("Enter mobile number !");
                } else if (!(refer_phone.getText().toString().length() == 10)) {
                    refer_phone.setError("Enter valid mobile number !");
                    isValidPhone = false;

                } else {
                    isValidPhone = true;
                }
                if (refer_email.getText().toString().matches("")) {
                    refer_email.setError("Enter your friend eMail !");
                } else if (!(refer_email.getText().toString().matches(emailPattern))) {
                    refer_email.setError("Enter valid email!");
                    isValidEmail = false;

                } else {
                    isValidEmail = true;
                }
                if (isValidName && isValidPhone && isValidEmail) {
                    new loadReferralCode().execute();
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private class loadReferralCode extends AsyncTask<String, Void, String> {
        String jsonValue;

        @Override
        protected String doInBackground(String... params) {
            try {
                String url = URL.REFER_URL + user_id + "&name=" + refer_name.getText().toString() + "&phone=" + refer_phone.getText().toString() + "&email=" + refer_email.getText().toString();
                System.out.println("referfriend" + url);
                ServiceHandler handler = new ServiceHandler();
                jsonValue = handler.makeServiceCall(url, ServiceHandler.GET);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(jsonValue);
                JSONArray jsonArray = jsonObject.getJSONArray("referal_member");
                JSONObject object = jsonArray.getJSONObject(0);
                int referral_value = object.getInt("result");
                if(referral_value==1){
                Toast.makeText(getApplicationContext(),"Successfully sent the referral code",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ReferAFriend.this,MainActivity.class);
                startActivity(intent);


                }else {
                    Toast.makeText(getApplicationContext(),"Technical Error.",Toast.LENGTH_SHORT).show();
                }

               // code.setText(referral_code);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
