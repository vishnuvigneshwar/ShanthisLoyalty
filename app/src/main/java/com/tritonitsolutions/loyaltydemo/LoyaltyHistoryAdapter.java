package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tritonitsolutions.layaltydemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 5/10/2015.
 */
public class LoyaltyHistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data_lh_points;
    LayoutInflater inflater;
    public LoyaltyHistoryAdapter(Context context,ArrayList<HashMap<String,String>> data_lh_points){
        this.context=context;
        this.data_lh_points=data_lh_points;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data_lh_points.size();
    }

    @Override
    public Object getItem(int position) {
        return data_lh_points.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data_lh_points.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.loyaltyhistory_row,parent,false);
            holder=new ViewHolder();
            holder.iv=(ImageView)vi.findViewById(R.id.iv_points);
            holder.tv=(TextView)vi.findViewById(R.id.tv_points);
            vi.setTag(holder);

        }else {
            holder=(ViewHolder)vi.getTag();

        }
        HashMap<String,String> history=data_lh_points.get(position);
         int i=Integer.parseInt(history.get(LoyaltyHistoryActivity.LOYALTY_ID));

        if(i==1){
           holder.iv.setImageResource(R.drawable.img_loyalty_point);

            holder.tv.setText("Loyalty Point:" + "\t" + history.get(LoyaltyHistoryActivity.LOYALTY_POINT));

        } if(i==2) {
           holder.iv.setImageResource(R.drawable.img_reedem_point);
            holder.tv.setText("Redeem Point:" + "\t" + history.get(LoyaltyHistoryActivity.LOYALTY_POINT));
        }


        return vi;
    }
    private  class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
