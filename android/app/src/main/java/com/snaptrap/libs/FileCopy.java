package com.snaptrap.libs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import de.robv.android.xposed.XposedBridge;


public class FileCopy {
    public static boolean copySnapMedia(File fromFile, File homeDir) {

        try {
           String hexCode = getHex(fromFile);
           String extension;

           if (hexCode.contains("ff d8 ff e0 00 10")) {
               extension = ".jpg";
           } else if (hexCode.contains("89 50 4e 47 0d 0a")) {
               extension = ".png";
           } else if (checkWildcard(hexCode, "00 00 00 ** 66 74", '*')) {
               extension = ".mp4";
           } else if (checkWildcard(hexCode, "52 49 46 46 ** **", '*')) {
               extension = ".webp";
           } else {
               XposedBridge.log("[SnapTrap]: File " + fromFile.getName() + " is unknown");
               return false;
           }

           XposedBridge.log("[SnapTrap]: File " + fromFile.getName() + " is a " + extension);

           InputStream inputStream = new FileInputStream(fromFile);
           OutputStream outputStream = new FileOutputStream(homeDir + "/" + fromFile.getName() + extension);

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            inputStream.close();
            outputStream.close();

            return true;

        } catch (Exception e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
            return false;
        }
    }

    public static String getHex(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fin = new FileInputStream(file.getPath());
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = fin.read(buffer)) > -1)
                for(int in = 0; in < bytesRead; in++)
                    builder.append(String.format("%02x", buffer[in] & 0xFF)).append(in != bytesRead - 1 ? " " : "");
        } catch (IOException e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
        }

        return builder.substring(0, 17);
    }

    public static boolean checkWildcard(String s1, String s2, char wildCard) {
        if(s1.length() != s2.length())
            return false;

        for(int i=0; i<s1.length(); i++) {
            char c1 = s1.charAt(i), c2 = s2.charAt(i);
            if(c1!=wildCard && c2!=wildCard && c1!=c2) {
                return false;
            }
        }

        return true;
    }
}
