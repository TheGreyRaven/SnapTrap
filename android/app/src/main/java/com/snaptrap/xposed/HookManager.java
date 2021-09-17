package com.snaptrap.xposed;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookManager implements IXposedHookLoadPackage, IXposedHookInitPackageResources {

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam lpparam) throws Throwable {
        XposedBridge.log(lpparam.toString());
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.snapchat.android"))
            return;
        XposedBridge.log("[SnapTrap]: Hooking into Snapchat...");

        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach", Context.class, new XC_MethodHook() {
            boolean canHook = false;
            String compatibleSnapchat = "11.45.0.38";


            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("[SnapTrap]: Looking for Snapchat application class...");
                try {
                    Context snapContext = (Context) param.args[0];
                    PackageInfo packageInfo = snapContext.getPackageManager().getPackageInfo(snapContext.getPackageName(), 0);
                    String version = packageInfo.versionName;
                    XposedBridge.log("[SnapTrap]: Snapchat version: " + version);
                    if (compatibleSnapchat.contains(version)) {
                        canHook = true;
                        XposedBridge.log("[SnapTrap]: Allowing hooking...");
                    }
                } catch (Exception e) {
                    XposedBridge.log("[SnapChat]: ERROR " + e.getMessage());
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (!canHook) {
                    XposedBridge.log("[SnapTrap]: ERROR incompatible Snapchat found, expected version: " + compatibleSnapchat);
                    return;
                }

                Context snapContext = (Context) param.args[0];
                XposedBridge.log("[SnapTrap]: Hooked into Snapchat application.");

                File home = new File(String.format("%s/SnapTrap/", Environment.getExternalStorageDirectory()));
                if (!home.exists()) {
                    XposedBridge.log("[SnapTrap]: Home directory not found, creating one...");
                    home.mkdir();
                }

                try {
                    String snapStorage = String.format("%s/files/file_manager/chat_snap/", snapContext.getApplicationInfo().dataDir);

                    File snapPath = new File(snapStorage);
                    if (snapPath.exists()) {
                        File[] files = snapPath.listFiles();

                        for (int i = 0; i < files.length; i++) {
                            XposedBridge.log("[SnapTrap] Found Snap named: " + files[i].getName());
                            File checkFile = new File(files[i].getPath());
                            long fileSizeInBytes = checkFile.length();
                            long fileSizeInKB = fileSizeInBytes / 1024;
                            long fileSizeInMB = fileSizeInKB / 1024;
                            File destination;
                            if (fileSizeInMB < 1) {
                                destination = new File(home.getPath() + ".jpg");
                            } else {
                                destination = new File(home.getPath() + ".mp4");
                            }
                            checkFile.renameTo(destination);
                        }

                    } else {
                        XposedBridge.log("[SnapTrap]: ERROR failed to access stored Snaps directory!");
                    }
                } catch (Exception e) {
                    XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
                }

                findAndHookMethod("QU7", lpparam.classLoader, "b", "PU7", XC_MethodReplacement.DO_NOTHING, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("[SnapTrap]: Looking for Snapchat screenshot function...");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("[SnapTrap]: Hooked and disabled screenshot detection!");
                    }
                });
            }
        });
    }
}
