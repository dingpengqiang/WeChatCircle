package com.noway.wechatcircle.ui

import android.os.Bundle
import com.noway.wechatcircle.R
import com.noway.wechatcircle.adapter.PhotoPagerAdapter
import com.noway.wechatcircle.base.BaseActivity
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_image_detail)
        super.onCreate(savedInstanceState)
    }

    override fun initTitle() {

    }

    override fun initPresenter() {

    }

    override fun initView() {
        var intent = intent
        var index = intent.getIntExtra("index",0)
        var list = intent.getStringArrayListExtra("list")
        viewpager.adapter = PhotoPagerAdapter(this,list)
        viewpager.currentItem = index
    }

    override fun initData() {

    }



}
