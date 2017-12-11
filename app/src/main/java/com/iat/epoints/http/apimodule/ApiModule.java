package com.iat.epoints.http.apimodule;

import com.iat.epoints.BuildConfig;
import com.iat.epoints.http.api.SignInAPI;
import com.iat.epoints.http.api.SignUpAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public final String BASE_URL = BuildConfig.BASE_URL_SIGN_UP_QA;
    public final String QA_BASE_URL = BuildConfig.BASE_URL_SIGIN_QA;
    public final String QA_BASE_SUCCESS_SIGN_IN_URL = BuildConfig.BASE_URL_SIGN_SUCCESS_QA;

    @Provides
    public OkHttpClient provideClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    @Provides
    public OkHttpClient provideClientHeader() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public SignUpAPI provideApiService() {
        return provideRetrofit(BASE_URL, provideClient()).create(SignUpAPI.class);
    }

    @Provides
    public SignInAPI provideSignInApiService()
    {
        return provideRetrofit(QA_BASE_URL, provideClientHeader()).create(SignInAPI.class);
    }

}
