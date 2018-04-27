package com.application.millipixels.expense_rocket.api;

/**
 * Created by millipixelsinteractive_024 on 26/04/18.
 */
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    /////////////////////Get OTP////////////////////////////

    @FormUrlEncoded
    @POST("sendOTP")
    Call<OTP> sendOtp(@Header("client_id") String clientId,
                             @Header("client_secret") String clientSecret,
                      @Field("mobile") String params);



    //////////////////////Verify OTP////////////////////////


    @FormUrlEncoded
    @POST("validateOTP")
    Call<VerifyOTPResponse> verifyOtp(@Header("client_id") String clientId,
                                      @Header("client_secret") String clientSecret,@Field("otp") String otp,
                      @Field("mobile") String mobileNumber);





}