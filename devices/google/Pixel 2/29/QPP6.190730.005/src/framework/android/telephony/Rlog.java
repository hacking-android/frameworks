/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Rlog {
    private static final boolean USER_BUILD = Build.IS_USER;

    private Rlog() {
    }

    @UnsupportedAppUsage
    public static int d(String string2, String string3) {
        return Log.println_native(1, 3, string2, string3);
    }

    @UnsupportedAppUsage
    public static int d(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(1, 3, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int e(String string2, String string3) {
        return Log.println_native(1, 6, string2, string3);
    }

    @UnsupportedAppUsage
    public static int e(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(1, 6, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int i(String string2, String string3) {
        return Log.println_native(1, 4, string2, string3);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static int i(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(1, 4, string2, stringBuilder.toString());
    }

    public static boolean isLoggable(String string2, int n) {
        return Log.isLoggable(string2, n);
    }

    public static String pii(String charSequence, Object object) {
        String string2 = String.valueOf(object);
        if (object != null && !TextUtils.isEmpty(string2) && !Rlog.isLoggable((String)charSequence, 2)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[");
            ((StringBuilder)charSequence).append(Rlog.secureHash(string2.getBytes()));
            ((StringBuilder)charSequence).append("]");
            return ((StringBuilder)charSequence).toString();
        }
        return string2;
    }

    public static String pii(boolean bl, Object object) {
        String string2 = String.valueOf(object);
        if (object != null && !TextUtils.isEmpty(string2) && !bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[");
            ((StringBuilder)object).append(Rlog.secureHash(string2.getBytes()));
            ((StringBuilder)object).append("]");
            return ((StringBuilder)object).toString();
        }
        return string2;
    }

    public static int println(int n, String string2, String string3) {
        return Log.println_native(1, n, string2, string3);
    }

    private static String secureHash(byte[] arrby) {
        MessageDigest messageDigest;
        if (USER_BUILD) {
            return "****";
        }
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "####";
        }
        return Base64.encodeToString(messageDigest.digest(arrby), 11);
    }

    @UnsupportedAppUsage
    public static int v(String string2, String string3) {
        return Log.println_native(1, 2, string2, string3);
    }

    public static int v(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(1, 2, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int w(String string2, String string3) {
        return Log.println_native(1, 5, string2, string3);
    }

    @UnsupportedAppUsage
    public static int w(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(1, 5, string2, stringBuilder.toString());
    }

    public static int w(String string2, Throwable throwable) {
        return Log.println_native(1, 5, string2, Log.getStackTraceString(throwable));
    }
}

