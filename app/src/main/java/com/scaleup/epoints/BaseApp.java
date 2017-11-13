package com.scaleup.epoints;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scaleup.epoints.deps.Deps;
import com.scaleup.epoints.deps.DaggerDeps;
import com.scaleup.epoints.networking.NetworkModule;

import java.io.File;

/**
 * Created by Abhijit.
 */
public class BaseApp  extends AppCompatActivity{
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}
