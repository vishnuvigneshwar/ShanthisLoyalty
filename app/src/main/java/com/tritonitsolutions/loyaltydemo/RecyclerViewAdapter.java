package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 8/17/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mNavTitles[];
    private int mIcons[];
    private String name;
    private String e_mail;
    private int prof;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;
        TextView textView;
        ImageView imageView;
        ImageView pro;
        TextView Name;
        TextView email;
        Context cn;

        public ViewHolder(View itemView, int ViewType, Context c) {
            super(itemView);
            cn = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            if (ViewType == TYPE_ITEM) {

                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            } else {
                pro = (ImageView) itemView.findViewById(R.id.circleView);
                Name = (TextView) itemView.findViewById(R.id.tv_name);
               // email = (TextView) itemView.findViewById(R.id.tv_email);
                Holderid = 0;
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MainActivity home = (MainActivity)cn;
            home.Drawer.closeDrawers();
            Intent intent;
            switch (position) {
                case 0:
                    break;
                case 1:
                    intent=new Intent(cn,UpdateProfileActivity.class);
                    cn.startActivity(intent);
                    break;
                case 2:
                    intent=new Intent(cn,ShopCategoryActivity.class);
                    cn.startActivity(intent);
                    break;
                case 3:
                    intent=new Intent(cn,ReferAFriend.class);
                    cn.startActivity(intent);
                    break;

                case 4:
                    intent=new Intent(cn,ShopLocator.class);
                    cn.startActivity(intent);
                    break;
                case 5:
                    Intent phoneIntent=new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:" +"04424412111"));
                    cn.startActivity(phoneIntent);;
                    break;
                case 6:
                    intent=new Intent(cn,FeedbackActivity.class);
                    cn.startActivity(intent);
                    break;
                case 7:
                    intent=new Intent(cn,WishListActivity.class);
                    cn.startActivity(intent);
                    break;
                case 8:
                    intent=new Intent(cn,LoginActivity.class);
                    SharedPreferences pref = cn.getSharedPreferences("User-id", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("User_id");
                    editor.commit();
                    cn.startActivity(intent);
                    ((MainActivity) cn).finish();



            }


        }



    }

    RecyclerViewAdapter(String Titles[], int Icons[], String Name, int profile, Context cntx) {
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
       // e_mail = email;
        prof=profile;
        this.context = cntx;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType, context);
            return vhItem;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            ViewHolder vhHeader = new ViewHolder(v, viewType, context);
            return vhHeader;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {

            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        } else {
            holder.Name.setText(name);
         //   holder.email.setText(e_mail);
            holder.pro.setImageResource(prof);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
    }


