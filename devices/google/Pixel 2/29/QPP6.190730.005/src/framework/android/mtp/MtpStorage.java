/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.storage.StorageVolume;

public class MtpStorage {
    private final String mDescription;
    private final long mMaxFileSize;
    private final String mPath;
    private final boolean mRemovable;
    private final int mStorageId;
    private final String mVolumeName;

    public MtpStorage(StorageVolume storageVolume, int n) {
        this.mStorageId = n;
        this.mPath = storageVolume.getInternalPath();
        this.mDescription = storageVolume.getDescription(null);
        this.mRemovable = storageVolume.isRemovable();
        this.mMaxFileSize = storageVolume.getMaxFileSize();
        this.mVolumeName = storageVolume.isPrimary() ? "external_primary" : storageVolume.getNormalizedUuid();
    }

    public final String getDescription() {
        return this.mDescription;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    @UnsupportedAppUsage
    public final String getPath() {
        return this.mPath;
    }

    @UnsupportedAppUsage
    public final int getStorageId() {
        return this.mStorageId;
    }

    public String getVolumeName() {
        return this.mVolumeName;
    }

    public final boolean isRemovable() {
        return this.mRemovable;
    }
}

