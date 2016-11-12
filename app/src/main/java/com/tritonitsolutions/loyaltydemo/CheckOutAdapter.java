package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.datamodel.CheckOutData;
import com.tritonitsolutions.layaltydemo.R;

import java.util.List;

/**
 * Created by TritonDev on 15/10/2015.
 */
public class CheckOutAdapter extends BaseAdapter {
    Context context;
    List<CheckOutData> list_data;
    LayoutInflater inflater;
    public CheckOutAdapter(Context context,List<CheckOutData> list_data){
        this.context=context;
        this.list_data=list_data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return list_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder=null;
        if(convertView==null){
            v=inflater.inflate(R.layout.checkout_row,parent,false);
            holder=new ViewHolder();
            holder.img=(ImageView)v.findViewById(R.id.iv_co_img);
            holder.names=(TextView)v.findViewById(R.id.tv_co_name);
            holder.sizes=(TextView)v.findViewById(R.id.tv_co_size);
            //holder.price=(TextView)v.findViewById(R.id.tv_co_price);
            holder.count=(TextView)v.findViewById(R.id.tv_co_count);
            holder.total=(TextView)v.findViewById(R.id.tv_co_total);
            v.setTag(holder);
        } else {
            holder=(ViewHolder)v.getTag();
        }
        Picasso.with(context).load(list_data.get(position).getPro_img()).into(holder.img);
        holder.names.setText(list_data.get(position).getPro_name());
        holder.sizes.setText(list_data.get(position).getPro_size());
      //  holder.price.setText(list_data.get(position).getPro_price());
        holder.count.setText(list_data.get(position).getPro_count());
        holder.total.setText(list_data.get(position).getPro_total());

        return v;
    }
    private class ViewHolder{
        ImageView img;
        TextView names,sizes,price,count,total;
    }
}
