/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.drm.DrmManagerClient
 *  android.util.Log
 */
package com.google.android.mms.util;

import android.content.Context;
import android.drm.DrmManagerClient;
import android.util.Log;

public class DownloadDrmHelper {
    public static final String EXTENSION_DRM_MESSAGE = ".dm";
    public static final String EXTENSION_INTERNAL_FWDL = ".fl";
    public static final String MIMETYPE_DRM_MESSAGE = "application/vnd.oma.drm.message";
    private static final String TAG = "DownloadDrmHelper";

    public static String getOriginalMimeType(Context object, String string, String string2) {
        DrmManagerClient drmManagerClient = new DrmManagerClient(object);
        object = string2;
        try {
            if (drmManagerClient.canHandle(string, null)) {
                object = drmManagerClient.getOriginalMimeType(string);
            }
        }
        catch (IllegalStateException illegalStateException) {
            Log.w((String)TAG, (String)"DrmManagerClient didn't initialize properly.");
            object = string2;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.w((String)TAG, (String)"Can't get original mime type since path is null or empty string.");
            object = string2;
        }
        return object;
    }

    public static boolean isDrmConvertNeeded(String string) {
        return MIMETYPE_DRM_MESSAGE.equals(string);
    }

    public static boolean isDrmMimeType(Context context, String string) {
        boolean bl;
        block5 : {
            boolean bl2 = false;
            boolean bl3 = false;
            bl = bl2;
            if (context == null) break block5;
            DrmManagerClient drmManagerClient = new DrmManagerClient(context);
            bl = bl3;
            if (string == null) break block5;
            bl = bl3;
            try {
                if (string.length() > 0) {
                    bl = drmManagerClient.canHandle("", string);
                }
            }
            catch (IllegalStateException illegalStateException) {
                Log.w((String)TAG, (String)"DrmManagerClient didn't initialize properly.");
                bl = bl2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.w((String)TAG, (String)"DrmManagerClient instance could not be created, context is Illegal.");
                bl = bl3;
            }
        }
        return bl;
    }

    public static String modifyDrmFwLockFileExtension(String string) {
        String string2 = string;
        if (string != null) {
            int n = string.lastIndexOf(".");
            string2 = string;
            if (n != -1) {
                string2 = string.substring(0, n);
            }
            string2 = string2.concat(EXTENSION_INTERNAL_FWDL);
        }
        return string2;
    }
}

