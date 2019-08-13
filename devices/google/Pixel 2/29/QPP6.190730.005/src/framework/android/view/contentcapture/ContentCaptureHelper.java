/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.os.Build;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public final class ContentCaptureHelper {
    private static final String TAG = ContentCaptureHelper.class.getSimpleName();
    public static boolean sDebug;
    public static boolean sVerbose;

    static {
        sVerbose = false;
        sDebug = true;
    }

    private ContentCaptureHelper() {
        throw new UnsupportedOperationException("contains only static methods");
    }

    public static int getDefaultLoggingLevel() {
        return (int)Build.IS_DEBUGGABLE;
    }

    public static String getLoggingLevelAsString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN-");
                    stringBuilder.append(n);
                    return stringBuilder.toString();
                }
                return "VERBOSE";
            }
            return "DEBUG";
        }
        return "OFF";
    }

    public static String getSanitizedString(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charSequence.length());
            stringBuilder.append("_chars");
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }

    public static void setLoggingLevel() {
        ContentCaptureHelper.setLoggingLevel(DeviceConfig.getInt("content_capture", "logging_level", ContentCaptureHelper.getDefaultLoggingLevel()));
    }

    public static void setLoggingLevel(int n) {
        CharSequence charSequence = TAG;
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("Setting logging level to ");
        charSequence2.append(ContentCaptureHelper.getLoggingLevelAsString(n));
        Log.i((String)charSequence, charSequence2.toString());
        sDebug = false;
        sVerbose = false;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    charSequence2 = TAG;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("setLoggingLevel(): invalud level: ");
                    ((StringBuilder)charSequence).append(n);
                    Log.w((String)charSequence2, ((StringBuilder)charSequence).toString());
                    return;
                }
                sVerbose = true;
            }
            sDebug = true;
            return;
        }
    }

    public static <T> ArrayList<T> toList(Set<T> collection) {
        collection = collection == null ? null : new ArrayList<T>(collection);
        return collection;
    }

    public static <T> ArraySet<T> toSet(List<T> collection) {
        collection = collection == null ? null : new ArraySet<T>(collection);
        return collection;
    }
}

