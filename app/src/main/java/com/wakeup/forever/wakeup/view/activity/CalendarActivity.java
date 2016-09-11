package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import com.dsw.calendar.views.CircleCalendarView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.CalendarActivityPresenter;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LL on 2016/8/28.
 */

@RequiresPresenter(CalendarActivityPresenter.class)
public class CalendarActivity extends BeamBaseActivity<CalendarActivityPresenter> {
    @Bind(R.id.circleMonthView)
    CircleCalendarView circleCalendarView;
    private ArrayList<Calendar> calendarArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_calendar_view);
        ButterKnife.bind(this);
        calendarArrayList = new ArrayList<Calendar>();
        getPresenter().initData();
        initView();
    }

    public void initView() {
        circleCalendarView.setonMonthChangeListener(new CircleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChanged(int year, int month) {
                circleCalendarView.setCalendarInfos(year, month + 2, getSignDays(year, month + 1));
            }
        });
        // circleCalendarView.leftClick();
    }

    public ArrayList<Calendar> getCalendarArrayList() {
        return calendarArrayList;
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public ArrayList<Integer> getSignDays(int year, int month) {
        ArrayList<Integer> days = new ArrayList<Integer>();

        for (Calendar calendar : calendarArrayList) {

            if ((calendar.get(Calendar.YEAR) == year) && (calendar.get(Calendar.MONTH) == month)) {
                days.add(calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
        return days;
    }

    public CircleCalendarView getCircleCalendarView() {
        return circleCalendarView;
    }


}








