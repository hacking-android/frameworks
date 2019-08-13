/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import java.io.ByteArrayOutputStream;

abstract class ResponseData {
    ResponseData() {
    }

    public static void writeLength(ByteArrayOutputStream byteArrayOutputStream, int n) {
        if (n > 127) {
            byteArrayOutputStream.write(129);
        }
        byteArrayOutputStream.write(n);
    }

    @UnsupportedAppUsage
    public abstract void format(ByteArrayOutputStream var1);
}

