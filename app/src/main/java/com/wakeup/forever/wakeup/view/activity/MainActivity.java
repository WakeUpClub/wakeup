package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.MainActivityPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.HomeFragment;
import com.wakeup.forever.wakeup.view.fragment.MainFragment;
import com.wakeup.forever.wakeup.view.fragment.UpdateFragment;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BeamBaseActivity<MainActivityPresenter> {

    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.rb_main)
    RadioButton rbMain;
    @Bind(R.id.rb_update)
    RadioButton rbUpdate;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rg_menuList)
    RadioGroup rgMenuList;
    @Bind(R.id.fl_main)
    FrameLayout flMain;

    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private HomeFragment homeFragment;
    private UpdateFragment updateFragment;

    private long lastPressBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.e("onCreate");
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager=getSupportFragmentManager();
        initView();
    }

    public void initView(){
        LogUtil.e("initView");
        //初始化fragment
        mainFragment=new MainFragment();
        updateFragment=new UpdateFragment();
        homeFragment=new HomeFragment();

        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.fl_main,mainFragment);
        transaction.add(R.id.fl_main,updateFragment);
        transaction.add(R.id.fl_main,homeFragment);
        hideAll(transaction);
        transaction.show(mainFragment);
        transaction.commit();

        rgMenuList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                hideAll(transaction);
                if(checkedId==rbMain.getId()){
                    transaction.show(mainFragment);
                }
                else if(checkedId==rbHome.getId()){
                    transaction.show(homeFragment);
                }
                else{
                    transaction.show(updateFragment);
                }
                transaction.commit();
            }
        });
    }

    public void hideAll(FragmentTransaction transaction){
        transaction.hide(mainFragment);
        transaction.hide(updateFragment);
        transaction.hide(homeFragment);
    }

    @Override
    public void onBackPressed() {
        long pressTime = new Date().getTime() / 1000;
        if (pressTime - lastPressBackTime < 2) {
            super.onBackPressed();
        } else {
            lastPressBackTime = pressTime;
            ToastUtil.showText("再点一次退出");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        LogUtil.e(fragment.toString());
        if(mainFragment==null&&fragment instanceof MainFragment){
            mainFragment= (MainFragment) fragment;
        }
        else if(updateFragment==null&&fragment instanceof UpdateFragment){
            updateFragment= (UpdateFragment) fragment;
        }
        else if(homeFragment==null&&fragment instanceof HomeFragment){
            homeFragment= (HomeFragment) fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
       // super.onSaveInstanceState(outState);
        LogUtil.e("onSaveInstanceState");
    }
}
