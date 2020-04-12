package com.popular.baking.networkUtils;

import android.util.Log;

import com.popular.baking.constants.Constants;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class LifeCycleEventManager implements LifecycleObserver  {

    private String TAG;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.d(TAG, "create: " + Constants.LifeCycleEvents_OnCreate);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.d(TAG, "start: " + Constants.LifeCycleEvents_OnStart);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.d(TAG, "resume: " + Constants.LifeCycleEvents_OnResume);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void pause() {
        Log.d(TAG, "pause: " + Constants.LifeCycleEvents_OnPause);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.d(TAG, "destory: " + Constants.LifeCycleEvents_OnDestroy);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.d(TAG, "stop: " + Constants.LifeCycleEvents_OnStop);
    }

    public LifeCycleEventManager(String TAG) {
        this.TAG = TAG;
    }

    public void registerLifeCycleEvent(Lifecycle lifeCycle){
        lifeCycle.addObserver(this);
    }
}
