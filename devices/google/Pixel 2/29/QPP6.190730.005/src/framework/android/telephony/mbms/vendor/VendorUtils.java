/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.telephony.mbms.MbmsDownloadReceiver;
import java.util.List;

@SystemApi
public class VendorUtils {
    public static final String ACTION_CLEANUP = "android.telephony.mbms.action.CLEANUP";
    public static final String ACTION_DOWNLOAD_RESULT_INTERNAL = "android.telephony.mbms.action.DOWNLOAD_RESULT_INTERNAL";
    public static final String ACTION_FILE_DESCRIPTOR_REQUEST = "android.telephony.mbms.action.FILE_DESCRIPTOR_REQUEST";
    public static final String EXTRA_FD_COUNT = "android.telephony.mbms.extra.FD_COUNT";
    public static final String EXTRA_FINAL_URI = "android.telephony.mbms.extra.FINAL_URI";
    public static final String EXTRA_FREE_URI_LIST = "android.telephony.mbms.extra.FREE_URI_LIST";
    public static final String EXTRA_PAUSED_LIST = "android.telephony.mbms.extra.PAUSED_LIST";
    public static final String EXTRA_PAUSED_URI_LIST = "android.telephony.mbms.extra.PAUSED_URI_LIST";
    public static final String EXTRA_SERVICE_ID = "android.telephony.mbms.extra.SERVICE_ID";
    public static final String EXTRA_TEMP_FILES_IN_USE = "android.telephony.mbms.extra.TEMP_FILES_IN_USE";
    public static final String EXTRA_TEMP_FILE_ROOT = "android.telephony.mbms.extra.TEMP_FILE_ROOT";
    public static final String EXTRA_TEMP_LIST = "android.telephony.mbms.extra.TEMP_LIST";

    public static ComponentName getAppReceiverFromPackageName(Context object, String object2) {
        object2 = new ComponentName((String)object2, MbmsDownloadReceiver.class.getCanonicalName());
        Intent intent = new Intent();
        intent.setComponent((ComponentName)object2);
        object = ((Context)object).getPackageManager().queryBroadcastReceivers(intent, 0);
        if (object != null && object.size() > 0) {
            return object2;
        }
        return null;
    }
}

