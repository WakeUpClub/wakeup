package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.ShareDetailActivityPresenter;

@RequiresPresenter(ShareDetailActivityPresenter.class)
public class ShareDetailActivity extends BeamBaseActivity<ShareDetailActivityPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
