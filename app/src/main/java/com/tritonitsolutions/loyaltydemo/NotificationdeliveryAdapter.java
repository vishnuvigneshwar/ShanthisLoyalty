package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by TritonDev on 23/10/2015.
 */
public class NotificationdeliveryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> notificationdelivery_store;
    public NotificationdeliveryAdapter(Context context, ArrayList<HashMap<String, String>> notificationdelivery_store) {
        this.context=context;
        this.notificationdelivery_store=notificationdelivery_store;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return notificationdelivery_store.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationdelivery_store.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationdelivery_store.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.notificatio_delivery_row,parent,false);
            holder=new ViewHolder();
            holder.invoice=(TextView)vi.findViewById(R.id.tv_pro_invoice);
            holder.total=(TextView)vi.findViewById(R.id.tv_pro_total);
            holder.desc=(TextView)vi.findViewById(R.id.tv_pro_report);
            holder.date=(TextView)vi.findViewById(R.id.tv_delivery_date);
            vi.setTag(holder);
        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> notifi=notificationdelivery_store.get(position);
        holder.invoice.setText(notifi.get(NoticationDelivery.PRO_INVOICE));
        holder.total.setText(notifi.get(NoticationDelivery.PRO_TOTAL));
        holder.desc.setText(notifi.get(NoticationDelivery.PRO_REPORT));
        holder.date.setText(notifi.get(NoticationDelivery.PRO_DATE));
        return vi;
    }
    private class ViewHolder{
        TextView invoice,total,desc,date;

    }
}
