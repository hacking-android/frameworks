/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.os.ParcelFileDescriptor;

public interface BackupHelper {
    public void performBackup(ParcelFileDescriptor var1, BackupDataOutput var2, ParcelFileDescriptor var3);

    public void restoreEntity(BackupDataInputStream var1);

    public void writeNewStateDescription(ParcelFileDescriptor var1);
}

