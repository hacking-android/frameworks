/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 */
package com.android.internal.os;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteServer;

public class ChildZygoteInit {
    private static final String TAG = "ChildZygoteInit";

    static String parseAbiListFromArgs(String[] arrstring) {
        for (String string2 : arrstring) {
            if (!string2.startsWith("--abi-list=")) continue;
            return string2.substring("--abi-list=".length());
        }
        return null;
    }

    static int parseIntFromArg(String[] object, String string2) {
        int n = -1;
        int n2 = ((String[])object).length;
        for (int i = 0; i < n2; ++i) {
            String string3 = object[i];
            if (!string3.startsWith(string2)) continue;
            string3 = string3.substring(string3.indexOf(61) + 1);
            try {
                n = Integer.parseInt(string3);
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid int argument: ");
                ((StringBuilder)object).append(string3);
                throw new IllegalArgumentException(((StringBuilder)object).toString(), numberFormatException);
            }
        }
        return n;
    }

    static String parseSocketNameFromArgs(String[] arrstring) {
        for (String string2 : arrstring) {
            if (!string2.startsWith("--zygote-socket=")) continue;
            return string2.substring("--zygote-socket=".length());
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void runZygoteServer(ZygoteServer zygoteServer, String[] object) {
        Throwable throwable2222;
        String string2 = ChildZygoteInit.parseSocketNameFromArgs((String[])object);
        if (string2 == null) throw new NullPointerException("No socketName specified");
        String string3 = ChildZygoteInit.parseAbiListFromArgs((String[])object);
        if (string3 == null) throw new NullPointerException("No abiList specified");
        try {
            Os.prctl((int)OsConstants.PR_SET_NO_NEW_PRIVS, (long)1L, (long)0L, (long)0L, (long)0L);
        }
        catch (ErrnoException errnoException) {
            throw new RuntimeException("Failed to set PR_SET_NO_NEW_PRIVS", errnoException);
        }
        int n = ChildZygoteInit.parseIntFromArg((String[])object, "--uid-range-start=");
        int n2 = ChildZygoteInit.parseIntFromArg((String[])object, "--uid-range-end=");
        if (n == -1) throw new RuntimeException("Couldn't parse UID range start/end");
        if (n2 == -1) throw new RuntimeException("Couldn't parse UID range start/end");
        if (n > n2) throw new RuntimeException("Passed in UID range is invalid, min > max.");
        if (n < 90000) throw new RuntimeException("Passed in UID range does not map to isolated processes.");
        Zygote.nativeInstallSeccompUidGidFilter(n, n2);
        zygoteServer.registerServerSocketAtAbstractName(string2);
        object = new StringBuilder();
        ((StringBuilder)object).append("ABSTRACT/");
        ((StringBuilder)object).append(string2);
        Zygote.nativeAllowFileAcrossFork(((StringBuilder)object).toString());
        object = zygoteServer.runSelectLoop(string3);
        zygoteServer.closeServerSocket();
        if (object == null) return;
        object.run();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                Log.e(TAG, "Fatal exception:", runtimeException);
                throw runtimeException;
            }
        }
        zygoteServer.closeServerSocket();
        throw throwable2222;
    }
}

