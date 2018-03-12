package com.noway.wechatcircle.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.noway.wechatcircle.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 包名:    com.noway.wechatcircle.adapter
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2018/3/10
 * 版本:    V-1.0.0
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> data ;
    public PhotoPagerAdapter(Context context,List<String> data) {
        this.context =context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());

        Picasso.with(context)
                .load(data.get(position))
                .placeholder(R.drawable.banner_default)
                .into(photoView);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

