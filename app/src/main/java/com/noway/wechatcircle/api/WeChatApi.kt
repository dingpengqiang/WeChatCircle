package com.noway.wechatcircle.api

import com.noway.wechatcircle.model.UserInfo
import com.noway.wechatcircle.model.UserList
import retrofit2.Call
import retrofit2.http.GET

/**
 * 包名:    com.noway.wechatcircle.api
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2018/3/10
 * 版本:    V-1.0.0
 */
interface WeChatApi{

    /**
     * http://thoughtworks-ios.herokuapp.com/user/jsmith
     */
    @GET("user/jsmith")
    fun getUserInfo():Call<UserInfo>

    /**
     * http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets
     */
    @GET("user/jsmith/tweets")
    fun getUserList():Call<List<UserList>>
}