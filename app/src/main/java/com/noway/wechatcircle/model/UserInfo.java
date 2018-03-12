package com.noway.wechatcircle.model;

import com.google.gson.annotations.SerializedName;

/**
 * 包名:    com.noway.wechatcircle.model
 * 标题:
 * 描述:    TODO
 * 作者:    NoWay
 * 邮箱:    dingpengqiang@qq.com
 * 日期:    2018/3/10
 * 版本:    V-1.0.0
 */
public class UserInfo {


    @SerializedName("profile-image")
    private String profileimage;
    private String avatar;
    private String nick;
    private String username;


    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
