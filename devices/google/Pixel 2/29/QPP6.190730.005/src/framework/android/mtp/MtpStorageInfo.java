/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

public final class MtpStorageInfo {
    private String mDescription;
    private long mFreeSpace;
    private long mMaxCapacity;
    private int mStorageId;
    private String mVolumeIdentifier;

    private MtpStorageInfo() {
    }

    public final String getDescription() {
        return this.mDescription;
    }

    public final long getFreeSpace() {
        return this.mFreeSpace;
    }

    public final long getMaxCapacity() {
        return this.mMaxCapacity;
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final String getVolumeIdentifier() {
        return this.mVolumeIdentifier;
    }
}

