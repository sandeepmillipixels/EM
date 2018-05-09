package com.application.millipixels.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.application.millipixels.expense_rocket.R;
import com.application.millipixels.models.LoginResponse;
import com.application.millipixels.network.NetworkClient;
import com.application.millipixels.network.NetworkInterface;
import com.application.millipixels.ui.presenterinterface.LoginPresenterInterface;
import com.application.millipixels.ui.view.LoginViewInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginPresenterInterface {

    LoginViewInterface loginViewInterface;
    String mobileNumber;

    Context context;


    public LoginPresenter(LoginViewInterface loginViewInterface, String mobileNumber){

        this.loginViewInterface=loginViewInterface;
        this.mobileNumber=mobileNumber;



    }


    @Override
    public void getData(Context context) {
        this.context=context;
        getObservable().subscribeWith(getObserver());
    }



    public Observable<LoginResponse> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterface.class).getOtp(context.getResources().getString(R.string.message_client_id),context.getResources().getString(R.string.message_secret_key) ,"+91"+mobileNumber).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<LoginResponse>getObserver(){
        return new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse response) {

                Log.d("Call On Next","OnNext"+response.getData());

                loginViewInterface.getOTP(response);
            }

            @Override
            public void onError(Throwable e) {


                loginViewInterface.displayError(e.getLocalizedMessage());

                Log.d("Call On Error","Error");

            }

            @Override
            public void onComplete() {
                Log.d("Call On Complete","Complete");


            }
        };
    }


}
