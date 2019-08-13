/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.os.UserHandle;
import android.permission.PermissionManagerInternal;
import android.util.Slog;
import com.android.server.LocalServices;

public class PermissionBackupHelper
extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_PERMISSIONS = "permissions";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "PermissionBackup";
    private final PermissionManagerInternal mPermissionManager;
    private final UserHandle mUser;

    public PermissionBackupHelper(int n) {
        super(1, KEY_PERMISSIONS);
        this.mUser = UserHandle.of(n);
        this.mPermissionManager = LocalServices.getService(PermissionManagerInternal.class);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void applyRestoredPayload(String string2, byte[] object) {
        int n = -1;
        try {
            if (string2.hashCode() == 1133704324 && string2.equals(KEY_PERMISSIONS)) {
                n = 0;
            }
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected restore key ");
                ((StringBuilder)object).append(string2);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            this.mPermissionManager.restoreRuntimePermissions((byte[])object, this.mUser);
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to restore key ");
            stringBuilder.append(string2);
            Slog.w(TAG, stringBuilder.toString());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] getBackupPayload(String string2) {
        int n = -1;
        if (string2.hashCode() == 1133704324 && string2.equals(KEY_PERMISSIONS)) {
            n = 0;
        }
        if (n == 0) return this.mPermissionManager.backupRuntimePermissions(this.mUser);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected backup key ");
            stringBuilder.append(string2);
            Slog.w(TAG, stringBuilder.toString());
            return null;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to store payload ");
            stringBuilder.append(string2);
            Slog.e(TAG, stringBuilder.toString());
        }
        return null;
    }
}

