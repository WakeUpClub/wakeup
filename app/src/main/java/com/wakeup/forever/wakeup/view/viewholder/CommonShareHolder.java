package com.wakeup.forever.wakeup.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by forever on 2016/8/29.
 */
public class CommonShareHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.civ_authorImage)
    CircleImageView civAuthorImage;
    @Bind(R.id.tv_authorName)
    TextView tvAuthorName;
    @Bind(R.id.rl_commonShareHeader)
    RelativeLayout rlCommonShareHeader;
    @Bind(R.id.iv_shareViewed)
    ImageView ivShareViewed;
    @Bind(R.id.tv_shareViewedCount)
    TextView tvShareViewedCount;
    @Bind(R.id.iv_shareLiked)
    ImageView ivShareLiked;
    @Bind(R.id.fl_shareComment)
    RelativeLayout flShareComment;
    @Bind(R.id.tv_likeUser)
    TextView tvLikeUser;
    @Bind(R.id.rl_tv_likeUser)
    RelativeLayout rlTvLikeUser;
    @Bind(R.id.rl_baseShare)
    LinearLayout rlBaseShare;
    @Bind(R.id.tv_publishTime)
    TextView tvPublishTime;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_imageDesc)
    ImageView ivImageDesc;
    @Bind(R.id.iv_comment)
    ImageView ivComment;

    public CommonShareHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public CircleImageView getCivAuthorImage() {
        return civAuthorImage;
    }

    public void setCivAuthorImage(CircleImageView civAuthorImage) {
        this.civAuthorImage = civAuthorImage;
    }

    public TextView getTvAuthorName() {
        return tvAuthorName;
    }

    public void setTvAuthorName(TextView tvAuthorName) {
        this.tvAuthorName = tvAuthorName;
    }

    public RelativeLayout getRlCommonShareHeader() {
        return rlCommonShareHeader;
    }

    public void setRlCommonShareHeader(RelativeLayout rlCommonShareHeader) {
        this.rlCommonShareHeader = rlCommonShareHeader;
    }

    public ImageView getIvShareViewed() {
        return ivShareViewed;
    }

    public void setIvShareViewed(ImageView ivShareViewed) {
        this.ivShareViewed = ivShareViewed;
    }

    public TextView getTvShareViewedCount() {
        return tvShareViewedCount;
    }

    public void setTvShareViewedCount(TextView tvShareViewedCount) {
        this.tvShareViewedCount = tvShareViewedCount;
    }

    public ImageView getIvShareLiked() {
        return ivShareLiked;
    }

    public void setIvShareLiked(ImageView ivShareLiked) {
        this.ivShareLiked = ivShareLiked;
    }

    public RelativeLayout getFlShareComment() {
        return flShareComment;
    }

    public void setFlShareComment(RelativeLayout flShareComment) {
        this.flShareComment = flShareComment;
    }

    public TextView getTvLikeUser() {
        return tvLikeUser;
    }

    public void setTvLikeUser(TextView tvLikeUser) {
        this.tvLikeUser = tvLikeUser;
    }

    public RelativeLayout getRlTvLikeUser() {
        return rlTvLikeUser;
    }

    public void setRlTvLikeUser(RelativeLayout rlTvLikeUser) {
        this.rlTvLikeUser = rlTvLikeUser;
    }

    public LinearLayout getRlBaseShare() {
        return rlBaseShare;
    }

    public void setRlBaseShare(LinearLayout rlBaseShare) {
        this.rlBaseShare = rlBaseShare;
    }

    public TextView getTvPublishTime() {
        return tvPublishTime;
    }

    public void setTvPublishTime(TextView tvPublishTime) {
        this.tvPublishTime = tvPublishTime;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public void setTvContent(TextView tvContent) {
        this.tvContent = tvContent;
    }

    public ImageView getIvImageDesc() {
        return ivImageDesc;
    }

    public void setIvImageDesc(ImageView ivImageDesc) {
        this.ivImageDesc = ivImageDesc;
    }

    public ImageView getIvComment() {
        return ivComment;
    }

    public void setIvComment(ImageView ivComment) {
        this.ivComment = ivComment;
    }
}
