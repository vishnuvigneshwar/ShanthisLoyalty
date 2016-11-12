package com.tritonitsolutions.loyaltydemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

/**
 * Created by TritonDev on 28/9/2015.
 */
public class ShopCategoryActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>>menulist,menucategory;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPager pager=null;
    String tabstring;
    ListView list_category;
    private SwipeRefreshLayout swipe_refresh_layout;
    int count=0;
    Timer timer;
    // TabHost host;
    TextView tv;
    ArrayList<HashMap<String,String>> arraylist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopcategory_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        swipe_refresh_layout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        list_category=(ListView)findViewById(R.id.list_category);

        ViewPagerAdapter adapter1=new ViewPagerAdapter(this);
        pager=(ViewPager)findViewById(R.id.reviewpager);
        pager.setAdapter(adapter1);
        pager.setCurrentItem(0);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        new TabListAsync().execute();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabstring = tab.getText().toString();
                // Toast.makeText(getApplicationContext(),tabstring,Toast.LENGTH_SHORT).show();
                System.out.println(tabstring);
                Log.e("Text", tabstring);
                new MenuListAsync().execute();
                //list_category.setAdapter(new CategoryBaseAdapter());
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new MenuListAsync().execute();

        swipe_refresh_layout.setColorSchemeColors(Color.BLUE,Color.BLUE,Color.GREEN);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                new MenuListAsync().execute();
            }
        });

    }

    public class TabListAsync extends AsyncTask<Void,Void,String>{
        ProgressDialog progressDialog;
        String response;
        HashMap<String,String>hashMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ShopCategoryActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();
            menulist=new ArrayList<HashMap<String,String>>();
            try{
                response=sh.makeServiceCall(URL.SHOP_CATEGORY_TAB,ServiceHandler.GET);
                JSONObject jsonObject=new JSONObject(response);
                JSONArray array=jsonObject.getJSONArray("menu");
                for (int i=0;i<array.length();i++){
                    hashMap=new HashMap<String,String>();
                    JSONObject jsonObject1=array.getJSONObject(i);
                    String p_id=jsonObject1.getString("p_id");
                    String p_cat=jsonObject1.getString("p_cat");

                    hashMap.put("p_id",p_id);
                    hashMap.put("p_cat",p_cat);
                    menulist.add(hashMap);
                }

            }catch (Exception e){

            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            //dhinesh
            if (response==null){
                Toast.makeText(getApplicationContext(),"Server not response",Toast.LENGTH_SHORT).show();
            }else{
                for (int j=0;j<menulist.size();j++){
                    tabLayout.addTab(tabLayout.newTab().setText(menulist.get(j).get("p_cat")));
                }
                // adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                // viewPager.setAdapter(adapter);
                // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        tabstring=tab.getText().toString();
                        // Toast.makeText(getApplicationContext(),tabstring,Toast.LENGTH_SHORT).show();
                        System.out.println(tabstring);
                        //Log.e("Text", tabstring);
                        new MenuListAsync().execute();
                        //list_category.setAdapter(new CategoryBaseAdapter());
                        //viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        }
    }

    //MenuList
    public class MenuListAsync extends AsyncTask<Void,Void,String>{
        ProgressDialog progressDialog;
        String response,p_name;
        HashMap<String,String>hashMap;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ShopCategoryActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ServiceHandler sh=new ServiceHandler();
            menucategory=new ArrayList<HashMap<String,String>>();
            try{
                response=sh.makeServiceCall(URL.MENS_CATEGORY_URL+ URLEncoder.encode(tabstring),ServiceHandler.GET);
                JSONObject jsonObject=new JSONObject(response);
                JSONArray array=jsonObject.getJSONArray("smenu");
                for (int i=0;i<array.length();i++){
                    hashMap=new HashMap<String,String>();
                    JSONObject jsonObject1=array.getJSONObject(i);
                    p_name=jsonObject1.getString("p_name");
                    String pdiscription=jsonObject1.getString("pdiscription");
                    String pimage=jsonObject1.getString("pimage");

                    hashMap.put("p_name",p_name);
                    hashMap.put("pdiscription",pdiscription);
                    hashMap.put("pimage",pimage);
                    menucategory.add(hashMap);
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
            list_category.setAdapter(new CategoryBaseAdapter());
            list_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String productname=menucategory.get(position).get("p_name");

                    //int a=menucategory.size();
                    Intent intent=new Intent(ShopCategoryActivity.this,CategoryDetailActivity.class);
                    intent.putExtra("p_name",productname);
                    intent.putExtra("tabstring",tabstring);

                    startActivity(intent);
                }
            });

        }
    }
    public  class CategoryBaseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menucategory.size();
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
                convertView=layoutInflater.inflate(R.layout.categoryadapter, null);
            }
            TextView tv_mens_category=(TextView)convertView.findViewById(R.id.tv_mens_category);
            TextView tv_mens_pricerange=(TextView)convertView.findViewById(R.id.tv_mens_pricerange);
            ImageView iv_category=(ImageView)convertView.findViewById(R.id.iv_category);


            tv_mens_category.setText(menucategory.get(position).get("p_name"));
            tv_mens_pricerange.setText(menucategory.get(position).get("pdiscription"));
            Log.v("imageurl",""+URL.BASE_URL+"url/"+menucategory.get(position).get("pimage"));
            Log.d("imageurl",""+URL.BASE_URL+menucategory.get(position).get("pimage"));
            Log.e("imageurl",""+URL.BASE_URL+menucategory.get(position).get("pimage"));
            Picasso.with(getApplicationContext()).load(URL.BASE_URL+"url/"+menucategory.get(position).get("pimage")).into(iv_category);

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.badge) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}