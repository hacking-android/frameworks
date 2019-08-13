/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.IOException;

public class BackupDataInput {
    long mBackupReader;
    private EntityHeader mHeader = new EntityHeader();
    private boolean mHeaderReady;

    @SystemApi
    public BackupDataInput(FileDescriptor fileDescriptor) {
        if (fileDescriptor != null) {
            this.mBackupReader = BackupDataInput.ctor(fileDescriptor);
            if (this.mBackupReader != 0L) {
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

    private native int readEntityData_native(long var1, byte[] var3, int var4, int var5);

    private native int readNextHeader_native(long var1, EntityHeader var3);

    private native int skipEntityData_native(long var1);

    protected void finalize() throws Throwable {
        try {
            BackupDataInput.dtor(this.mBackupReader);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getDataSize() {
        if (this.mHeaderReady) {
            return this.mHeader.dataSize;
        }
        throw new IllegalStateException("Entity header not read");
    }

    public String getKey() {
        if (this.mHeaderReady) {
            return this.mHeader.key;
        }
        throw new IllegalStateException("Entity header not read");
    }

    public int readEntityData(byte[] object, int n, int n2) throws IOException {
        if (this.mHeaderReady) {
            if ((n = this.readEntityData_native(this.mBackupReader, (byte[])object, n, n2)) >= 0) {
                return n;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("result=0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            throw new IOException(((StringBuilder)object).toString());
        }
        throw new IllegalStateException("Entity header not read");
    }

    public boolean readNextHeader() throws IOException {
        int n = this.readNextHeader_native(this.mBackupReader, this.mHeader);
        if (n == 0) {
            this.mHeaderReady = true;
            return true;
        }
        if (n > 0) {
            this.mHeaderReady = false;
            return false;
        }
        this.mHeaderReady = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("failed: 0x");
        stringBuilder.append(Integer.toHexString(n));
        throw new IOException(stringBuilder.toString());
    }

    public void skipEntityData() throws IOException {
        if (this.mHeaderReady) {
            this.skipEntityData_native(this.mBackupReader);
            return;
        }
        throw new IllegalStateException("Entity header not read");
    }

    private static class EntityHeader {
        @UnsupportedAppUsage
        int dataSize;
        @UnsupportedAppUsage
        String key;

        private EntityHeader() {
        }
    }

}

