package com.wakeup.forever.wakeup.model.service;

import com.squareup.okhttp.RequestBody;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by forever on 2016/8/26.
 */
public interface ShareService {
    @GET("share/getShare.do")
    Observable<HttpResult<ArrayList<Share>>> getShare(@QueryMap Map<String,Object> query);

    @GET("commonShare/getCommonShare.do")
    Observable<HttpResult<ArrayList<CommonShare>>> getCommonShare(@Query("token") String token,@QueryMap Map<String,Object> query);

    @Multipart
    @POST("commonShare/publishCommonShare.do")
    Observable<HttpResult<ArrayList<String>>> publishCommonShare(@QueryMap Map<String,Object> query,@Part("image"+"\";filename=\""+"image.jpg")RequestBody image);
}
