package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
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
 * Created by TritonDev on 1/10/2015.
 */
public class NotificationOfferAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data_notification;
    LayoutInflater inflater;
    public NotificationOfferAdapter(Context context, ArrayList<HashMap<String, String>> data_notification){
        this.context=context;
        this.data_notification=data_notification;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data_notification.size();
    }

    @Override
    public Object getItem(int position) {
        return data_notification.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data_notification.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.notification_offer_row,parent,false);
            holder=new ViewHolder();
            holder.iv=(ImageView)vi.findViewById(R.id.img_notification);
            holder.title=(TextView)vi.findViewById(R.id.tv_title);
            holder.description=(TextView)vi.findViewById(R.id.tv_desc);
           // holder.coupon_code=(TextView)vi.findViewById(R.id.tv_code);
            holder.expire_date=(TextView)vi.findViewById(R.id.tv_exp_date);
            vi.setTag(holder);
        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> notifi=data_notification.get(position);
        holder.title.setText(notifi.get(NotificationOffer.TITLE));
        holder.description.setText(notifi.get(NotificationOffer.DESCRIPTION));
        //holder.coupon_code.setText(notifi.get(NotificationOffer.COUPON_CODE));
        holder.expire_date.setText(notifi.get(NotificationOffer.EXPIRE_DATE));
       // int i=Integer.parseInt(notifi.get(NotificationOffer.NOTIFICATION_ID));

//        if(i==1){
//            holder.iv.setImageResource(R.drawable.notification_green_icon);
//
//        } else {
//            holder.iv.setImageResource(R.drawable.notification_red_icon);
//        }

        return vi;
    }
    private class ViewHolder{
        TextView title,description,coupon_code,expire_date;

        ImageView iv;
    }
}

