/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.HexEncoding
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.util.Log;
import android.util.MutableInt;
import com.android.internal.annotations.GuardedBy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import libcore.util.HexEncoding;

@SystemApi
public class SystemProperties {
    @UnsupportedAppUsage
    public static final int PROP_NAME_MAX = Integer.MAX_VALUE;
    public static final int PROP_VALUE_MAX = 91;
    private static final String TAG = "SystemProperties";
    private static final boolean TRACK_KEY_ACCESS = false;
    @UnsupportedAppUsage
    @GuardedBy(value={"sChangeCallbacks"})
    private static final ArrayList<Runnable> sChangeCallbacks = new ArrayList();
    @GuardedBy(value={"sRoReads"})
    private static final HashMap<String, MutableInt> sRoReads = null;

    @UnsupportedAppUsage
    private SystemProperties() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void addChangeCallback(Runnable runnable) {
        ArrayList<Runnable> arrayList = sChangeCallbacks;
        synchronized (arrayList) {
            if (sChangeCallbacks.size() == 0) {
                SystemProperties.native_add_change_callback();
            }
            sChangeCallbacks.add(runnable);
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void callChangeCallbacks() {
        arrayList = SystemProperties.sChangeCallbacks;
        // MONITORENTER : arrayList
        if (SystemProperties.sChangeCallbacks.size() == 0) {
            // MONITOREXIT : arrayList
            return;
        }
        arrayList2 = new ArrayList<Runnable>(SystemProperties.sChangeCallbacks);
        var2_3 = Binder.clearCallingIdentity();
        var4_4 = 0;
        do {
            var5_5 = arrayList2.size();
            if (var4_4 >= var5_5) ** GOTO lbl25
            {
                catch (Throwable throwable) {
                    Binder.restoreCallingIdentity(var2_3);
                    throw throwable;
                }
            }
            try {
                arrayList2.get(var4_4).run();
                ** GOTO lbl23
            }
            catch (Throwable var6_6) {
                Log.wtf("SystemProperties", "Exception in SystemProperties change callback", var6_6);
lbl23: // 2 sources:
                ++var4_4;
                continue;
lbl25: // 1 sources:
                Binder.restoreCallingIdentity(var2_3);
                // MONITOREXIT : arrayList
                return;
            }
            break;
        } while (true);
    }

    public static String digestOf(String ... object) {
        Arrays.sort((Object[])object);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        for (Object object2 : object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object2);
            stringBuilder.append("=");
            stringBuilder.append(SystemProperties.get((String)object2));
            stringBuilder.append("\n");
            messageDigest.update(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        }
        try {
            String noSuchAlgorithmException = HexEncoding.encodeToString((byte[])messageDigest.digest()).toLowerCase();
            return noSuchAlgorithmException;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    @SystemApi
    public static String get(String string2) {
        return SystemProperties.native_get(string2);
    }

    @SystemApi
    public static String get(String string2, String string3) {
        return SystemProperties.native_get(string2, string3);
    }

    @SystemApi
    public static boolean getBoolean(String string2, boolean bl) {
        return SystemProperties.native_get_boolean(string2, bl);
    }

    @SystemApi
    public static int getInt(String string2, int n) {
        return SystemProperties.native_get_int(string2, n);
    }

    @SystemApi
    public static long getLong(String string2, long l) {
        return SystemProperties.native_get_long(string2, l);
    }

    private static native void native_add_change_callback();

    @UnsupportedAppUsage
    private static native String native_get(String var0);

    private static native String native_get(String var0, String var1);

    private static native boolean native_get_boolean(String var0, boolean var1);

    private static native int native_get_int(String var0, int var1);

    @UnsupportedAppUsage
    private static native long native_get_long(String var0, long var1);

    private static native void native_report_sysprop_change();

    private static native void native_set(String var0, String var1);

    private static void onKeyAccess(String string2) {
    }

    @UnsupportedAppUsage
    public static void reportSyspropChanged() {
        SystemProperties.native_report_sysprop_change();
    }

    @UnsupportedAppUsage
    public static void set(String string2, String string3) {
        if (string3 != null && !string3.startsWith("ro.") && string3.length() > 91) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("value of system property '");
            stringBuilder.append(string2);
            stringBuilder.append("' is longer than ");
            stringBuilder.append(91);
            stringBuilder.append(" characters: ");
            stringBuilder.append(string3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        SystemProperties.native_set(string2, string3);
    }
}

