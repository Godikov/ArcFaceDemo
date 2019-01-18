package com.humu.arcfacedemo;

import android.app.Application;

import com.humu.arcfacedemo.face.FaceDB;

/**
 * Created by Administrator on 2019/1/18.
 */

public class App extends Application {

    private FaceDB mFaceDB;

    @Override
    public void onCreate() {
        super.onCreate();
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
    }
}
