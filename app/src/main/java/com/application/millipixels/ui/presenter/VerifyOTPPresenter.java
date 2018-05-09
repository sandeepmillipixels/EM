package com.application.millipixels.ui.presenter;


import android.content.Context;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.models.VerifyOTPResponseRX;
import com.application.millipixels.network.NetworkClient;
import com.application.millipixels.network.NetworkInterface;
import com.application.millipixels.ui.presenterinterface.VerifyPresenterinterface;
import com.application.millipixels.ui.view.VerifyViewinterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VerifyOTPPresenter implements VerifyPresenterinterface {

    String mobileNumber;
    String otp;

    Context context;

    VerifyViewinterface verifyViewinterface;

    public VerifyOTPPresenter(VerifyViewinterface verifyViewinterface,String mobileNumber,String otp){

        this.mobileNumber=mobileNumber;
        this.otp=otp;
        this.verifyViewinterface=verifyViewinterface;

    }

    @Override
    public void getUserData(Context context) {
        this.context=context;
        observable().subscribeWith(observer());

    }


    public Observable<VerifyOTPResponseRX> observable(){

        return NetworkClient.getRetrofit().create(NetworkInterface.class).verifyOtp(context.getResources().getString(R.string.message_client_id),context.getResources().getString(R.string.message_secret_key),otp,mobileNumber).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<VerifyOTPResponseRX> observer(){

        return new DisposableObserver<VerifyOTPResponseRX>(){

            @Override
            public void onNext(VerifyOTPResponseRX verifyOTPResponseRX) {

                verifyViewinterface.getUserData(verifyOTPResponseRX);

            }

            @Override
            public void onError(Throwable e) {


                verifyViewinterface.displayError(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }


}
