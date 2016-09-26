package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.content.pm.PackageManager;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.DataManager.VersionDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.MainFragment;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/17.
 */
public class MainFragmentPresenter extends BeamBasePresenter<MainFragment>{


    public void signIn(){
        UserDataManager.getInstance().signIn(new BaseSubscriber<HttpResult<String>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<String> stringHttpResult) {
                if(stringHttpResult.getResultCode()==200){
                    ToastUtil.showText("签到成功");
                    getView().onShakeSignSuccess();
                }
                else{
                    getView().onShakeSignFail();
                    ToastUtil.showText(stringHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onShakeSignFail();
                ToastUtil.showText("请检查网络，亲");
            }
        });
    }

    public void initSignCount(){
        UserDataManager.getInstance().getSignCount(new Subscriber<HttpResult<ArrayList<Long>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                   // ToastUtil.showText(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<ArrayList<Long>> arrayListHttpResult) {
                if(arrayListHttpResult.getResultCode()==200){
                    getView().showSignCount(arrayListHttpResult.getData().size());
                }
            }
        });
    }

    public void checkUpdate(){
        try {
            final int versionCode=getView().getActivity().getPackageManager().getPackageInfo(getView().getActivity().getPackageName(), 0).versionCode;
            VersionDataManager.getInstance().getNewVersion(new Subscriber<HttpResult<VersionUpdate>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HttpResult<VersionUpdate> versionUpdateHttpResult) {
                    if(versionUpdateHttpResult.getResultCode()==200){
                        if(versionUpdateHttpResult.getData().getVersionCode()>versionCode){
                            getView().showUpdateDialog(versionUpdateHttpResult.getData());
                        }
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {

        }


    }

}
