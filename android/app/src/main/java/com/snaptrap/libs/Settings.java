package com.snaptrap.libs;

import android.content.SharedPreferences;
import android.os.Environment;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.snaptrap.MainApplication;

public class Settings extends ReactContextBaseJavaModule {
    Settings(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SnapTrapSettings";
    }

    static SharedPreferences sharedPref     = MainApplication.getAppContext().getSharedPreferences("SnapTrap", 0);
    static SharedPreferences.Editor editor  = sharedPref.edit();

    public static String getSavingPath() {
        return sharedPref.getString("savePath", String.format("%s/SnapTrap/", Environment.getExternalStorageDirectory()));
    };

    public static Boolean getBool(String name) {
        if (name == null) {
            return false;
        }

        return sharedPref.getBoolean(name, true);
    };

    public static void setBool(String name, Boolean bool) {
        if (name == null || bool == null) {
            return;
        }

        editor.putBoolean(name, bool);
        editor.commit();
    };

    public static String getString(String name) {
        return sharedPref.getString(name, "");
    }

    public static void setString(String name, String value) {
        if (name == null || value == null) {
            return;
        }

        editor.putString(name, value);
        editor.commit();
    }
}
