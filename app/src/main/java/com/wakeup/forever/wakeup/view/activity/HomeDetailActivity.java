package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.HomeDetailActivityPresenter;
import com.wakeup.forever.wakeup.view.fragment.UserCenterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(HomeDetailActivityPresenter.class)
public class HomeDetailActivity extends BeamBaseActivity<HomeDetailActivityPresenter> {

    @Bind(R.id.fl_homeDetail)
    FrameLayout flHomeDetail;

    public static final int USER_CENTER=1;
    public  static final int SHARE_DETAIL=2;

    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        ButterKnife.bind(this);
        initView();
        int flag=getIntent().getIntExtra(GlobalConstant.FLAG,USER_CENTER);
        changeFragment(flag);
    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
    }

    public void changeFragment(int flag){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        switch (flag){
            case USER_CENTER:
                fragmentTransaction.replace(R.id.fl_homeDetail,new UserCenterFragment());
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }


}
