package com.tritonitsolutions.loyaltydemo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by TritonDev on 11/9/2015.
 */
public class NewUserActivity extends ActionBarActivity {
    Toolbar toolbar;
    EditText reg_name, reg_email, reg_ph_no, reg_lastname, reg_dob, reg_pwd, reg_confirm_pwd,reg_refer;
    Spinner gender;
    Button reg_submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Intent intent;
    // String[] str_gender={"Gender","Male","Female"};
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        reg_name = (EditText) findViewById(R.id.et_newuser_name);
        reg_email = (EditText) findViewById(R.id.et_newuser_email);
        reg_ph_no = (EditText) findViewById(R.id.et_newuser_ph_no);
        gender = (Spinner) findViewById(R.id.et_newuser_gender);
        reg_lastname = (EditText) findViewById(R.id.et_newuser_last_name);
        reg_dob = (EditText) findViewById(R.id.et_newuser_dob);
        //     ArrayAdapter<String>sp_gender=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,str_gender);
        // gender.setAdapter(sp_gender);
        // reg_lcn = (EditText) findViewById(R.id.et_newuser_loyalty_no);
        reg_pwd = (EditText) findViewById(R.id.et_newuser_pwd);
        reg_pwd.setTypeface(Typeface.DEFAULT);
        reg_pwd.setTransformationMethod(new PasswordTransformationMethod());
        reg_confirm_pwd = (EditText) findViewById(R.id.et_newuser_confirm_pwd);
        reg_confirm_pwd.setTypeface(Typeface.DEFAULT);
        reg_confirm_pwd.setTransformationMethod(new PasswordTransformationMethod());
        reg_refer=(EditText)findViewById(R.id.et_newuser_refer);
        reg_submit = (Button) findViewById(R.id.btn_newuser_submit);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        reg_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewUserActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                reg_dob.setError(null);
            }
        });

        reg_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidName, isValidLastName, isValidDOB, isValidEmail = false, isValidPhone = false, isValidCardno = false, isValidPassword = false, isValidConfirmPassword = false;
                if (reg_name.getText().toString().matches("")) {
                    reg_name.setError("Enter first name !");
                    isValidName = false;
                } else {
                    isValidName = true;
                }
                if (reg_lastname.getText().toString().matches("")) {
                    reg_lastname.setError("Enter last name !");
                    isValidLastName = false;

                } else {
                    isValidLastName = true;
                }
                if (reg_email.getText().toString().matches("")) {
                    reg_email.setError("Enter eMail !");
                } else if (!(reg_email.getText().toString().matches(emailPattern))) {
                    reg_email.setError("Enter valid eMail !");
                    isValidEmail = false;

                } else {
                    isValidEmail = true;
                }
                if (reg_dob.getText().toString().matches("")) {
                    reg_dob.setError("Enter date of birth !");
                    isValidDOB = false;

                } else {
                    isValidDOB = true;
                    reg_dob.setError(null);

                }
                if (reg_ph_no.getText().toString().matches("")) {
                    reg_ph_no.setError("Enter mobile number !");
                } else if (!(reg_ph_no.getText().toString().length() == 10)) {
                    reg_ph_no.setError("Enter 10-digit number !");
                    isValidPhone = false;
                } else {
                    isValidPhone = true;
                }
//                if (reg_lcn.getText().toString().matches("")) {
//                    reg_lcn.setError("Enter card number !");
//
//                } else if (!(reg_lcn.getText().toString().length() == 12)) {
//                    reg_lcn.setError("Enter 12-digit card number !");
//
//                    isValidCardno = false;
//
//                } else {
//                    isValidCardno = true;
//                }
                if (reg_pwd.getText().toString().matches("")) {
                    reg_pwd.setError("Enter your password !");

                } else if (!(reg_pwd.getText().toString().length() >= 8)) {
                    reg_pwd.setError("password minimum 8-char !");
                    isValidPassword = false;
                } else {
                    isValidPassword = true;
                }

                if (!(reg_confirm_pwd.getText().toString().equals(reg_pwd.getText().toString()))) {
                    reg_confirm_pwd.setError("Password Mismatch !");
                    isValidConfirmPassword = false;
                } else {
                    isValidConfirmPassword = true;
                }


                if (isValidName && isValidLastName && isValidDOB && isValidEmail && isValidPhone && isValidPassword && isValidConfirmPassword) {
                    new newUserAsynTask().execute();

                }

            }
        });

    }

    public class newUserAsynTask extends AsyncTask<String, String, String> {
        String jsonStr;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String url = URL.NEW_USER_URL + "fname=" + reg_name.getText().toString() + "&lname=" + reg_lastname.getText().toString() + "&email=" + reg_email.getText().toString() + "&dob=" + reg_dob.getText().toString() + "&gender=" + gender.getSelectedItem().toString() + "&mobile=" + reg_ph_no.getText().toString() + "&password=" + reg_pwd.getText().toString() + "&ref_id=" +reg_refer.getText().toString();
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

                  if (returnedResult == 1) {
                    Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG).show();


                } else if (returnedResult == 2) {
                    Toast.makeText(getApplicationContext(), "Phone number already registered", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Successfully registered.Please login", Toast.LENGTH_LONG).show();
                    intent = new Intent(NewUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                    reg_name.setText("");
                    reg_lastname.setText("");
                    reg_dob.setText("");
                    reg_email.setText("");
                    reg_ph_no.setText("");
                    //reg_lcn.setText("");
                    reg_pwd.setText("");
                    reg_confirm_pwd.setText("");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        reg_dob.setText(sdf.format(myCalendar.getTime()));
    }
}



