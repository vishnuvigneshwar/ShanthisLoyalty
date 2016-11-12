package com.tritonitsolutions.loyaltydemo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tritonitsolutions.Util.URL;
import com.tritonitsolutions.layaltydemo.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 9/8/2015.
 */
public class NewArrivalActivity extends ActionBarActivity {
    Toolbar toolbar;
    public static final String NEW_ARRIVAL="new_arraival";
    public static final String NEW_ARRIVAL_NAME="p_name";
    public static final String NEW_ARRIVAL_IMAGE="p_img";
    ArrayList<HashMap<String,String>> new_arrival_list;
    JSONArray new_arrival=null;
    ListView lv;
    NewArrivalAdapter newArrivalAdapter;
    private SwipeRefreshLayout refreshLayout;
    RelativeLayout  relativelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_arrival_layout);
        lv=(ListView)findViewById(R.id.lv_new_arrival);
        new_arrival_list=new ArrayList<HashMap<String, String>>();
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout_arrival);
        relativelist=(RelativeLayout)findViewById(R.id.relativelist);
        new loadNewArrivalData().execute();
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                new_arrival_list.clear();
                new loadNewArrivalData().execute();

            }
        });


    }
    private class loadNewArrivalData extends AsyncTask<String,Void,Void>{
        String jsonValue;

        @Override
        protected Void doInBackground(String... params) {
            try {
                ServiceHandler handler=new ServiceHandler();
                jsonValue=handler.makeServiceCall(URL.NEW_ARRIVAL_URL,ServiceHandler.GET);
            }catch (Exception e){
                e.printStackTrace();
            }

            if(jsonValue != null){
                try {
                    JSONObject object=new JSONObject(jsonValue);
                    new_arrival=object.getJSONArray(NEW_ARRIVAL);
                    for (int i=0;i<new_arrival.length();i++){
                        JSONObject jsonObject=new_arrival.getJSONObject(i);
                        String arrival_name=jsonObject.getString(NEW_ARRIVAL_NAME);
                        String arrival_img=jsonObject.getString(NEW_ARRIVAL_IMAGE);
                        System.out.println("images------->"+arrival_img);

                        HashMap<String,String> arrival=new HashMap<String,String>();
                        arrival.put(NEW_ARRIVAL_NAME,arrival_name);
                        arrival.put(NEW_ARRIVAL_IMAGE,arrival_img);
                        new_arrival_list.add(arrival);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            return null;
        }
        protected  void onPostExecute(Void result){

            if (new_arrival_list.size()>0){
                lv.setVisibility(View.VISIBLE);
            newArrivalAdapter=new NewArrivalAdapter(NewArrivalActivity.this,new_arrival_list);
            lv.setAdapter(newArrivalAdapter);
            newArrivalAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
            }
            else{
                relativelist.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
            }
            super.onPostExecute(result);
        }
    }
}
