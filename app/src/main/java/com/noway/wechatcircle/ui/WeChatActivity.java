package com.noway.wechatcircle.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.noway.wechatcircle.R;
import com.noway.wechatcircle.adapter.WecChatAdapter;
import com.noway.wechatcircle.api.WeChatApi;
import com.noway.wechatcircle.base.BaseActivity;
import com.noway.wechatcircle.model.UserInfo;
import com.noway.wechatcircle.model.UserList;
import com.noway.wechatcircle.net.RetroiftHttp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.OVER_SCROLL_NEVER;



public class WeChatActivity extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    ImageView mIvBg;
    ImageView mIvAvatar;
    TextView mTvUserName;
    private WecChatAdapter mAdapter;
    private int mPage=0;
    private int mSize = 5;
    private ArrayList<UserList> mUserLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_we_chat);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new WecChatAdapter(R.layout.item_wechat, null);
        mRecyclerView.setAdapter(mAdapter);

        View header = LayoutInflater.from(this).inflate(R.layout.header_recycle_item,null);
        mIvBg = header.findViewById(R.id.iv_bg);
        mIvAvatar = header.findViewById(R.id.iv_avatar);
        mTvUserName = header.findViewById(R.id.tv_user_name);
        mAdapter.addHeaderView(header);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getData();
                refreshLayout.finishRefresh();
            }
        });
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(mUserLists,mPage,false);
                mRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void initData() {
        RetroiftHttp.createApi(WeChatApi.class)
                .getUserInfo()
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null){
                                setUserInfo(response.body());
                            }
                        }else {
                            showToast("用户信息请求失败");

                        }

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        showToast(t.getMessage());
                    }
                });
    }

    private void setUserInfo(UserInfo userInfo) {
        Glide.with(this).load(userInfo.getProfileimage()).into(mIvBg);
        Glide.with(this).load(userInfo.getAvatar()).into(mIvAvatar);
        mTvUserName.setText(userInfo.getNick());

        mRefreshLayout.autoRefresh();

    }

    private void getData() {
        RetroiftHttp.createApi(WeChatApi.class)
                .getUserList()
                .enqueue(new Callback<List<UserList>>() {
                    @Override
                    public void onResponse(Call<List<UserList>> call, Response<List<UserList>> response) {
                        if (response.isSuccessful()){
                            if (response.body()!=null){
                                setUserList(response.body());
                            }
                        }else {
                            showToast("列表请求失败");

                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserList>> call, Throwable t) {
                        showToast(t.getMessage());
                    }
                });
    }

    private void setUserList(List<UserList> list) {

        ArrayList<UserList> userLists = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.isEmpty(list.get(i).getError())&& TextUtils.isEmpty(list.get(i).getUnknownError())){
                userLists.add(list.get(i));
            }
        }
        if (mUserLists !=null){
            mUserLists.clear();
        }

        mUserLists = userLists;

        mPage = 0;
        loadData(userLists,mPage,true);
    }

    private void loadData(ArrayList<UserList> userLists,int page,boolean refresh) {
        int i = userLists.size() / mSize;
        if (userLists.size()%mSize ==0){

        }else {
            i+=1;
        }
        if (page < i){
            if (userLists.size()>0){
                if (userLists.size()>(page+1)*mSize){
                    List<UserList> lists = userLists.subList(page * mSize, (page * mSize) + mSize);
                    if (refresh){
                        mAdapter.getData().clear();
                    }
                    mAdapter.addData(lists);

                }else if (userLists.size()>page*mSize){
                    List<UserList> data =  userLists.subList(page * mSize, userLists.size());
                    mAdapter.addData(data);
                }
                mPage+=1;
            }
        }

    }




}
