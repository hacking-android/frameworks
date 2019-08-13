/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.UnsupportedAppUsage;
import android.app.backup.BackupDataInput;
import java.io.IOException;
import java.io.InputStream;

public class BackupDataInputStream
extends InputStream {
    @UnsupportedAppUsage
    int dataSize;
    @UnsupportedAppUsage
    String key;
    BackupDataInput mData;
    byte[] mOneByte;

    BackupDataInputStream(BackupDataInput backupDataInput) {
        this.mData = backupDataInput;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public int read() throws IOException {
        byte[] arrby = this.mOneByte;
        if (this.mOneByte == null) {
            this.mOneByte = arrby = new byte[1];
        }
        this.mData.readEntityData(arrby, 0, 1);
        return arrby[0];
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.mData.readEntityData(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        return this.mData.readEntityData(arrby, n, n2);
    }

    public int size() {
        return this.dataSize;
    }
}

