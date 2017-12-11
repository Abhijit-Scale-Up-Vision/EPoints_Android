package com.iat.epoints.http.api;

import com.iat.epoints.BuildConfig;
import com.iat.epoints.http.apimodel.SignInResult;
import com.iat.epoints.http.apimodel.SignInSucess;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by USER on 08-12-2017.
 */

public interface SignInAPI
{

    @Headers("Content-Type: application/json")
    @POST(BuildConfig.SIGN_IN_API)
    Call<SignInResult> signInUser(@Query("grant_type") String grantType,
                                  @Query("username") String userName,
                                  @Query("password") String password,
                                  @Query("client_id") String client_id,
                                  @Query("client_secret") String client_secret);

    @Headers("Content-Type: application/json")
    @GET(BuildConfig.BASE_URL_SIGN_SUCCESS_QA)
    Call<SignInSucess> signInUserSucess(@Header("Authorization") String authorization);
}
