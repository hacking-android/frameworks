/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;

public abstract class SmsAddress {
    public static final int TON_ABBREVIATED = 6;
    public static final int TON_ALPHANUMERIC = 5;
    public static final int TON_INTERNATIONAL = 1;
    public static final int TON_NATIONAL = 2;
    public static final int TON_NETWORK = 3;
    public static final int TON_SUBSCRIBER = 4;
    public static final int TON_UNKNOWN = 0;
    public String address;
    @UnsupportedAppUsage
    public byte[] origBytes;
    public int ton;

    public boolean couldBeEmailGateway() {
        boolean bl = this.address.length() <= 4;
        return bl;
    }

    public String getAddressString() {
        return this.address;
    }

    public boolean isAlphanumeric() {
        boolean bl = this.ton == 5;
        return bl;
    }

    public boolean isNetworkSpecific() {
        boolean bl = this.ton == 3;
        return bl;
    }
}

