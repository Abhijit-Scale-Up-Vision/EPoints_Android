package com.iat.epoints.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iat.epoints.BuildConfig;
import com.iat.epoints.http.api.RTokenAPI;
import com.iat.epoints.http.api.SignInAPI;
import com.iat.epoints.http.apimodel.SignInResult;
import com.iat.epoints.root.App;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manikanta.
 */

public class LoginExpiryService extends IntentService
{

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Inject
    RTokenAPI rTokenAPI;

    int expTime,cSec,eTimeCallSec;
    boolean val = false;
    public static final int notify = 9000;  //interval between two services(Here Service run every 5 seconds)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling


    public LoginExpiryService() {
        super("LoginExpiryService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((App) getApplication()).getComponent().inject(LoginExpiryService.this);
        mSharedPreferences = getSharedPreferences("epointsPrefFile",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();


        if (mTimer != null)
            mTimer.cancel();
        else
            mTimer = new Timer();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId)
    {
        cSec        = (int) System.currentTimeMillis();

        expTime      = intent.getIntExtra("expiryTime",0);


        eTimeCallSec = cSec + 5*1000;
        val = true;
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);  //Schedule task
        return START_STICKY;
    }


    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run()
        {
            // run on another thread
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    if (val == true)
                    {
                        int cSecs        = (int) System.currentTimeMillis();
                        if (cSecs > eTimeCallSec)
                        {
                            val = false;

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

                                            val = true;
                                            mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);  //Schedule task
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
                    }
                }
            });

        }

    }
}
