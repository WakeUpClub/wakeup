package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ShareDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.GetImageUtils;
import com.wakeup.forever.wakeup.utils.ImageUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.PublishCommonShareActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by forever on 2016/8/30.
 */
public class PublishCommonShareActivityPresenter extends BeamBasePresenter<PublishCommonShareActivity> {
    private File tempFile;
    GetImageUtils getImageUtils;
    private Bitmap bitmap;

    public GetImageUtils getGetImageUtils(){
        return getImageUtils;
    }

    @Override
    protected void onCreate(@NonNull PublishCommonShareActivity view, Bundle savedState) {
        super.onCreate(view, savedState);

    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GetImageUtils.PHOTO_CARMERA:
                if (null != data) {
                    bitmap= (Bitmap) data.getExtras().get("data");
                    ImageUtil.saveCommonCompressBitmapFile(bitmap,tempFile);
                    getView().showImage(bitmap);
                }
                break;
            case GetImageUtils.PHOTO_PICK:
                if (null != data) {
                    Uri uri = data.getData();
                    ContentResolver cr = getView().getContentResolver();
                    try {
                        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        ImageUtil.saveCommonCompressBitmapFile(bitmap,tempFile);
                        getView().showImage(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(), e);
                    }
                }
                break;

            default:
                break;
        }
    }
    public  void publishCommonShare() {
        getView().showProgressDialog();
        if(tempFile==null){
            ToastUtil.showText("请先选择图片");
            getView().showSnackBar("请先选择图片");
            return;
        }
        RequestBody image=RequestBody.create(MediaType.parse("multipart/form-data"),tempFile);
        Map<String,Object> queryMap=getView().getAllDate();
        if(queryMap!=null){
            ShareDataManager.getInstance().publishCommonShare(queryMap, image, new BaseSubscriber<HttpResult<ArrayList<String>>>(getView()) {
                @Override
                public void onSuccess(HttpResult<ArrayList<String>> arrayListHttpResult) {
                    getView().dismissProgressDialog();
                    if(arrayListHttpResult.getResultCode()==200){
                        ToastUtil.showText("发送成功");
                        getView().finish();
                    }
                    else{
                        ToastUtil.showText(arrayListHttpResult.getMessage());
                    }
                }

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
                }
            });
        }

    }

    public void initData() {
        tempFile=new File(Environment.getExternalStorageDirectory(), GetImageUtils.getPhotoFileName());
        getImageUtils=new GetImageUtils(getView(),tempFile,getView().getIvAddPicture());
    }

}
