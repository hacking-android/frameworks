/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.IOException;

public class BackupDataOutput {
    @UnsupportedAppUsage
    long mBackupWriter;
    private final long mQuota;
    private final int mTransportFlags;

    @SystemApi
    public BackupDataOutput(FileDescriptor fileDescriptor) {
        this(fileDescriptor, -1L, 0);
    }

    @SystemApi
    public BackupDataOutput(FileDescriptor fileDescriptor, long l) {
        this(fileDescriptor, l, 0);
    }

    public BackupDataOutput(FileDescriptor fileDescriptor, long l, int n) {
        if (fileDescriptor != null) {
            this.mQuota = l;
            this.mTransportFlags = n;
            this.mBackupWriter = BackupDataOutput.ctor(fileDescriptor);
            if (this.mBackupWriter != 0L) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Native initialization failed with fd=");
            stringBuilder.append(fileDescriptor);
            throw new RuntimeException(stringBuilder.toString());
        }
        throw new NullPointerException();
    }

    private static native long ctor(FileDescriptor var0);

    private static native void dtor(long var0);

    private static native void setKeyPrefix_native(long var0, String var2);

    private static native int writeEntityData_native(long var0, byte[] var2, int var3);

    private static native int writeEntityHeader_native(long var0, String var2, int var3);

    protected void finalize() throws Throwable {
        try {
            BackupDataOutput.dtor(this.mBackupWriter);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public long getQuota() {
        return this.mQuota;
    }

    public int getTransportFlags() {
        return this.mTransportFlags;
    }

    public void setKeyPrefix(String string2) {
        BackupDataOutput.setKeyPrefix_native(this.mBackupWriter, string2);
    }

    public int writeEntityData(byte[] object, int n) throws IOException {
        if ((n = BackupDataOutput.writeEntityData_native(this.mBackupWriter, (byte[])object, n)) >= 0) {
            return n;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("result=0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new IOException(((StringBuilder)object).toString());
    }

    public int writeEntityHeader(String charSequence, int n) throws IOException {
        if ((n = BackupDataOutput.writeEntityHeader_native(this.mBackupWriter, (String)charSequence, n)) >= 0) {
            return n;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("result=0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new IOException(((StringBuilder)charSequence).toString());
    }
}

