/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.util.Date;

abstract class Utils {
    private Utils() {
    }

    static Date cloneIfNotNull(Date date) {
        date = date != null ? (Date)date.clone() : null;
        return date;
    }

    static byte[] cloneIfNotNull(byte[] object) {
        object = object != null ? (byte[])object.clone() : null;
        return object;
    }
}

