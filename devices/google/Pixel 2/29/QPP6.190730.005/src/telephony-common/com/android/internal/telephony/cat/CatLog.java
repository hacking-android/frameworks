/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;

public abstract class CatLog {
    static final boolean DEBUG = true;

    @UnsupportedAppUsage
    public static void d(Object object, String string) {
        String string2 = object.getClass().getName();
        object = new StringBuilder();
        ((StringBuilder)object).append(string2.substring(string2.lastIndexOf(46) + 1));
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(string);
        Rlog.d((String)"CAT", (String)((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static void d(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        Rlog.d((String)"CAT", (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static void e(Object object, String string) {
        object = object.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((String)object).substring(((String)object).lastIndexOf(46) + 1));
        stringBuilder.append(": ");
        stringBuilder.append(string);
        Rlog.e((String)"CAT", (String)stringBuilder.toString());
    }

    public static void e(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        Rlog.e((String)"CAT", (String)stringBuilder.toString());
    }
}

