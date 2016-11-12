package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tritonitsolutions.layaltydemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TritonDev on 26/9/2015.
 */
public class CustomerHistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> customer_history_data;
    LayoutInflater inflater;
    public CustomerHistoryAdapter(Context context,ArrayList<HashMap<String,String>> customer_history_data){
        this.context=context;
        this.customer_history_data=customer_history_data;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return customer_history_data.size();
    }

    @Override
    public Object getItem(int position) {
        return customer_history_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customer_history_data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
            ViewHolder holder=null;
        if(convertView==null){
            vi=inflater.inflate(R.layout.customerhisory_row,parent,false);
            holder=new ViewHolder();
            holder.pro_id=(TextView)vi.findViewById(R.id.tv_pro_id);
            holder.invoice_no=(TextView)vi.findViewById(R.id.tv_invoice_no);
            holder.sales_date=(TextView)vi.findViewById(R.id.tv_sales_date);
            holder.qty=(TextView)vi.findViewById(R.id.tv_qty);
            holder.total_amt=(TextView)vi.findViewById(R.id.tv_total_amt);
            vi.setTag(holder);
        }else {
            holder=(ViewHolder)vi.getTag();
        }
        HashMap<String,String> ch=customer_history_data.get(position);
        holder.pro_id.setText(ch.get(CustomerHistoryActivity.CH_PROD_NAME));
        holder.invoice_no.setText(ch.get(CustomerHistoryActivity.CH_INVOICE_NO));
        holder.sales_date.setText(ch.get(CustomerHistoryActivity.CH_SALES_DATE));
        holder.qty.setText(ch.get(CustomerHistoryActivity.CH_QUANTITY));
        holder.total_amt.setText(ch.get(CustomerHistoryActivity.CH_TOTAL));

        return vi;
    }
    private class ViewHolder{
        TextView pro_id,invoice_no,sales_date,qty,total_amt;
    }
}
