package com.application.millipixels.network;

import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.models.VerifyOTPResponseRX;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NetworkInterface {


    /////////////////////Get OTP////////////////////////////

    @FormUrlEncoded
    @POST("sendOTP")
    Call<LoginResponse> getOtp(@Header("client_id") String clientId,
                        @Header("client_secret") String clientSecret,
                        @Field("mobile") String params);


    //////////////////////Verify OTP////////////////////////

    @FormUrlEncoded
    @POST("validateOTP")
    Call<VerifyOTPResponseRX> verifyOtp(@Header("client_id") String clientId,
                                              @Header("client_secret") String clientSecret, @Field("otp") String otp,
                                              @Field("mobile") String mobileNumber);




}
