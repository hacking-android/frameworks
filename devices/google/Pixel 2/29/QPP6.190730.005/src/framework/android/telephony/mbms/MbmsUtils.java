/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.telephony.mbms.MbmsTempFileProvider;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MbmsUtils {
    private static final String LOG_TAG = "MbmsUtils";

    public static File getEmbmsTempFileDirForService(Context context, String string2) {
        string2 = string2.replaceAll("[^a-zA-Z0-9_]", "_");
        return new File(MbmsTempFileProvider.getEmbmsTempFileDir(context), string2);
    }

    public static ServiceInfo getMiddlewareServiceInfo(Context list, String string2) {
        PackageManager packageManager = ((Context)((Object)list)).getPackageManager();
        Intent intent = new Intent();
        intent.setAction(string2);
        list = MbmsUtils.getOverrideServiceName((Context)((Object)list), string2);
        if (list == null) {
            list = packageManager.queryIntentServices(intent, 1048576);
        } else {
            intent.setComponent((ComponentName)((Object)list));
            list = packageManager.queryIntentServices(intent, 131072);
        }
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                Log.w(LOG_TAG, "More than one MBMS service found, cannot get unique service");
                return null;
            }
            return list.get((int)0).serviceInfo;
        }
        Log.w(LOG_TAG, "No MBMS services found, cannot get service info");
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ComponentName getOverrideServiceName(Context var0, String var1_2) {
        block5 : {
            block8 : {
                block6 : {
                    block7 : {
                        var2_3 = null;
                        var3_4 = var1_2.hashCode();
                        if (var3_4 == -1374878107) break block6;
                        if (var3_4 == -407466459) break block7;
                        if (var3_4 != 1752202112 || !var1_2.equals("android.telephony.action.EmbmsGroupCall")) ** GOTO lbl-1000
                        var3_4 = 2;
                        break block8;
                    }
                    if (!var1_2.equals("android.telephony.action.EmbmsDownload")) ** GOTO lbl-1000
                    var3_4 = 0;
                    break block8;
                }
                if (var1_2.equals("android.telephony.action.EmbmsStreaming")) {
                    var3_4 = 1;
                } else lbl-1000: // 3 sources:
                {
                    var3_4 = -1;
                }
            }
            var1_2 = var3_4 != 0 ? (var3_4 != 1 ? (var3_4 != 2 ? var2_3 : "mbms-group-call-service-override") : "mbms-streaming-service-override") : "mbms-download-service-override";
            if (var1_2 == null) {
                return null;
            }
            try {
                var0 = var0.getPackageManager().getApplicationInfo(var0.getPackageName(), 128);
                if (var0.metaData != null) break block5;
                return null;
            }
            catch (PackageManager.NameNotFoundException var0_1) {
                return null;
            }
        }
        var0 = var0.metaData.getString((String)var1_2);
        if (var0 != null) return ComponentName.unflattenFromString((String)var0);
        return null;
    }

    public static boolean isContainedIn(File object, File file) {
        try {
            object = ((File)object).getCanonicalPath();
            boolean bl = file.getCanonicalPath().startsWith((String)object);
            return bl;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to resolve canonical paths: ");
            ((StringBuilder)object).append(iOException);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    public static int startBinding(Context context, String object, ServiceConnection serviceConnection) {
        Intent intent = new Intent();
        if ((object = MbmsUtils.getMiddlewareServiceInfo(context, (String)object)) == null) {
            return 1;
        }
        intent.setComponent(MbmsUtils.toComponentName((ComponentInfo)object));
        context.bindService(intent, serviceConnection, 1);
        return 0;
    }

    public static ComponentName toComponentName(ComponentInfo componentInfo) {
        return new ComponentName(componentInfo.packageName, componentInfo.name);
    }
}

