package com.wakeup.forever.wakeup.presenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.CommonShareLike;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.view.viewholder.CommonShareHolder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by forever on 2016/8/29.
 */
public class CommonShareAdapter extends RecyclerView.Adapter<CommonShareHolder> {

    private ArrayList<CommonShare> commonShareList;
    private Context context;
    private CommonShareHolder commonShareHolder;

    public CommonShareAdapter(Context context,ArrayList<CommonShare> commonShareList){
        this.context=context;
        this.commonShareList=commonShareList;
    }
    @Override
    public CommonShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseCommonShareItem= LayoutInflater.from(context).inflate(R.layout.layout_common_share_item,null);
        commonShareHolder=new CommonShareHolder(baseCommonShareItem);
        return commonShareHolder;
    }

    @Override
    public void onBindViewHolder(CommonShareHolder holder, int position) {
        CommonShare commonShare=commonShareList.get(position);
        Glide.with(context)
                .load(commonShare.getUser().getHeadURL())
                .error(R.drawable.head)
                .crossFade()
                .into(holder.getCivAuthorImage());
        holder.getTvAuthorName().setText(commonShare.getUser().getName());
        holder.getTvPublishTime().setText(new Date(commonShare.getPublishTime()).toLocaleString());
        holder.getTvContent().setText(commonShare.getContent());
        Glide.with(context)
                .load(commonShare.getImageDesc())
                .error(R.drawable.splash01)
                .crossFade()
                .into(holder.getIvImageDesc());
        holder.getTvShareViewedCount().setText(commonShare.getViewCount()+"");
        StringBuffer likeUser=new StringBuffer("　　　");
        LogUtil.e(likeUser.length()+"哪了");
        for(CommonShareLike commonShareLike:commonShare.getLikedList()){
            likeUser.append(commonShareLike.getUserName()+",");
            LogUtil.e(commonShareLike.getUserName());
        }
        likeUser.replace(likeUser.length()-1,likeUser.length()-1,"等人覺得很贊");
        holder.getTvLikeUser().setText(likeUser);
    }

    @Override
    public int getItemCount() {
        return commonShareList.size();
    }
}
