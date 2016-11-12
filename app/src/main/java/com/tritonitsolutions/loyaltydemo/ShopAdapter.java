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
 * Created by TritonDev on 9/5/2015.
 */
public class ShopAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> store_data;
   LayoutInflater inflater;
    Picasso mPicasso;
   // ImageLoader loader;
    public ShopAdapter(Context context, ArrayList<HashMap<String, String>> store_data){
        this.context=context;
        this.store_data=store_data;
       // loader=new ImageLoader(context);
         mPicasso = Picasso.with(context);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return store_data.size();
    }

    @Override
    public Object getItem(int position) {
        return store_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return store_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder=null;

        if(convertView==null){
            vi=inflater.inflate(R.layout.store_row,parent,false);
            holder=new ViewHolder();
            holder.st_name=(TextView)vi.findViewById(R.id.tv_store_name);
            holder.st_image=(ImageView)vi.findViewById(R.id.iv_store_image);
            vi.setTag(holder);

        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> datas=store_data.get(position);
        holder.st_name.setText(datas.get(ShopActivity.TAG_STORE_NAME));
        Picasso.with(context).load(datas.get(ShopActivity.TAG_STORE_IMAGE)).into(holder.st_image);

       //loader.DisplayImage(datas.get(ShopActivity.TAG_STORE_IMAGE), holder.st_image);
        return vi;
    }
private class ViewHolder{
    TextView st_name;
    ImageView st_image;

}
}
