
package com.iat.epoints.http.api;

import com.iat.epoints.BuildConfig;
import com.iat.epoints.http.apimodel.SignInResult;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Manikanta.
 */

public interface RTokenAPI
{
    @Headers("Content-Type: application/json")
    @POST(BuildConfig.BASE_URL_SIGN_IN_RTOKEN)
    Call<SignInResult> signInRefreshToken(@Query("client_id") String client_id,
                                          @Query("client_secret") String client_secret,
                                          @Query("grant_type") String grant_type,
                                          @Query("refresh_token") String refresh_token);

}
