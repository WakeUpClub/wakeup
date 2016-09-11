package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
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
                }
                else{
                    ToastUtil.showText(stringHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText("床君崩溃了，亲");
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
                    ToastUtil.showText(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<ArrayList<Long>> arrayListHttpResult) {
                if(arrayListHttpResult.getResultCode()==200){
                    getView().showSignCount(arrayListHttpResult.getData().size());
                }
            }
        });
    }

}
