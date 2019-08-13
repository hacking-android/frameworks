/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

public final class SmsBroadcastConfigInfo {
    private int mFromCodeScheme;
    private int mFromServiceId;
    private boolean mSelected;
    private int mToCodeScheme;
    private int mToServiceId;

    public SmsBroadcastConfigInfo(int n, int n2, int n3, int n4, boolean bl) {
        this.mFromServiceId = n;
        this.mToServiceId = n2;
        this.mFromCodeScheme = n3;
        this.mToCodeScheme = n4;
        this.mSelected = bl;
    }

    public int getFromCodeScheme() {
        return this.mFromCodeScheme;
    }

    public int getFromServiceId() {
        return this.mFromServiceId;
    }

    public int getToCodeScheme() {
        return this.mToCodeScheme;
    }

    public int getToServiceId() {
        return this.mToServiceId;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public void setFromCodeScheme(int n) {
        this.mFromCodeScheme = n;
    }

    public void setFromServiceId(int n) {
        this.mFromServiceId = n;
    }

    public void setSelected(boolean bl) {
        this.mSelected = bl;
    }

    public void setToCodeScheme(int n) {
        this.mToCodeScheme = n;
    }

    public void setToServiceId(int n) {
        this.mToServiceId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SmsBroadcastConfigInfo: Id [");
        stringBuilder.append(this.mFromServiceId);
        stringBuilder.append(',');
        stringBuilder.append(this.mToServiceId);
        stringBuilder.append("] Code [");
        stringBuilder.append(this.mFromCodeScheme);
        stringBuilder.append(',');
        stringBuilder.append(this.mToCodeScheme);
        stringBuilder.append("] ");
        String string2 = this.mSelected ? "ENABLED" : "DISABLED";
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }
}

