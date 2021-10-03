package com.snaptrap.xposed;

import static com.snaptrap.libs.FileCopy.copySnapMedia;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.Toast;

import com.snaptrap.MainApplication;
import com.snaptrap.libs.Directories;
import com.snaptrap.libs.Logging;
import com.snaptrap.libs.Settings;
import com.snaptrap.libs.SnapchatData;

import java.io.File;
import java.lang.reflect.Method;

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
        Logging.log(lpparam.toString());
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.contains("com.snapchat.androi")) {
            return;
        }

        Logging.log("Hooking into Snapchat.");

        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Logging.log("Looking for Snapchat application class.");
                try {
                    Context snapContext = (Context) param.args[0];
                    if (SnapchatData.verifyVersion(snapContext)) {
                        Logging.log("Snapchat is OK.");
                    }

                    if (Settings.verifySettingsFile()) {
                        Logging.log("Settings file is OK.");
                    }
                } catch (Exception e) {
                    Logging.log("ERROR " + e.getMessage());
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (!SnapchatData.canHook) {
                    Logging.log("ERROR incompatible Snapchat found.");
                    return;
                }

                Context snapContext = (Context) param.args[0];
                Logging.log("Hooked into Snapchat application.");

                if (Directories.createHome()) {
                    Logging.log("Home directory is OK.");
                }

                if (Directories.verifySnapStorage(snapContext)) {
                    Logging.log("Snapchat directory is OK.");
                    Directories.copySnaps(snapContext);
                }

                String className = (String) SnapchatData.versions.get(SnapchatData.snapchatVersion).get("className");
                String methodName = (String) SnapchatData.versions.get(SnapchatData.snapchatVersion).get("methodName");
                String typesAndCallback = (String) SnapchatData.versions.get(SnapchatData.snapchatVersion).get("parameterTypesAndCallback");

                findAndHookMethod(className, lpparam.classLoader, methodName, typesAndCallback, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        if (Settings.getDisableScreenshot()) {
                            Logging.log("Screenshot detected, replacing method with null return.");
                            return null;
                        } else {
                            Method snapMethod = (Method) param.method;
                            XposedBridge.invokeOriginalMethod(snapMethod, param.thisObject, param.args);
                        }
                        return null;
                    }
                });
            }
        });
    }
}
