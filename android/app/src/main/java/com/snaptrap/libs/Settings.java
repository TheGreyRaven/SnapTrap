package com.snaptrap.libs;

import android.os.Environment;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Settings extends ReactContextBaseJavaModule {

    public Settings(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SnapSettings";
    }

    static Gson gson = new Gson();
    static String settingsLocation = String.format("%s/SnapTrap", Environment.getExternalStorageDirectory());
    static File jsonSettings = new File(settingsLocation + "/settings.json");
    static File homeDir = new File(settingsLocation);

    static public Boolean verifySettingsFile() {

        if (jsonSettings.exists()) {
            return true;
        }

        if (!homeDir.exists()) {
            homeDir.mkdir();
            homeDir.setReadable(true);
            homeDir.setWritable(true);
        }

        try {
            Map<String, Object> settings = new HashMap<>();
            settings.put("saveSnaps", true);
            settings.put("disableScreenshot", true);
            settings.put("saveLocation", settingsLocation);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }

        return true;
    }

    public static String getSavingPath() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("saveLocation").getAsString();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return settingsLocation;
        }
    };

    public static boolean getSaveSnaps() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("saveSnaps").getAsBoolean();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean setSaveSnaps(boolean bool) {
        try {
            Map<String, Object> settings = new HashMap<>();
            settings = gson.fromJson(new FileReader(jsonSettings), settings.getClass());
            settings.remove("saveSnaps");
            settings.put("saveSnaps", bool);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
            return true;
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean getDisableScreenshot() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("disableScreenshot").getAsBoolean();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean setDisableScreenshot(boolean bool) {
        try {
            Map<String, Object> settings = new HashMap<>();
            settings = gson.fromJson(new FileReader(jsonSettings), settings.getClass());
            settings.remove("disableScreenshot");
            settings.put("disableScreenshot", bool);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
            return true;
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    @ReactMethod
    public static void exportGetSavingPath(Promise promise) {
        promise.resolve(getSavingPath());
    }

    @ReactMethod
    public static void exportSetSaveSnaps(Boolean bool, Promise promise) {
        promise.resolve(setSaveSnaps(bool));
    }

    @ReactMethod
    public static void exportGetSaveSnaps(Promise promise) {
        promise.resolve(getSaveSnaps());
    }

    @ReactMethod
    public static void exportSetDisableScreenshot(Boolean bool, Promise promise) {
        promise.resolve(setDisableScreenshot(bool));
    }

    @ReactMethod
    public static void exportGetDisableScreenshot(Promise promise) {
        promise.resolve(getDisableScreenshot());
    }

    /**
     * React Native exports.
     * These functions are separate to the ones above so they can be used in async fashion in React Native.
     */

    /*@ReactMethod
    public static void exportGetSavingPath(Promise promise) {
        try {
            promise.resolve(sharedPref.getString("savePath", String.format("%s/SnapTrap/", Environment.getExternalStorageDirectory())));
        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            promise.reject(e);
        }
    };

    public static void exportGetBool(String name, Promise promise) {
        if (name == null) {
            promise.reject(new Exception("Missing parameter."));
            return;
        }

        try {
            promise.resolve(sharedPref.getBoolean(name, true));
        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            promise.reject(e);
        }
    };

    public static void exportSetBool(String name, Boolean bool, Promise promise) {
        if (name == null || bool == null) {
            promise.reject(new Exception("Missing parameter."));
            return;
        }

        try {
            editor.putBoolean(name, bool);
            editor.commit();
            promise.resolve(null);
        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            promise.reject(e);
        }
    };

    public static void exportGetString(String name, Promise promise) {
        if (name == null) {
            promise.reject(new Exception("Missing parameter."));
            return;
        }

        try {
            promise.resolve(sharedPref.getString(name, ""));
        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            promise.reject(e);
        }
    }

    public static void exportSetString(String name, String value, Promise promise) {
        if (name == null || value == null) {
            promise.reject(new Exception("Missing parameter."));
            return;
        }

        try {
            editor.putString(name, value);
            editor.commit();
            promise.resolve(null);
        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            promise.reject(e);
        }
    }*/
}
