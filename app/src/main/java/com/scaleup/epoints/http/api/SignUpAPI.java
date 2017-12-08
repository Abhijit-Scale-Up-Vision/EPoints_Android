package com.scaleup.epoints.http.api;

import com.scaleup.epoints.http.apimodel.ResultStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SignUpAPI {

    @Headers("Content-Type: application/json")
    @GET("rest/join/init-mobile/{email}/")
    Call<ResultStatus> registerUser(@Path("email") String email, @Query("firstName") String firstname,
                               @Query("lastName") String lastname,
                               @Query("password") String password);

    @Headers("Content-Type: application/json")
    @GET("rest/join/init-mobile/{email}/")
    Call<ResultStatus> alreadyRegisterUser(@Path("email") String email);

}
