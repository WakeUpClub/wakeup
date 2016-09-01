package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.model.DataManager.ShareDataManager;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.CommonShareFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by forever on 2016/8/26.
 */
public class CommonShareFragmentPresenter extends BeamBasePresenter<CommonShareFragment> {

    public void refreshData(){
        Map<String,Object> requestMap=new HashMap<String, Object>();
        requestMap.put("limit",5);
        ShareDataManager.getInstance().getCommonShare(requestMap, new BaseSubscriber<HttpResult<ArrayList<CommonShare>>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<CommonShare>> arrayListHttpResult) {
                ArrayList<CommonShare> commonShares=arrayListHttpResult.getData();
                getView().getCommonShareList().clear();
                getView().getCommonShareList().addAll(commonShares);
                getView().refreshData();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage()+"納尼");
            }
        });
    }

    public void loadMore(){
        Map<String,Object> requestMap=new HashMap<String, Object>();
        requestMap.put("limit",5);
        requestMap.put("offset",getView().getCommonShareList().size());
        ShareDataManager.getInstance().getCommonShare(requestMap, new BaseSubscriber<HttpResult<ArrayList<CommonShare>>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<CommonShare>> arrayListHttpResult) {
                ArrayList<CommonShare> commonShares=arrayListHttpResult.getData();
                if(commonShares.size()==0){
                    ToastUtil.showText("没有更多了，亲");
                    return;
                }
                getView().getCommonShareList().addAll(commonShares);
                getView().refreshData();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage()+"納尼");
            }
        });
    }
}
