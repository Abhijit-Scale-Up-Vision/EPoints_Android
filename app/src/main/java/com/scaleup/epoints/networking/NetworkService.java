package com.scaleup.epoints.networking;

import com.scaleup.epoints.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Abhijit.
 */
public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
