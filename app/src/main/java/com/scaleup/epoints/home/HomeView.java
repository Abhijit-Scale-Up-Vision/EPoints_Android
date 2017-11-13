package com.scaleup.epoints.home;

import com.scaleup.epoints.models.CityListResponse;

/**
 * Created by Abhijit.
 */
public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getCityListSuccess(CityListResponse cityListResponse);

}
