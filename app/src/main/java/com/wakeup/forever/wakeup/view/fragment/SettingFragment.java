package com.wakeup.forever.wakeup.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.view.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    @Bind(R.id.btn_quitLogin)
    Button btnQuitLogin;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_quitLogin)
    public void onClick() {
        PrefUtils.setString(getContext(),GlobalConstant.TOKEN,"");
        startActivity(new Intent(getContext(), LoginActivity.class));
        ActivityManager.finishAll();
    }
}
