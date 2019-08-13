/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.app.backup.BackupProgress;

@SystemApi
public abstract class BackupObserver {
    public void backupFinished(int n) {
    }

    public void onResult(String string2, int n) {
    }

    public void onUpdate(String string2, BackupProgress backupProgress) {
    }
}

