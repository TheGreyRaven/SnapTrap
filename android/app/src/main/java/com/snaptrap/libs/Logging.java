package com.snaptrap.libs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.robv.android.xposed.XposedBridge;

public class Logging {
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public void log(String text) {
        XposedBridge.log("[SnapTrap] (" + dateFormat.format(new Date()) + "): " + text);
    }
}
