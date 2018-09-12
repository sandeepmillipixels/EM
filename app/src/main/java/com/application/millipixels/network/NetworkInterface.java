package com.application.millipixels.network;

import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.models.RegisterResponse;
import com.application.millipixels.models.VerifyOTPResponseRX;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NetworkInterface {




    //////////////////////Registeration////////////////////////

    @FormUrlEncoded
    @POST("registration")
    Call<RegisterResponse> registeration(@Header("client_id") String clientId,
                                        @Header("client_secret") String clientSecret, @Header("device_id") String device_id,@Header("device_token") String device_token, @Header("device_type") String device_type,
                                        @Field("name")String name,@Field("email") String email,@Field("register_type")String register_type,@Field("social_token")String social_token,@Field("mobile") String mobileNumber);


    /////////////////////Get OTP////////////////////////////

    @FormUrlEncoded
    @POST("generate-otp")
    Call<LoginResponse> getOtp(@Header("client_id") String clientId,
                        @Header("client_secret") String clientSecret,
                        @Field("mobile") String params);


    //////////////////////Verify OTP////////////////////////

    @FormUrlEncoded
    @POST("verify-otp")
    Call<VerifyOTPResponseRX> verifyOtp(@Header("client_id") String clientId,
                                              @Header("client_secret") String clientSecret, @Field("otp") String otp,
                                              @Field("mobile") String mobileNumber);




}
