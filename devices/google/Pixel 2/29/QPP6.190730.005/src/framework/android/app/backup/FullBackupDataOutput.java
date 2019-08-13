/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupDataOutput;
import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;

public class FullBackupDataOutput {
    @UnsupportedAppUsage
    private final BackupDataOutput mData;
    private final long mQuota;
    private long mSize;
    private final int mTransportFlags;

    public FullBackupDataOutput(long l) {
        this.mData = null;
        this.mQuota = l;
        this.mSize = 0L;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(long l, int n) {
        this.mData = null;
        this.mQuota = l;
        this.mSize = 0L;
        this.mTransportFlags = n;
    }

    @UnsupportedAppUsage
    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor) {
        this(parcelFileDescriptor, -1L, 0);
    }

    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor, long l) {
        this.mData = new BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), l, 0);
        this.mQuota = l;
        this.mTransportFlags = 0;
    }

    public FullBackupDataOutput(ParcelFileDescriptor parcelFileDescriptor, long l, int n) {
        this.mData = new BackupDataOutput(parcelFileDescriptor.getFileDescriptor(), l, n);
        this.mQuota = l;
        this.mTransportFlags = n;
    }

    @UnsupportedAppUsage
    public void addSize(long l) {
        if (l > 0L) {
            this.mSize += l;
        }
    }

    @UnsupportedAppUsage
    public BackupDataOutput getData() {
        return this.mData;
    }

    public long getQuota() {
        return this.mQuota;
    }

    public long getSize() {
        return this.mSize;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }
}

