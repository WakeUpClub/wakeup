package com.wakeup.forever.wakeup.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.MainFragmentPresenter;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.CalendarActivity;
import com.wakeup.forever.wakeup.view.activity.PointRankActivity;
import com.wakeup.forever.wakeup.widget.ColorArcProgressBar;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


@RequiresPresenter(MainFragmentPresenter.class)
public class MainFragment extends BeamFragment<MainFragmentPresenter> {

    @Bind(R.id.btn_calendar)
    Button btnCalendar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ib_share)
    ImageButton ibShare;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tv_rank)
    TextView tvRank;
    @Bind(R.id.btn_sign)
    Button btnSign;
    @Bind(R.id.cpb_signTime)
    ColorArcProgressBar cpbSignTime;

    private EditText inputMessage;
    private TextView mathMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;

    }

    private void initData() {
        getPresenter().initSignCount();
        btnCalendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                final int x=random.nextInt(100);
                final int y=random.nextInt(100);
                AlertDialog dialog= new  AlertDialog.Builder(getContext())
                        .setView(R.layout.dialog_calculate)
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    int result=Integer.parseInt(inputMessage.getText().toString());
                                    if(x*y==result){
                                        dialog.dismiss();
                                        getPresenter().signIn();
                                    }
                                    else{
                                        ToastUtil.showText("你答错了，亲");
                                    }
                                }
                                catch (Exception e){
                                    ToastUtil.showText("请认真填写哦，亲");
                                }

                            }
                        }).create();
                dialog.show();
                mathMessage= (TextView) dialog.findViewById(R.id.tv_math);
                inputMessage= (EditText) dialog.findViewById(R.id.input_userMessage);
                mathMessage.setText(x+"*"+y+"=");
            }
        });

        tvRank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), PointRankActivity.class);
                startActivity(i);
            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showSignCount(int count){
        cpbSignTime.setCurrentValues(count);
    }


}
