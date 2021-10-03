package com.snaptrap.libs;

import static com.snaptrap.libs.FileCopy.copySnapMedia;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

import de.robv.android.xposed.XposedBridge;

public class Directories {

    final static String home = Settings.getSavingPath();

    public static boolean createHome() {
        File dir = new File(home);
        if (!dir.exists()) {
            try {
                Logging.log("Home directory not found, trying creating one at " + home);
                dir.mkdir();
                dir.setWritable(true);
                dir.setReadable(true);
                return true;
            } catch (Exception e) {
                Logging.log("ERROR " + e.getMessage());
                return false;
            }
        }
        return true;
    };

    public static boolean verifySnapStorage(Context snapContext) {
        File snapStorage = new File(String.format("%s/files/file_manager/chat_snap/", snapContext.getApplicationInfo().dataDir));
        if (!snapStorage.exists()) {
            Logging.log("ERROR Could not find 'chat_snap' folder in Snapchat data directory.");
        }

        if (!snapStorage.canRead() || !snapStorage.canWrite()) {
            Logging.log("ERROR read or write access is missing, trying to fix.");
            try {
                snapStorage.setWritable(true);
                snapStorage.setReadable(true);
                return true;
            } catch (Exception e) {
                Logging.log("ERROR " + e.getMessage());
                return false;
            }
        }

        return true;
    };

    public static void copySnaps(Context snapContext) {
        if (Settings.getSaveSnaps()) {
            File snapStorage = new File(String.format("%s/files/file_manager/chat_snap/", snapContext.getApplicationInfo().dataDir));

            File[] files = snapStorage.listFiles();
            Logging.log("Snapchat 'chat_snap' folder have a length of: " + files.length);

            for (int i = 0; i < files.length; i++) {
                Logging.log("Found file: " + files[i].getPath());
                if (copySnapMedia(files[i], home)) {
                    Logging.log("Successfully saved Snap: " + files[i].getPath());
                } else {
                    Logging.log("Failed to save Snap: " + files[i].getPath());
                }
            }
            Toast.makeText(snapContext, (files.length > 0) ? "Trying to save: " + files.length + " snaps!" : "No snaps were found!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(snapContext, "Automatic saving is disabled!", Toast.LENGTH_LONG).show();
        }
        return;
    };
}
