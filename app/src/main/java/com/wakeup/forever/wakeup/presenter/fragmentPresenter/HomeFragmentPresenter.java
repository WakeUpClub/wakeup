package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.view.fragment.HomeFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by forever on 2016/8/17.
 */

public class HomeFragmentPresenter extends BeamBasePresenter<HomeFragment> {

    public void initData() {
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        if(token.isEmpty()){
            return;
        }
        User userTemp = UserCacheManager.getUser();
        if(userTemp!=null){
            getView().showUserInfo(userTemp);
        }
        List<User> users=DataSupport.findAll(User.class);
        UserDataManager.getInstance().getUserInfo(new BaseSubscriber<HttpResult<User>>(getView().getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if (userHttpResult.getResultCode() == 200) {
                    User user=userHttpResult.getData();
                    getView().showUserInfo(user);
                    getView().setHeadClickable(false);
                    UserCacheManager.saveUser(userHttpResult.getData());
                } else {
                    getView().showSnackBar(GlobalConstant.ERROR_MESSAGE);
                    getView().setHeadClickable(true);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().setHeadClickable(true);
                getView().showSnackBar(GlobalConstant.ERROR_MESSAGE);
            }
        });
    }
}
