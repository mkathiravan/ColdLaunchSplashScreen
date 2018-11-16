package com.example.kabali.coldlaunchsplashscreen;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context)
    {
        this.context = context;

        appDatabase = Room.databaseBuilder(context,AppDatabase.class,"MyToDos").build();
    }

    public static synchronized DatabaseClient getmInstance(Context mContext)
    {
        if(mInstance == null)
        {
            mInstance = new DatabaseClient(mContext);
        }
           return mInstance;
    }

    public AppDatabase getAppDatabase()
    {
        return appDatabase;
    }

}
