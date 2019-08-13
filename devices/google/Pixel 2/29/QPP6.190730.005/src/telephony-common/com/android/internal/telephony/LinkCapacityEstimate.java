/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

public class LinkCapacityEstimate {
    public static final int INVALID = -1;
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_SUSPENDED = 1;
    public final int confidence;
    public final int downlinkCapacityKbps;
    public final int status;
    public final int uplinkCapacityKbps;

    public LinkCapacityEstimate(int n, int n2) {
        this.downlinkCapacityKbps = n;
        this.uplinkCapacityKbps = n2;
        this.confidence = -1;
        this.status = -1;
    }

    public LinkCapacityEstimate(int n, int n2, int n3) {
        this.downlinkCapacityKbps = n;
        this.confidence = n2;
        this.status = n3;
        this.uplinkCapacityKbps = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{downlinkCapacityKbps=");
        stringBuilder.append(this.downlinkCapacityKbps);
        stringBuilder.append(", uplinkCapacityKbps=");
        stringBuilder.append(this.uplinkCapacityKbps);
        stringBuilder.append(", confidence=");
        stringBuilder.append(this.confidence);
        stringBuilder.append(", status=");
        stringBuilder.append(this.status);
        return stringBuilder.toString();
    }
}

