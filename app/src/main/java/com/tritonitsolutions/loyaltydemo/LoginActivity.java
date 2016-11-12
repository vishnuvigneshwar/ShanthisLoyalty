package com.tritonitsolutions.loyaltydemo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TritonDev on 10/9/2015.
 */
public class LoginActivity extends ActionBarActivity {

    EditText user_name, password,forgot_email;
    Button login, register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Intent intent;
    TextView forgot_pwd;
    String user_name1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        user_name = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_pwd);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        forgot_pwd=(TextView)findViewById(R.id.tv_forgot_pwd);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidUser = false, isValidPwd = false;
                if (user_name.getText().toString().matches("")) {
                    user_name.setError("Enter eMail id !");

                } else if (!(user_name.getText().toString().matches(emailPattern))) {
                    user_name.setError("Enter Valid eMail address !");
                    isValidUser = false;

                } else {
                    isValidUser = true;
                }
                if (password.getText().toString().matches("")) {
                    password.setError("Enter Password !");

                } else if (!(password.getText().toString().length() >= 8)) {
                    password.setError("Minimum 8-char !");
                    isValidPwd = false;
                } else {
                    isValidPwd = true;
                }
                if (isValidUser && isValidPwd) {
                    new loadLoginData().execute();

                }


            }
        });
        forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.custom_alertdialog);
                dialog.setTitle("Forgot Password");
                forgot_email=(EditText)dialog.findViewById(R.id.et_forgot_email);
                Button ok=(Button)dialog.findViewById(R.id.btn_forgot_ok);
                Button cancel=(Button)dialog.findViewById(R.id.btn_forgot_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(forgot_email.getText().toString().matches("")){
                            forgot_email.setError("Enter eMail !");
                            dialog.show();
                        }else if(!(forgot_email.getText().toString().matches(emailPattern))){
                            forgot_email.setError("Enter valid eMail !");
                            dialog.show();

                        }else {
                            new loadForgot().execute();
                            dialog.dismiss();
                        }

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }


        });

    }

    private class loadLoginData extends AsyncTask<String, Void, String> {
        String jsonStr;

        @Override
        protected String doInBackground(String... params) {
            String url = URL.LOGIN_URL+"email=" + user_name.getText().toString() + "&password=" + password.getText().toString();
            System.out.println("urls------->" + url);
            ServiceHandler handler = new ServiceHandler();
            jsonStr = handler.makeServiceCall(url, ServiceHandler.GET);
            System.out.println("returnValue------>"+jsonStr);

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(jsonStr);
                JSONArray Jarray = object.getJSONArray("login");
                JSONObject Jasonobject = Jarray.getJSONObject(0);
                int returnedResult = Jasonobject.getInt("value");
                try {
                  String user_id = Jasonobject.getString("cus_id");
                  user_name1=Jasonobject.getString("name");

                  SharedPreferences pred = getApplicationContext().getSharedPreferences("User-id", MODE_PRIVATE);
                  SharedPreferences.Editor editorp = pred.edit();
                  editorp.putString("User_id", user_id);
                  editorp.commit();

                  SharedPreferences preferences=getApplicationContext().getSharedPreferences("cus-name",MODE_PRIVATE);
                  SharedPreferences.Editor editor = preferences.edit();
                  editor.putString("cus_name", user_name1);
                  editor.commit();

                }catch (Exception e)
              {
                  e.printStackTrace();
                  Toast.makeText(getApplicationContext(),"Username or Password Incorrect",Toast.LENGTH_LONG).show();

              }
                  if (returnedResult == 1)
                    {
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user",user_name1);
                    startActivity(intent);
                        user_name.setText("");
                        password.setText("");
                        finish();
                    }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }


        }


    }
    private class loadForgot extends AsyncTask<String,Void,String>{
        String forgot_json;

        @Override
        protected String doInBackground(String... params) {
            String url_forgot=URL.FORGOT_URL+forgot_email.getText().toString();
            ServiceHandler handler=new ServiceHandler();
            forgot_json=handler.makeServiceCall(url_forgot,ServiceHandler.GET);

            return null;
        }
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(forgot_json);
                System.out.println("jsonValue-------->"+jsonObject);
                JSONArray jsonArray=jsonObject.getJSONArray("mail");
                JSONObject obj=jsonArray.getJSONObject(0);
                int forgot=obj.getInt("value");
                if(forgot==1){
                    Toast.makeText(getApplicationContext(),"Successfully your password reset.Check your eMail",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"eMail invalid",Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}

