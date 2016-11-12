package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tritonitsolutions.layaltydemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 16/9/2015.
 */
public class NewArrivalAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> new_arrival_data;
    LayoutInflater inflater;
   // ImageLoader imageLoader;
    Picasso picasso;
    public NewArrivalAdapter(Context context,ArrayList<HashMap<String,String>> new_arrival_data){
        this.context=context;
        this.new_arrival_data=new_arrival_data;
       // imageLoader=new ImageLoader(context);
        picasso=Picasso.with(context);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return new_arrival_data.size();
    }

    @Override
    public Object getItem(int position) {
        return new_arrival_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return new_arrival_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.new_arrival_row,parent,false);
            holder=new ViewHolder();
            holder.tv=(TextView)vi.findViewById(R.id.tv_product_name);
            holder.img=(ImageView)vi.findViewById(R.id.iv_new_arrival);
            vi.setTag(holder);
        }
        else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> new_arr=new_arrival_data.get(position);
        holder.tv.setText(new_arr.get(NewArrivalActivity.NEW_ARRIVAL_NAME));
        Picasso.with(context).load(new_arr.get(NewArrivalActivity.NEW_ARRIVAL_IMAGE)).into(holder.img);
        System.out.println("img------->"+new_arr.get(NewArrivalActivity.NEW_ARRIVAL_IMAGE));
      //  imageLoader.DisplayImage(new_arr.get(NewArrivalActivity.NEW_ARRIVAL_IMAGE),holder.img);
        return vi;
    }
    private class ViewHolder{
        ImageView img;
        TextView tv;
    }
}
