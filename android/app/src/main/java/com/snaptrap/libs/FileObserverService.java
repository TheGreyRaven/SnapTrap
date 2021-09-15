package com.snaptrap.libs;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

public class FileObserverService extends Service {
    private static final String INTENT_EXTRA_FILEPATH = "";
    private FileObserver mFileObserver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if((intent.hasExtra(INTENT_EXTRA_FILEPATH))) // we store the path of directory inside the intent that starts the service
            mFileObserver = new FileObserver(intent.getStringExtra(INTENT_EXTRA_FILEPATH)) {
                @Override
                public void onEvent(int event, String path) {
                    // If an event happens we can do stuff inside here
                    // for example we can send a broadcast message with the event-id
                    Log.d("FILEOBSERVER_EVENT", "Event with id " + Integer.toHexString(event) + " happened"); // event identifies the occured Event in hex
                }
            };
        mFileObserver.startWatching(); // The FileObserver starts watching
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }
}
