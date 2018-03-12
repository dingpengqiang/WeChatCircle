package com.noway.wechatcircle.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.ItemImageLongClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.noway.wechatcircle.R;
import com.noway.wechatcircle.model.UserList;
import com.noway.wechatcircle.ui.ImageDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
public class WecChatAdapter extends BaseQuickAdapter<UserList,BaseViewHolder> {
    public WecChatAdapter(int layoutResId, @Nullable List<UserList> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, final UserList item) {

        //昵称
        helper.setText(R.id.item_tv_name,item.getSender().getNick());

        //头像
        ImageView ivAvatar = helper.getView(R.id.item_iv_avatar);
        Glide.with(mContext).load(item.getSender().getAvatar()).into(ivAvatar);

        //添加文本信息
        if (!TextUtils.isEmpty(item.getContent())){
            helper.setText(R.id.item_tv_content,item.getContent());
            helper.getView(R.id.item_tv_content).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.item_tv_content).setVisibility(View.GONE);
        }

        //添加九宫格照片
        NineGridImageView<String> nineGridImageView = helper.getView(R.id.item_nine);
        nineGridImageView.setAdapter(mAdapter);
        //九宫格照片点击事件
        nineGridImageView.setItemImageClickListener(new ItemImageClickListener<String>() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
//                Log.d("onItemImageClick", list.get(index));
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putStringArrayListExtra("list", (ArrayList<String>) list);
                intent.putExtra("index",index);
                mContext.startActivity(intent);
            }
        });
        nineGridImageView.setItemImageLongClickListener(new ItemImageLongClickListener<String>() {
            @Override
            public boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<String> list) {
                Log.d("onItemImageLongClick", list.get(index));
                return true;
            }
        });
        if (item.getImages()!=null){

            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < item.getImages().size(); i++) {
                strings.add(item.getImages().get(i).getUrl());
            }
            nineGridImageView.setImagesData(strings);
        }else {
            nineGridImageView.setImagesData(null);
        }

        //评论列表
        LinearLayout layout = helper.getView(R.id.item_text_ll);
        if (item.getComments()!=null){
            layout.setVisibility(View.VISIBLE);
            layout.removeAllViews();
            for (int i = 0; i < item.getComments().size(); i++) {
                TextView textView = new TextView(mContext);
                textView.setText(getSpannable(item.getComments().get(i).getSender().getUsername()+":")
                        + item.getComments().get(i).getContent());
                layout.addView(textView);
            }
        }else {
            layout.setVisibility(View.GONE);
        }
        final LinearLayout rootLayout = helper.getView(R.id.item_ll);
        TextView tvComment = helper.getView(R.id.item_tv_comment);
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    private String getSpannable(String text){
        Spannable span = new SpannableString(text);


        span.setSpan( new ForegroundColorSpan(Color.BLUE), 0, text.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span.toString();
    }

    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {


        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            Picasso.with(context).load(s).placeholder(R.drawable.banner_default).into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
            Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected boolean onItemImageLongClick(Context context, ImageView imageView, int index, List<String> list) {
            Toast.makeText(context, "image long click position is " + index, Toast.LENGTH_SHORT).show();
            return true;
        }
    };




}
