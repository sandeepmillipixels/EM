package com.application.millipixels.expense_rocket.api;

/**
 * Created by millipixelsinteractive_024 on 26/04/18.
 */
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("/mobile/otp")
    @FormUrlEncoded
    Call<OTP> sendOtp(@Field("client_id") String clientId,
                             @Field("client_secret") String clientSecret,
                             @Field("mobile") String mobile);

}