/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc.apdu;

public class ApduException
extends Exception {
    private final int mApduStatus;

    public ApduException(int n) {
        this.mApduStatus = n;
    }

    public ApduException(String string) {
        super(string);
        this.mApduStatus = 0;
    }

    public int getApduStatus() {
        return this.mApduStatus;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.getMessage());
        stringBuilder.append(" (apduStatus=");
        stringBuilder.append(this.getStatusHex());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String getStatusHex() {
        return Integer.toHexString(this.mApduStatus);
    }
}

