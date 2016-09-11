package com.wakeup.forever.wakeup.presenter.activityPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.CalendarActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by forever on 2016/9/1.
 */
public class CalendarActivityPresenter extends BeamBasePresenter<CalendarActivity> {
    public void initData() {
        getView().showProgressDialog();
        UserDataManager.getInstance().getSignInfo(new BaseSubscriber<HttpResult<ArrayList<Long>>>(getView()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<Long>> arrayListHttpResult) {
                if (arrayListHttpResult.getResultCode() == 200)
                    for (Long day : arrayListHttpResult.getData()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(day);
                        getView().getCalendarArrayList().add(calendar);
                        Date date = new Date();
                        date.setTime(calendar.getTimeInMillis());
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        getView().getCircleCalendarView().setCalendarInfos(year, month + 2, getView().getSignDays(year, month + 1));
                    }
                else {
                    ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
                }
                getView().dismissProgressDialog();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
                getView().dismissProgressDialog();
                ToastUtil.showText("网络出了点问题呢");
            }
        });
    }
}
