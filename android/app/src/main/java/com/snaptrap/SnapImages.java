package com.snaptrap;

import android.net.Uri;
import android.util.Log;
import androidx.documentfile.provider.DocumentFile;
import com.anggrayudi.storage.file.DocumentFileCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;

import java.util.ArrayList;
import java.util.List;

public class SnapImages extends ReactContextBaseJavaModule {
    SnapImages(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SnapImageModule";
    }

    @ReactMethod
    public void getImagesFromPath(String stringPath, Promise promise) {
        List<String> imageList = new ArrayList<>();
        try {
            DocumentFile[] files = DocumentFileCompat.fromUri(
                    getReactApplicationContext(),
                    Uri.parse(stringPath)
            ).listFiles();

            for (int i = 0; i < files.length; i++){
                if (isValidMedia(files[i].getName())) {
                    imageList.add(String.valueOf(files[i].getUri()));
                }
            }

            String[] returnArray = new String[imageList.size()];
            returnArray = imageList.toArray(returnArray);

            WritableArray promiseArray = Arguments.createArray();
            for(int i = 0; i < returnArray.length; i++){
                promiseArray.pushString(returnArray[i]);
            }
            promise.resolve(promiseArray);
        } catch (Exception e) {
            Log.e("SnapImages-getImages", e.getMessage());
            promise.reject(e);
        }
    }

    public boolean isValidMedia(String file) {
        final String[] mediaTypes = {
                ".bmp",
                ".gif",
                ".png",
                ".jpg",
                ".jpeg",
                ".webp"
        };

        for (int i = 0; i < mediaTypes.length; i++) {
            if (file.endsWith(mediaTypes[i])) {
                return true;
            }
        }
        return false;
    }

}