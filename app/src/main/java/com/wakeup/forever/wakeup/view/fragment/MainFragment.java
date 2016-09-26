package com.wakeup.forever.wakeup.view.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.http.download.DownloadUtils;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.MainFragmentPresenter;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.CalendarActivity;
import com.wakeup.forever.wakeup.view.activity.PointRankActivity;
import com.wakeup.forever.wakeup.widget.ColorArcProgressBar;

import java.io.File;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;


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
    private TextView tvCount;
    private RadioButton rb_shake;
    private RadioButton rb_calculate;
    private int lastProgress = 0;
    private int shakeCount;

    private  SensorManager sensorManager;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;

    }

    private void initData() {
        sensorManager=(SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        getPresenter().checkUpdate();
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
            showSignChoiceDialog();
            }
        });

        tvRank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PointRankActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showSignCount(int count) {
        cpbSignTime.setCurrentValues(count);
    }

    public void showUpdateDialog(final VersionUpdate versionUpdate) {
        TextView tvTitle = new TextView(getContext());
        TextView tvLogDetail = new TextView(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_version_update)
                .setCancelable(false)
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startDownload(versionUpdate);
                    }
                })
                .create();
        dialog.show();
        tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvLogDetail = (TextView) dialog.findViewById(R.id.tv_logDetail);
        tvTitle.setText("发现新版本:" + versionUpdate.getVersionName());
        tvLogDetail.setText(versionUpdate.getDescription());
    }

    private void startDownload(final VersionUpdate versionUpdate) {
        final NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder builder = new Notification.Builder(getContext());
        builder.setSmallIcon(R.drawable.head)
                .setTicker("showProgressBar")
                .setOngoing(true).setContentTitle("wakeup更新")
                .setContentText("正在下载");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wakeup";
        final File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        final boolean flag = true;
        DownloadUtils.getInstance().download(versionUpdate.getUrl(), dir.getAbsolutePath(), "wakeup.apk", new DownloadUtils.RequestCallBack() {
            @Override
            public void onProgress(long progress, long total, boolean done) {

                int i = (int) (((float) progress / (float) total) * 100);
                builder.setProgress(100, i, false);
                if (i % 5 == 0 && lastProgress != i) {
                    lastProgress = i;
                    builder.setContentText(i+"%");
                    notificationManager.notify(0, builder.build());
                }
                if (progress == total) {
                    builder.setContentTitle("wakeup")
                            .setContentText("下载完成")
                            .setProgress(0, 0, false).setOngoing(false);
                    notificationManager.notify(0, builder.build());
                    File file=new File(dir,"wakeup.apk");
                    startInstall(file);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });

        notificationManager.notify(0, builder.build());
    }

    public void startInstall(File mFile){
        Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(android.content.Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(mFile),"application/vnd.android.package-archive");

        startActivity(install);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            int medumValue = 19;    //摇晃有效值

            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                shakeCount--;
                tvCount.setText(shakeCount+"次");
            }
            if(shakeCount<=0){
                vibrator.vibrate(200);
                getPresenter().signIn();
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    public void showShakeDialog(){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_sign_shanke)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        shakeCount=50;
                    }
                })
                .create();
        dialog.show();
        tvCount= (TextView) dialog.findViewById(R.id.tv_count);

    }

    public void showCalculateDialog(){
        Random random = new Random();
        final int x = random.nextInt(100);
        final int y = random.nextInt(100);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
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
                        try {
                            int result = Integer.parseInt(inputMessage.getText().toString());
                            if (x * y == result) {
                                dialog.dismiss();
                                getPresenter().signIn();
                            } else {
                                ToastUtil.showText("你答错了，亲");
                            }
                        } catch (Exception e) {
                            ToastUtil.showText("请认真填写哦，亲");
                        }

                    }
                }).create();
        dialog.show();
        mathMessage = (TextView) dialog.findViewById(R.id.tv_math);
        inputMessage = (EditText) dialog.findViewById(R.id.input_userMessage);
        mathMessage.setText(x + "*" + y + "=");
    }

    public void showSignChoiceDialog(){

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_sign_choice)
                .setCancelable(false)
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(rb_shake.isChecked()){
                            showShakeDialog();
                        }
                        else{
                            showCalculateDialog();
                        }
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        rb_shake= (RadioButton) dialog.findViewById(R.id.rb_shake);
    }

    @Override
    public void onStart() {
        super.onStart();
        shakeCount=50;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);             // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    public void onShakeSignSuccess(){
        if(tvCount!=null){
            tvCount.setText("签到成功");
        }

    }

    public void onShakeSignFail(){
        if(tvCount!=null){
            tvCount.setText("签到失败");
        }

    }
}
