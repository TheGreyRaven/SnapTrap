package com.snaptrap.libs;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.Toast;

import java.util.HashMap;

public class SnapchatData {

    public static String snapchatVersion;
    public static Boolean canHook = false;

    static public HashMap<String, HashMap> versions = new HashMap<String, HashMap>() {{
        put("11.45.0.38", new HashMap<String, String>() {{
            put("className", "QU7");
            put("methodName", "b");
            put("parameterTypesAndCallback", "PU7");
        }});

        put("11.46.0.33", new HashMap<String, String>() {{
            put("className", "le8");
            put("methodName", "b");
            put("parameterTypesAndCallback", "ke8");
        }});

        put("11.47.0.36", new HashMap<String, String>() {{
            put("className", "qf8");
            put("methodName", "b");
            put("parameterTypesAndCallback", "pf8");
        }});
    }};

    public static boolean verifyVersion(Context snapContext) {
        try {
            PackageInfo packageInfo = snapContext.getPackageManager().getPackageInfo(snapContext.getPackageName(), 0);
            snapchatVersion = packageInfo.versionName;
            Logging.log("Snapchat version: " + snapchatVersion);

            if (SnapchatData.versions.get(snapchatVersion) != null) {
                canHook = true;
                Logging.log("Allowing hooking.");
                Toast.makeText(snapContext, "SnapTrap loaded!", Toast.LENGTH_LONG).show();
                return true;
            } else {
                Logging.log("Incompatible version, aborting hooks.");
                Toast.makeText(snapContext, "Incompatible Snapchat version!", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (Exception e) {
            Logging.log("ERROR Incompatible version, aborting hooks.");
            return false;
        }
    }
}
