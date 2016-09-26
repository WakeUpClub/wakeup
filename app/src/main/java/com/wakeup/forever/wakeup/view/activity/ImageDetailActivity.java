package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.utils.UiUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageDetailActivity extends AppCompatActivity {


    @Bind(R.id.iv_imageDetail)
    PhotoView ivImageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        UiUtil.immerseStatusBar(this);
        initView();
    }

    private void initView() {
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.head)
                .crossFade()
                .into(ivImageDetail);

        ivImageDetail.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {

            }

            @Override
            public void onOutsidePhotoTap() {
                finish();
            }
        });
    }
}
