package com.snaptrap.xposed;

import static com.snaptrap.libs.FileCopy.copySnapMedia;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.widget.Toast;

import com.snaptrap.libs.SnapchatData;

import java.io.File;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookManager implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    /**
     * TODO: Refactor hook manager and move logs, media saving etc into separate class.
     */

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam lpparam) throws Throwable {
        XposedBridge.log(lpparam.toString());
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.contains("com.snapchat.androi")) // This is not a typo, I have 3 different Snapchat versions installed on my phone and each one have a random last character.
            return;

        XposedBridge.log("[SnapTrap]: Hooking into Snapchat...");
        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach", Context.class, new XC_MethodHook() {
            boolean canHook = false;
            String snapchatVersion;

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("[SnapTrap]: Looking for Snapchat application class...");
                try {
                    Context snapContext = (Context) param.args[0];
                    PackageInfo packageInfo = snapContext.getPackageManager().getPackageInfo(snapContext.getPackageName(), 0);
                    snapchatVersion = packageInfo.versionName;
                    XposedBridge.log("[SnapTrap]: Snapchat version: " + snapchatVersion);

                    if (SnapchatData.versions.get(snapchatVersion) != null) {
                        canHook = true;
                        XposedBridge.log("[SnapTrap]: Allowing hooking...");
                        Toast.makeText(snapContext, "SnapTrap loaded!", Toast.LENGTH_LONG).show();
                    } else {
                        XposedBridge.log("[SnapTrap]: Incompatible version, aborting hooks.");
                        Toast.makeText(snapContext, "Incompatible Snapchat version!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (!canHook) {
                    XposedBridge.log("[SnapTrap]: ERROR incompatible Snapchat found");
                    return;
                }

                Context snapContext = (Context) param.args[0];
                XposedBridge.log("[SnapTrap]: Hooked into Snapchat application.");

                File home = new File(String.format("%s/SnapTrap/", Environment.getExternalStorageDirectory()));
                if (!home.exists()) {
                    XposedBridge.log("[SnapTrap]: Home directory not found, creating one...");
                    home.mkdir();
                }

                XposedBridge.log("[SnapTrap]: Checking home folder permissions: READ = " + home.canRead() + ", WRITE = " + home.canWrite());
                if (!home.canRead() || !home.canWrite()) {
                    home.setReadable(true);
                    home.setWritable(true);
                }

                File snapStorage = new File(String.format("%s/files/file_manager/chat_snap/", snapContext.getApplicationInfo().dataDir));

                if (snapStorage.exists()) {
                    XposedBridge.log("[SnapTrap]: Snapchat 'chat_snap' folder exists");
                    XposedBridge.log("[SnapTrap]: Checking folder permissions: READ = " + snapStorage.canRead() + ", WRITE = " + snapStorage.canWrite());

                    if (!snapStorage.canRead()) {
                        XposedBridge.log("[SnapTrap]: Trying to get read access...");
                        if (snapStorage.setReadable(true)) {
                            XposedBridge.log("[SnapTrap]: Read access was successful");
                        } else {
                            XposedBridge.log("[SnapTrap]: Failed to gain read access");
                        }
                    }

                    File[] files = snapStorage.listFiles();
                    XposedBridge.log("[SnapTrap]: Snapchat 'chat_snap' folder have a length of: " + files.length);

                    for (int i = 0; i < files.length; i++) {
                        XposedBridge.log("[SnapTrap]: Found file: " + files[i].getPath());
                        if (copySnapMedia(files[i], home)) {
                            XposedBridge.log("[SnapTrap]: Successfully saved Snap: " + files[i].getPath());
                        } else {
                            XposedBridge.log("[SnapTrap]: Failed to save Snap: " + files[i].getPath());
                        }
                    }
                    Toast.makeText(snapContext, "Trying to save: " + files.length + " snaps!", Toast.LENGTH_LONG).show();
                } else {
                    XposedBridge.log("[SnapTrap]: Snapchat 'chat_snap' folder does NOT exists");
                }

                // Shorten this shit below
                findAndHookMethod((String) SnapchatData.versions.get(snapchatVersion).get("className"), lpparam.classLoader, (String) SnapchatData.versions.get(snapchatVersion).get("methodName"), SnapchatData.versions.get(snapchatVersion).get("parameterTypesAndCallback"), new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("[SnapTrap]: Screenshot detected, replacing method with null return.");
                        return null;
                    }
                });
            }
        });
    }
}
