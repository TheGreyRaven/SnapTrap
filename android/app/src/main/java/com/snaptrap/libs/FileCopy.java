package com.snaptrap.libs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import de.robv.android.xposed.XposedBridge;


public class FileCopy {
    static final long JPG_HEADER = 0xFFD8FFE00010L;
    static final long PNG_HEADER = 0x89504E470D0AL;
    static final long WEBP_HEADER = 0x524946460000L;
    static final long MP4_HEADER = 0x000000006674L;
    static final long WEBP_MASK = 0xFFFFFFFF0000L;
    static final long MP4_MASK = 0xFFFFFF00FFFFL;

    private static String getFileExtension(long headerBytes) {
        if (headerBytes == JPG_HEADER) {
            return ".jpg";
        }
        if (headerBytes == PNG_HEADER) {
            return ".png";
        }
        // The following have "wildcard" bits that can be masked out for the comparison
        if ((headerBytes & MP4_MASK) == MP4_HEADER) {
            return ".mp4";
        }
        if ((headerBytes & WEBP_MASK) == WEBP_HEADER) {
            return ".webp";
        }
        return "";
    }

    public static boolean copySnapMedia(File fromFile, String homeDir) {
        try {
           long header  = getHeaderBytes(fromFile);
           String extension = getFileExtension(header);
           if (extension.equals("")) {
               XposedBridge.log(String.format("[SnapTrap]: File %s is unknown. Header = 0x%012x", fromFile.getName(), header));
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

    public static long getHeaderBytes(File file) {
        // We only check for the first 6 bytes of the header,
        // add 2 more for 64-bit long padding.
        byte[] buffer = new byte[8];
        try {
            FileInputStream fin = new FileInputStream(file.getPath());
            int bytesRead = 0;
            while (bytesRead >= 0 && bytesRead < 6) {
                // Read into the buffer the first 6 bytes of the file,
                // offset by 2 to keep the buffer's upper bits 0.
                bytesRead = fin.read(buffer, 2, 6);
            }
        } catch (IOException e) {
            XposedBridge.log("[SnapTrap]: ERROR " + e.getMessage());
        }
        // Return the 64-bit long representation of the read bits
        return ByteBuffer.wrap(buffer).getLong();
    }
}
