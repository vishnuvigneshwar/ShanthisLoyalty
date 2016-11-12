package com.tritonitsolutions.loyaltydemo;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 8/24/2015.
 */
public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    int[]images={R.drawable.img_shirt};
    View view;
    LayoutInflater inflater;
    public ViewPagerAdapter(Context context) {
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return images.length;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        view=inflater.inflate(R.layout.pager_layout,container,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.iv_autoslide);
        imageView.setImageResource(images[position]);
        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==((View) object);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }

}
