/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.backup;

import android.accounts.AccountManagerInternal;
import android.app.backup.BlobBackupHelper;
import android.util.Slog;
import com.android.server.LocalServices;

public class AccountManagerBackupHelper
extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_ACCOUNT_ACCESS_GRANTS = "account_access_grants";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "AccountsBackup";

    public AccountManagerBackupHelper() {
        super(1, KEY_ACCOUNT_ACCESS_GRANTS);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void applyRestoredPayload(String string2, byte[] object) {
        AccountManagerInternal accountManagerInternal = LocalServices.getService(AccountManagerInternal.class);
        int n = -1;
        try {
            if (string2.hashCode() == 1544100736 && string2.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                n = 0;
            }
            if (n != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected restore key ");
                ((StringBuilder)object).append(string2);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            accountManagerInternal.restoreAccountAccessPermissions((byte[])object, 0);
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
        byte[] arrby = LocalServices.getService(AccountManagerInternal.class);
        int n = -1;
        if (string2.hashCode() == 1544100736 && string2.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
            n = 0;
        }
        if (n == 0) return arrby.backupAccountAccessPermissions(0);
        try {
            arrby = new StringBuilder();
            arrby.append("Unexpected backup key ");
            arrby.append(string2);
            Slog.w(TAG, arrby.toString());
            return new byte[0];
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to store payload ");
            stringBuilder.append(string2);
            Slog.e(TAG, stringBuilder.toString());
        }
        return new byte[0];
    }
}

