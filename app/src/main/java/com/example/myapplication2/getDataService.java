package com.example.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class getDataService extends Service {
    public getDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
