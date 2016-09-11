package com.wakeup.forever.wakeup.model.service;

import com.squareup.okhttp.RequestBody;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.bean.UserPoint;

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
 * Created by forever on 2016/8/19.
 */
public interface UserService {
    @GET("user/register.do")
    Observable<HttpResult<User>> register(@Query("phone") String phone,@Query("password") String password);

    @GET("user/login.do")
    Observable<HttpResult<User>> login(@Query("phone") String phone,@Query("password") String password);

    @GET("user/signIn.do")
    Observable<HttpResult<String>> signIn(@Query("token") String token);

    @GET("user/getSignInfo.do")
    Observable<HttpResult<ArrayList<Long>>> getSignInfo(@Query("token") String token);

    @POST("user/updateUserInfo.do")
    Observable<HttpResult<User>> updateUserInfo(@Query("token") String token, @QueryMap Map<String,Object> user);

    @POST("user/getUserInfo.do")
    Observable<HttpResult<User>> getUserInfo(@Query("token") String token);

    @POST("user/password/updatePassword.do")
    Observable<HttpResult<User>> updatePassword(@Query("phone") String phone,@Query("password") String password);

    @Multipart
    @POST("user/uploadHeadUrl.do")
    Observable<HttpResult<User>> uploadHeadUrl(@Query("token") String token, @Part("headImage"+"\";filename=\""+"image.jpg")RequestBody headImage);

    @POST("user/point/monthPointRank.do")
    Observable<HttpResult<ArrayList<UserPoint>>> monthPointRank(@QueryMap Map<String,Object> queryMap);

    @POST("user/point/allPointRank.do")
    Observable<HttpResult<ArrayList<UserPoint>>> allPointRank(@QueryMap Map<String,Object> queryMap);


}
