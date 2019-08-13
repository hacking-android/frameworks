/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma;

public class CdmaSmsBroadcastConfigInfo {
    private int mFromServiceCategory;
    private int mLanguage;
    private boolean mSelected;
    private int mToServiceCategory;

    public CdmaSmsBroadcastConfigInfo(int n, int n2, int n3, boolean bl) {
        this.mFromServiceCategory = n;
        this.mToServiceCategory = n2;
        this.mLanguage = n3;
        this.mSelected = bl;
    }

    public int getFromServiceCategory() {
        return this.mFromServiceCategory;
    }

    public int getLanguage() {
        return this.mLanguage;
    }

    public int getToServiceCategory() {
        return this.mToServiceCategory;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CdmaSmsBroadcastConfigInfo: Id [");
        stringBuilder.append(this.mFromServiceCategory);
        stringBuilder.append(", ");
        stringBuilder.append(this.mToServiceCategory);
        stringBuilder.append("] ");
        String string = this.isSelected() ? "ENABLED" : "DISABLED";
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}

