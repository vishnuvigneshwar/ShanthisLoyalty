package com.tritonitsolutions.loyaltydemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 6/10/2015.
 */
public class CategoryDetailActivity extends Activity {

    String strcategorytypename,strcategoryname;
    String strsize;
    Spinner spin_category_size;
    ListView lv_category_detail;
    ArrayList<HashMap<String,String>> detailarraylist,sizearraylistchoose;
    ArrayList<String>sizearraylist;
    ArrayAdapter<String> spinner_adapter;
    private SwipeRefreshLayout swipe_refresh_layout;
    EditText edt_category_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorydetail_layout);
        lv_category_detail=(ListView)findViewById(R.id.lv_category_detail);
        spin_category_size=(Spinner)findViewById(R.id.spin_category_size);
        strcategorytypename=getIntent().getStringExtra("p_name");
        strcategoryname=getIntent().getStringExtra("tabstring");
        swipe_refresh_layout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        edt_category_size=(EditText)findViewById(R.id.edt_category_size);
        new CategorydetailListAsync().execute();
        swipe_refresh_layout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.GREEN);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                new CategorydetailListAsync().execute();
            }
        });

//spin_category_size.setOnItemSelectedListener(this);

    }

  /*  @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }*/

    //MenuList
    public class CategorydetailListAsync extends AsyncTask<Void,Void,String> {
        ProgressDialog progressDialog;
        String response,p_img1,p_img2,p_img3,p_id,p_name,p_size,p_prize,p_desc;
        HashMap<String,String> hashMap,hashMap1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(CategoryDetailActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();

            detailarraylist=new ArrayList<HashMap<String,String>>();
            sizearraylistchoose=new ArrayList<HashMap<String,String>>();
            sizearraylist=new ArrayList<String>();
            try{
                //response=sh.makeServiceCall(URL.MENS_CATEGORY_URL+ URLEncoder.encode("women"),ServiceHandler.GET);
                response=sh.makeServiceCall(URL.CATEGORY_DETAIL_LIST_URL+ URLEncoder.encode(strcategorytypename)+"&cat="+URLEncoder.encode(strcategoryname),ServiceHandler.GET);
                JSONObject jsonObject=new JSONObject(response);
                JSONArray array=jsonObject.getJSONArray("men");
                for (int i=0;i<array.length();i++){
                    hashMap=new HashMap<String,String>();
                    hashMap1=new HashMap<String,String>();
                    JSONObject jsonObject1=array.getJSONObject(i);
                    p_img1=jsonObject1.getString("p_img1");
                    p_img2=jsonObject1.getString("p_img2");
                    p_img3=jsonObject1.getString("p_img3");
                    p_id=jsonObject1.getString("p_id");
                    p_name=jsonObject1.getString("p_name");
                    p_size=jsonObject1.getString("p_size");
                    p_prize=jsonObject1.getString("p_prize");
                    p_desc=jsonObject1.getString("p_desc");


                    hashMap.put("p_img1",p_img1);
                    hashMap.put("p_img2",p_img2);
                    hashMap.put("p_img3",p_img3);
                    hashMap.put("p_id",p_id);
                    hashMap.put("p_name",p_name);
                    hashMap.put("p_prize",p_prize);
                    hashMap.put("p_desc", p_desc);
                    hashMap.put("p_size", p_size);
                    detailarraylist.add(hashMap);
                    sizearraylistchoose.add(hashMap);
                    sizearraylist.add(p_size);

                }

            }catch (Exception e){

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            swipe_refresh_layout.setRefreshing(false);
            lv_category_detail.setAdapter(new DetailListAdapter());
            //sizearraylist=new ArrayList<String>();
            //spinner adapter
           /* sizearraylist.add("Choose Size");
            spinner_adapter = new ArrayAdapter<String>(CategoryDetailActivity.this,android.R.layout.simple_spinner_item, sizearraylist);
            spin_category_size.setAdapter(spinner_adapter);*/

    edt_category_size.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString().trim();
            if (text.length() > 0) {
                detailarraylist = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < sizearraylistchoose.size(); i++) {
                    if (sizearraylistchoose.get(i).get("p_size").contains(text)) {
                        detailarraylist.add(sizearraylistchoose.get(i));
                    }
                }
            } else {
                detailarraylist = new ArrayList<HashMap<String, String>>();
                detailarraylist.addAll(sizearraylistchoose);
            }
            if (detailarraylist.size() > 0) {
                lv_category_detail.setAdapter(new DetailListAdapter());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryDetailActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setCancelable(false);
                builder.setMessage("Size not found");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_category_size.setText("");
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

            lv_category_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    String strp_img1 = detailarraylist.get(position).get("p_img1");
                    String strp_img2 = detailarraylist.get(position).get("p_img2");
                    String strp_img3 = detailarraylist.get(position).get("p_img3");
                    String strp_id = detailarraylist.get(position).get("p_id");
                    String strp_name = detailarraylist.get(position).get("p_name");
                    String strp_size = detailarraylist.get(position).get("p_size");
                    String strp_prize = detailarraylist.get(position).get("p_prize");
                    String strp_desc = detailarraylist.get(position).get("p_desc");

                    Intent intent = new Intent(CategoryDetailActivity.this, FinalCartActivity.class);
                    intent.putExtra("p_img1", strp_img1);
                    intent.putExtra("p_img2", strp_img2);
                    intent.putExtra("p_img3", strp_img3);
                    intent.putExtra("p_id", strp_id);
                    intent.putExtra("p_name", strp_name);
                    intent.putExtra("p_size", strp_size);
                    intent.putExtra("p_prize", strp_prize);
                    intent.putExtra("p_desc", strp_desc);
                    startActivity(intent);
                }
            });

        }
    }

    public class DetailListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return detailarraylist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                LayoutInflater layoutInflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView=layoutInflater.inflate(R.layout.detail_row, null);
            }
            TextView tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            TextView tv_size=(TextView)convertView.findViewById(R.id.tv_size);
            TextView tv_price=(TextView)convertView.findViewById(R.id.tv_price);
            ImageView img_product=(ImageView)convertView.findViewById(R.id.img_product);


            tv_name.setText(detailarraylist.get(position).get("p_name"));
            tv_size.setText(detailarraylist.get(position).get("p_size"));
            tv_price.setText(detailarraylist.get(position).get("p_prize"));
            Log.v("lastimagearea",""+URL.BASE_URL+"url/"+detailarraylist.get(position).get("p_img1"));
            Log.d("lastimagearea",""+URL.BASE_URL+"url/"+detailarraylist.get(position).get("p_img1"));
            Log.e("lastimagearea",""+URL.BASE_URL+"url/"+detailarraylist.get(position).get("p_img1"));
            Log.i("lastimagearea",""+URL.BASE_URL+"url/"+detailarraylist.get(position).get("p_img1"));
            Picasso.with(getApplicationContext()).load(URL.BASE_URL+"url/"+detailarraylist.get(position).get("p_img1")).into(img_product);
            return convertView;
        }
    }
}

