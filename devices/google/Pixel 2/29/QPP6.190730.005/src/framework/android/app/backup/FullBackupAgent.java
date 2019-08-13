/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.os.ParcelFileDescriptor;
import java.io.IOException;

public class FullBackupAgent
extends BackupAgent {
    @Override
    public void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
    }

    @Override
    public void onRestore(BackupDataInput backupDataInput, int n, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
    }
}

