/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

public class ProcessedData {
    private String mAccountId = "_NO_USER";
    private final byte[] mData;
    private String mSubscriptionId = "";

    ProcessedData(byte[] arrby, String string2) {
        this.mData = arrby;
        this.mAccountId = string2;
    }

    ProcessedData(byte[] arrby, String string2, String string3) {
        this.mData = arrby;
        this.mAccountId = string2;
        this.mSubscriptionId = string3;
    }

    public String getAccountId() {
        return this.mAccountId;
    }

    public byte[] getData() {
        return this.mData;
    }

    public String getSubscriptionId() {
        return this.mSubscriptionId;
    }
}

