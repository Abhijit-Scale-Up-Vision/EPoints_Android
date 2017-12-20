package com.iat.epoints.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iat.epoints.http.api.RTokenAPI;
import com.iat.epoints.http.apimodel.SignInResult;
import com.iat.epoints.root.App;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manikanta.
 */

public class LoginExpiryService extends Service
{

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Inject
    RTokenAPI rTokenAPI;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        ((App) getApplication()).getComponent().inject(LoginExpiryService.this);
        mSharedPreferences = getSharedPreferences("epointsPrefFile",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId)
    {
        if (intent!=null)
        {
            int expTime      = intent.getIntExtra("expiryTime",0);
            countTimer(expTime);
        }


        return START_STICKY;
    }

    private void countTimer(int expTime)
    {
        new CountDownTimer(expTime,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                long mSecRemaining = millisUntilFinished / 1000;
                Log.i("mSec Remaining",""+mSecRemaining);
            }

            @Override
            public void onFinish()
            {
                Log.i("TimeUp","Done");
                refreshToken();
            }
        }.start();

    }

    private void refreshToken()
    {
        if (mSharedPreferences !=null)
        {
            String rToken = mSharedPreferences.getString("REFRESH_TOKEN","");
            Log.i("Refresh Token",""+rToken);

            if (rToken !=null)
            {
                                    /*

                                    https://qa-oauth.epoints.com/oauth/token?
                                    client_id=bdl&
                                    client_secret=bdl_secret&
                                    grant_type=refresh_token&
                                    refresh_token=857b14e4-f086-47b5-97d5-4d4102d6a990

                                    Reponse

                                    {
                                           "access_token": "bc6a6034-3cae-48c1-b7c6-3b935d3087fa",
                                           "token_type": "bearer",
                                           "refresh_token": "9a4e3a5a-8fd7-4512-bafe-a4b0e6f6e366",
                                           "expires_in": 3599,
                                           "scope": "read write"
                                    }
                                    */


                retrofit2.Call<SignInResult> call = rTokenAPI.signInRefreshToken("bdl","bdl_secret","refresh_token",rToken);
                call.enqueue(new Callback<SignInResult>() {
                    @Override
                    public void onResponse(retrofit2.Call<SignInResult> call, Response<SignInResult> response)
                    {
                        Log.i("Response From Server",""+response.code());

                        String  aToken = response.body().getAccessToken();
                        String  rToken = response.body().getRefreshToken();
                        Integer eIn    = response.body().getExpiresIn();

                        Log.i("aToken in Service",""+aToken);
                        Log.i("rToken in Service",""+rToken);
                        Log.i("eIn in Service",""+eIn);

                        mEditor.putString("ACCESS_TOKEN",response.body().getAccessToken());
                        mEditor.putString("REFRESH_TOKEN",response.body().getRefreshToken());
                        mEditor.putInt("EXPIRY_TIME",response.body().getExpiresIn());
                        mEditor.commit();

                        countTimer(eIn);



                    }

                    @Override
                    public void onFailure(retrofit2.Call<SignInResult> call, Throwable t)
                    {
                        Log.e("Excpetion",""+t);
                    }
                });


            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


/*
        if (LoginExpiryService.class != null) {
            Service service = (Service) LoginExpiryService.context;
            service.stopSelf();
        }
*/
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
