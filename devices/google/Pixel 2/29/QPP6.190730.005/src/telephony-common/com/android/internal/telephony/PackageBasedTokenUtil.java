/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.util.Base64
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PackageBasedTokenUtil {
    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    private static final String HASH_TYPE = "SHA-256";
    static final int NUM_BASE64_CHARS = 11;
    private static final int NUM_HASHED_BYTES = 9;
    private static final String TAG = "PackageBasedTokenUtil";

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String generatePackageBasedToken(PackageManager object, String string) {
        int i;
        MessageDigest messageDigest;
        block6 : {
            messageDigest = null;
            object = object.getPackageInfo((String)string, (int)64).signatures;
            if (object != null) break block6;
            Log.e((String)TAG, (String)"The certificates is missing.");
            return messageDigest;
        }
        try {
            messageDigest = MessageDigest.getInstance(HASH_TYPE);
            messageDigest.update(string.getBytes(CHARSET_UTF_8));
            messageDigest.update(" ".getBytes(CHARSET_UTF_8));
            i = 0;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("NoSuchAlgorithmException");
            ((StringBuilder)object).append(noSuchAlgorithmException);
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            return null;
        }
        do {
            if (i >= ((Object)object).length) {
                return Base64.encodeToString((byte[])Arrays.copyOf(messageDigest.digest(), 9), (int)3).substring(0, 11);
            }
            messageDigest.update(object[i].toCharsString().getBytes(CHARSET_UTF_8));
            ++i;
        } while (true);
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to find package with package name: ");
            stringBuilder.append(string);
            Log.e((String)TAG, (String)stringBuilder.toString());
            return null;
        }
    }

    public static String generateToken(Context object, String string) {
        PackageManager packageManager = object.getPackageManager();
        String string2 = PackageBasedTokenUtil.generatePackageBasedToken(packageManager, string);
        Iterator iterator = packageManager.getInstalledPackages(128).iterator();
        while (iterator.hasNext()) {
            String string3 = ((PackageInfo)iterator.next()).packageName;
            if (string.equals(string3)) continue;
            object = string2;
            if (string2.equals(PackageBasedTokenUtil.generatePackageBasedToken(packageManager, string3))) {
                Log.e((String)TAG, (String)"token collides with other installed app.");
                object = null;
            }
            string2 = object;
        }
        return string2;
    }
}

