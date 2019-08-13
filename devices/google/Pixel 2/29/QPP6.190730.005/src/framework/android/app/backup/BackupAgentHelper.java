/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.app.backup.BackupHelperDispatcher;
import android.os.ParcelFileDescriptor;
import java.io.IOException;

public class BackupAgentHelper
extends BackupAgent {
    static final String TAG = "BackupAgentHelper";
    BackupHelperDispatcher mDispatcher = new BackupHelperDispatcher();

    public void addHelper(String string2, BackupHelper backupHelper) {
        this.mDispatcher.addHelper(string2, backupHelper);
    }

    public BackupHelperDispatcher getDispatcher() {
        return this.mDispatcher;
    }

    @Override
    public void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException {
        this.mDispatcher.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    @Override
    public void onRestore(BackupDataInput backupDataInput, int n, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        this.mDispatcher.performRestore(backupDataInput, n, parcelFileDescriptor);
    }
}

