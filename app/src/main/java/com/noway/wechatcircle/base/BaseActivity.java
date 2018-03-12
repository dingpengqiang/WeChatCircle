package com.noway.wechatcircle.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * 包名:    com.noway.wechatcircle.base
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2018/3/10
 * 版本:    V-1.0.0
 */
public abstract class BaseActivity extends AppCompatActivity{

    /**当前Activity的弱引用，防止内存泄露**/
    private WeakReference<Activity> context = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initThis();
        initTitle();
        initPresenter();
        initView();
        initData();
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);

    }

    private void initThis() {
        //将当前Activity压入栈
        context = new WeakReference<Activity>(this);

    }
    protected WeakReference<Activity> getWContext(){
        return context;
    }

    /**
     * 吐司
     * @param msg 消息
     */
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化P层
     */
    protected abstract void initPresenter();
    /**
     * 初始化视图
     */
    protected abstract  void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();


}
