package org.peenyaindustries.piaconnect.piaconnect;

import android.app.Application;
import android.content.Context;

import org.peenyaindustries.piaconnect.storage.Database;

public class MyApplication extends Application {

    private static MyApplication sInstance;
    private static Database database;

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public synchronized static Database getWritableDatabase() {
        if (database == null) {
            database = new Database(getAppContext());
        }

        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        database = new Database(this);
    }
}
